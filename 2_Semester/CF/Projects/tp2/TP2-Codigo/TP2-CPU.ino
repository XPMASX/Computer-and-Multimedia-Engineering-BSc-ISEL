int RAM[256];
int ROM[128];

#define tempoRuido 200

const int ROM_MC[64]= {0x621, 0x621, 0x621, 0x621, 0x621, 0x621, 0x621, 0x621, 
                       0x600, 0x600, 0x1740, 0x3E00, 0x1740, 0x1740,0x3F40, 0x3F40, 
                       0x780, 0x620, 0x200, 0x500, 0x600, 0x600, 0x600, 0x600, 
                       0x610, 0x610, 0x610, 0x610, 0x610, 0x610, 0x610, 0x610,
                       0x608, 0x608, 0x608, 0x608, 0x608, 0x608, 0x608, 0x608, 
                       0x604, 0x604, 0x604, 0x604, 0x604, 0x604, 0x604, 0x604, 
                       0x602, 0x602, 0x602, 0x602, 0x602, 0x602, 0x602, 0x602, 
                       0x602, 0x602, 0x602, 0x602, 0x602, 0x602, 0x602, 0x602};


int DPC, QPC = 0;     // registo PC
int DRn[2], QRn[2], ARn;  // conjunto de 2 registos
int DA, QA;               // registo A
int const5, rel6, end7;
int S;                    // ALU
int addressMC;
long unsigned int t0, t;

bool DC, QC;    // flag Cy ou Borrow
bool DZ, QZ;    // flag zero
bool DOV, QOV;    // flag overflow
bool EnAux, EnPC, EnRn, EnA, EnA0, EnA1, EnC, EnOV, EnZ, JC, JNZ, JOV, JMP, WR, RD;
bool clk;
bool d3, d4, d5, d6, d7, d8;


void setup(){
  Serial.begin(9600);
  attachInterrupt(0, clock, RISING);  //d2
  t=millis();
  
  program();
  
  //subtractor
  //program2();
  
  //program3();
  //program4();
  
}

void loop(){
  circuitoSequencial();
  circuitoCombinatorio();
  mooduloControlo(); //?????
  escreverSaidas();
}

void circuitoSequencial(){
  if (clk){
    //Registo A
    registos5bits(EnA, DA, &QA);
    
    //Conjunto de registo RN
    ARn = ROM[QPC] & 0x01; 
    registos5bits(EnRn, DRn[ARn], &QRn[ARn]);
      
    // Contador PC
    contador7bits(EnPC, DPC, &QPC);
    
    // Carry
    registo1bit(EnC, DC, &QC);
    
    // OV
    registo1bit(EnOV, DOV, &QOV);
    
    // Zero
    registo1bit(EnZ, DZ, &QZ);
   
    clk=false;
  }
}

void circuitoCombinatorio(){
    // definição das variaveis
    // bits e0 -> e6 (d0 - d6)
    end7   = ROM[QPC] & 0x07F;
  
    // bits c0 -> c4 (d1 - d5)
    const5 = ROM[QPC] & 0x03E;
  
    // bits r0 -> r5  (d0 - d5) 
    rel6   = ROM[QPC] & 0x03F;
  
    //distinção entre R0 e R1
    ARn = ROM[QPC] & 0x01 == 0x01;
  
    //bits que distinguem entre as operação da ALU
    d3 = ROM[QPC] & 0x008;
    d4 = ROM[QPC] & 0x010;
    d5 = ROM[QPC] & 0x020;
  
    // Mux do Registo Rn
	mux2x1(EnAux, QA, const5, &DRn[ARn]);
  
    // Mux do Registo A               
    mux3x1(EnA, EnA1, EnA0, S, QRn[ARn], RAM[QRn[ARn]], &DA);
  
    //bits que distinguem entre as operação da ALU
  d6 = ROM[QPC] & 0x040;
    d7 = ROM[QPC] & 0x080;
    d8 = ROM[QPC] & 0x100;
  
    // Arithmetic Logic Unit
    //if(d6 & !d7 & !d8){
    ALU(d3 , d4 , d5 , QC, QOV, QZ, QA, QRn[ARn], &DC, &DOV, &DZ, &S);
    //}
    // Registo na RAM (@Rn, A)
    registos8bits(!WR, QA, &RAM[QRn[ARn]]);
 
}

void circuitoCombinatorioMC(){
    EnPC = JNZ && !QZ || JC && QC|| JOV && QOV || JMP;
}
                              
