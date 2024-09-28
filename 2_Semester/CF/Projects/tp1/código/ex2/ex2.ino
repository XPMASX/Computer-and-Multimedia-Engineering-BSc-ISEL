#define A2 A5
#define A1 2
#define A0 3
#define B2 4
#define B1 5
#define B0 6
#define SEL 7
#define LED_Cyout 8
#define LED_S2 9
#define LED_S1 10
#define LED_S0 13
#define LED_Flag 12
#define LED_Zero 11
bool a2,a1,a0,b2,b1,b0,cyout,s2,s1,s0,zero,sel,flag,bwout,bwin,cyin;

void setup()
{
  //pinMode(A2, INPUT);
  pinMode(A1, INPUT);
  pinMode(A0, INPUT);
  pinMode(B2, INPUT);
  pinMode(B1, INPUT);
  pinMode(B0, INPUT);
  pinMode(SEL, INPUT);
  pinMode(LED_Cyout, OUTPUT);
  pinMode(LED_S2, OUTPUT);
  pinMode(LED_S1, OUTPUT);
  pinMode(LED_S0, OUTPUT);
  pinMode(LED_Flag, OUTPUT);
  pinMode(LED_Zero, OUTPUT);
  Serial.begin(9600);

}

void loop()
{
  lerEntradas();
  //subtractor1bit(false,a0,b0,&bwout,&s0);
  //subtractor3bits(a2,a1,a0,b2,b1,b0,false,&bwout,&s2,&s1,&s0);
  somador3bits(a2,a1,a0,b2,b1,b0,false,&cyout,&s2,&s1,&s0);
  //somador1bit(false,a0,b0,&cyout,&s0);
  zerO();
  escreverSaidas();
}


void lerEntradas(){
  a2=analogRead(A2);
  a1=digitalRead(A1);
  a0=digitalRead(A0);
  b2=digitalRead(B2);
  b1=digitalRead(B1);
  b0=digitalRead(B0);
  sel=digitalRead(SEL);
}

void escreverSaidas(){
  
  digitalWrite(LED_Cyout,cyout);
  Serial.println(cyout);
  digitalWrite(LED_S2,s2);
  digitalWrite(LED_S1,s1);
  digitalWrite(LED_S0,s0);
  digitalWrite(LED_Flag,flag);
  digitalWrite(LED_Zero,zero);

}

void somador1bit(bool cyin, bool a, bool b, bool *cyout, bool *s){
 *cyout=(((b && cyin) || (a && cyin) || (a && b)) && sel)|| (((b && cyin) || (!a && cyin) || (!a && b)) && !sel);
 *s= a^b^cyin;
}

void somador3bits(bool a2, bool a1, bool a0, bool b2, bool b1, bool b0,
                  bool cyin, bool *cyout, bool *s2, bool *s1, bool *s0){
  
  bool cy0,cy1;
  
  somador1bit(false,a0,b0,&cy0,s0);
  somador1bit(cy0,a1,b1,&cy1,s1);
  somador1bit(cy1,a2,b2,cyout,s2);
  
}


void zerO(){
    zero = !s2 && !s1 && !s0 && !cyout;
    flag = cyout^cyin;
  }
