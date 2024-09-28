#define tRuido 100
#define f 5 
#define LED_S3 8
#define LED_S2 9
#define LED_S1 10

#define LED_S0 11

bool F; //F=0 crescente F=1 decrescente 
        //botao pressionado é 0
bool D1,D0,Q1,Q0;  
bool S3,S2,S1,S0;  //saidas
long unsigned int t0,t=millis();

void setup(){
    pinMode(f, INPUT);
    pinMode(LED_S3, OUTPUT);
    pinMode(LED_S2, OUTPUT);
    pinMode(LED_S1, OUTPUT);
    pinMode(LED_S0, OUTPUT);
    Serial.begin(9600);
  attachInterrupt(0, circuitoSequencial, RISING); 
// o @param1 pode ser 0 ou 1 (pin D2, D3)
// quando o @param3 for RISING (ascendente),
//executa o @param2 circuitoSequencial (é onde chamamos os flipflops)
//para o @param2 temos (RISING, FALLING, HIGH, LOW)
  interrupts();
}
void loop () {
  
  lerEntradas();
  noInterrupts();
  circuitoCombinatorio();
  interrupts();
  escreverSaidas();
  
}

bool filtro(){
  t0=t;
  t=millis();
//se o tempo tRuido já tiver passado retorna true
  return ((t - t0) > tRuido);

}

void circuitoSequencial(){
  if(filtro()){ //filtro do ruido
  flipflopD(D1, &Q1);
  flipflopD(D0, &Q0);
  }
}

                        
void circuitoCombinatorio(){
  FES();
  FS();
}

void flipflopD(bool D, bool *Q){
  *Q=D;
}

void FES(){ //modelo Moorey-Mealay
  D1= F^Q1^Q0;
  D0= !Q0;
    
    
}
                        
void FS(){
  
  S3 = 1;  
  S2 = (!F && Q1) ||(F && !Q1); 
  S1 = !Q0; 
  S0 = 1;  
      
}
void lerEntradas(){
  
  F=digitalRead(f);
  
}
void escreverSaidas(){
  
  digitalWrite(LED_S3,S3);
  digitalWrite(LED_S2,S2);
  digitalWrite(LED_S1,S1);
  digitalWrite(LED_S0,S0);

}