void validarSinaisMC(){
  
//adress dos bits dependentes/bits de entrada no Modulo de Controlo
// D8 -> D3
    addressMC = ((ROM[QPC] & 0x1F8) >> 3);
  
    EnZ  = (ROM_MC[addressMC] & 0x1000) == 0x1000;
    EnC  = (ROM_MC[addressMC] & 0x2000) == 0x2000;
	EnOV = (ROM_MC[addressMC] & 0x0800) == 0x0800;
	WR   = (ROM_MC[addressMC] & 0x0400) == 0x0400;  
	RD   = (ROM_MC[addressMC] & 0x0200) == 0x0200;
    EnA  = (ROM_MC[addressMC] & 0x0100) == 0x0100;
    EnA0 = (ROM_MC[addressMC] & 0x080) == 0x080; 
	EnA1 = (ROM_MC[addressMC] & 0x040) == 0x040; 
	EnRn = (ROM_MC[addressMC] & 0x020) == 0x020;
    JC   = (ROM_MC[addressMC] & 0x010) == 0x010;
    JNZ  = (ROM_MC[addressMC] & 0x08) == 0x08;
    JOV  = (ROM_MC[addressMC] & 0x04) == 0x04;
	JMP  = (ROM_MC[addressMC] & 0x02) == 0x02;
	EnAux= (ROM_MC[addressMC] & 0x01) == 0x01;  
}

                              
void mooduloControlo(){
  validarSinaisMC();
  circuitoCombinatorioMC();
}

void clock(){
  if(filtroRuido()) clk=true;
}

bool filtroRuido(){
  t0 = t;
  t = millis();
  return((t - t0) > tempoRuido);
}

// Mux RegistoRn
void mux2x1(bool EnAux, int i1, int i0, int *y){
  if (EnAux) *y=i0;
  else *y=i1;  
}
                              
// Registo
void registos5bits(bool enable, int D, int *Q){
  if (enable) *Q=D & 0x1F;
}

void registos8bits(bool enable, int D, int*Q){
  if (enable) *Q=D & 0xFF;
}

// Mux Registo A
void mux3x1(bool enable, bool a1, bool a0, int i2, int i1, int i0, int *y){
  if (a0 && !a1) *y=i1;
  else if (!a0 && !a1) *y=i0;
  else if (!a0 && a1) *y=i2;
}
                       

// ALU
void ALU(bool d3, bool d4, bool d5, bool cyin, bool ovin, bool zin, int a, int b, bool *cyout, bool *ovout, bool *zero, int *s){
  if (d3 && d4 && !d5){             //CPLF
    *cyout=!cyin;
    *ovout=!ovin;
    *zero= !zin;
  }
  else{                         //Intructions:
    if (!d3 && d4 && !d5) *s = !a & 0x1F;        		//NOT
    if (!d3 && !d4 && d5) *s = (a & b) & 0x1F;       	//AND
    if (d3 && !d4 && d5) *s = (a | b) & 0x1F;        	//OR
    if (!d3 && d4 && d5)  *s= (a + b + cyin) & 0x1F; 	//ADD   
    if (d3 && d4 && d5) *s = (a - b - cyin) & 0x1F;   	//SUBB
  }
  if (*s == 0 ) *zero = 1;
  else *zero = 0;
  if (*s > 15 ) *ovout = 1;
  else *ovout = 0;
  *cyout = (*s & 0x20) == 0x20;
  
}
                              
//Somador rel6 do PC
void somador7bits(int A, int B, int *Y){
  *Y = A + B;
}                          

// PC
void contador7bits(bool enable, int D, int *Q){
  if (enable){
    if(JMP) *Q = end7 & 0xFF;
    else if (JC | JNZ | JOV) *Q = (*Q + rel6) & 0xFF;
    
  } else *Q = (*Q + 1) & 0xFF;

}

// Carry, OV, Zero
void registo1bit(bool enable, bool D, bool *Q){
  if (enable) *Q=D;
}
                              

void program(){
  //este é o teste do ultimo slide do moodle
  
  RAM[2] = 5; // X
  RAM[4] = 0x1F; // Y
  RAM[6] = 0; // Z
  
  ROM[0]  = 0x002; // MOV R0, 2          	| Rn[0] = const5 (=2)
  ROM[1]  = 0x09E; // MOV A, @R0 (A = X)    | A = MD(Rn[0])
  ROM[2]  = 0x08F; // MOV R1, A (R1 = X)     | Rn[1] = A  
  ROM[3]  = 0x000; // MOV R0, 0        		| Rn[0] = const5 (=0)
  ROM[4]  = 0x086; // MOV A, R0 (A = R0 = 0)| A = Rn[0]
  ROM[5]  = 0x076; // ADDC A, R0 (CY = 0)   | A = A + Rn[0] + C
  ROM[6]  = 0x004; // MOV R0, 4          | Rn[0] = const5 (=4)
  ROM[7]  = 0x09E; // MOV A, @R0 (A = Y)     | A = Rn[0]
  ROM[8]  = 0x077; // ADDC A, R1 (A = X+Y+0)| A = A + Rn[1] + C 
  ROM[9]  = 0x006; // MOV R0, 6          | Rn[0] = const5 (=2)
  ROM[10] = 0x096; // MOV @R0, A (Z = X+Y) | MD(Rn) = A
  ROM[11] = 0x180; // JMP 0          | PC = end7
  
}
void program2(){
// SUBTRATOR
  ROM[0]  = 0x00A; // MOV R0, 10         | Rn[0] = const5 (=10)
  ROM[1]  = 0x086; // MOV A, R0 (A=R0=10)  | A = Rn[0]
  ROM[2]  = 0x004; // MOV R0, 4            | Rn[0] = const5 (=4)
  ROM[3]  = 0x07E; // SUBB A, Rn1        | A = A – Rn[1] – C (C=0)
  ROM[4]  = 0x006; // MOV  R0, 6       | Rn[0] = const5 (=6)
  ROM[5]  = 0x07E; // SUBB A, Rn1        | A = A – Rn[1] – C (C=0)
  ROM[6]  = 0X10A; // JNZ  Rel 6           | Se (Z/) PC += rel6 (Rel6= 10)
  ROM[16] = 0x180; // JMP 0          | PC = end7

}

void program3(){
  
  ROM[0] = 0x002; // MOV R0, 2    | Rn[0] = const5 (=2)
  ROM[1] = 0x086; // MOV A, R0     | A = Rn[0]
  ROM[2] = 0X06E; // OR  A, Rn     | A = A or Rn
  ROM[3] = 0x086; // MOV A, R0     | A = Rn[0]
  ROM[4] = 0X066; // AND A, Rn     | A = A and Rn
  ROM[5] = 0x057; // NOT A         | A = A/
}

void program4(){
  ROM[0]  = 0x004; // MOV R0, 4     | Rn[0] = const5 (=4)
  ROM[1]  = 0x086; // MOV A, R0     | A = Rn[0]
  ROM[2]  = 0x009; // MOV R1, 8     | Rn[1] = const5 (=8)
  ROM[3]  = 0x077; // ADDC A, R1      | A = A + Rn[1] + C
  ROM[4]  = 0x104; // JNZ 4         | Se (Z/) PC += rel6
  //ROM[5]  = 0x002; // MOV R0, 0 NÃO EXECUTA
  ROM[8]  = 0x01E; // MOV R0, 0   | Rn[0] = const5 (=0)
  ROM[9]  = 0x086; // MOV A, R0     | A = Rn[0]
  ROM[10] = 0x01F; // MOV R1, 30    | Rn[1] = const5 (=30)
  ROM[11] = 0x077; // ADDC A, R1    | A = A + Rn[1] + C
  ROM[12] = 0x0C4; // JC 4          | Se (C) PC += rel6
  ROM[16] = 0x145; // JOV 5     | Se (Ov) PC += rel6
  ROM[21] = 0x05F; //CPLF           | C=C/;Ov=Ov/;Z=Z/
  ROM[22] = 0X180; // JMP 0     | PC = end7
}

void escreverSaidas(){
  if(clk){
    Serial.print("DBROM: 0x");Serial.print(ROM[QPC],16);
    Serial.print("; ROM_MC: 0x");Serial.println(ROM_MC[addressMC] ,16);
    
    String s = "QPC: " + String(QPC) +"; DRn[0]: " + String(DRn[0])+"; QRn[0]: " + String(QRn[0]) +"; DRn[1]: " + String(DRn[1])+"; QRn[1]: " + String(QRn[1])+"; DA: " + String(DA) + " ; QA: " + String(QA)+ " ; Cyout: " + String(QC)+ " ; Ov: " + String(QOV)+ " ; Zero: " + String(QZ) + " ; S: " + String(S) + " ; RAM[QRn["+String(ARn)+"]]: " + String(RAM[QRn[ARn]]);
    
    
    Serial.print(s);
     
    Serial.println();
 
    
  }
}
