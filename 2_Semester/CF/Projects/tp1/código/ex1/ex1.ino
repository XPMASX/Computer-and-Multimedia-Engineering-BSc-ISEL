#define A A0
#define B A1
#define F A2
#define AO A3
#define LED 7
bool a,b,f,ao,avaria;

void setup()
{
  pinMode(A, INPUT);
  pinMode(B, INPUT);
  pinMode(F, INPUT);
  pinMode(AO, INPUT);
  pinMode(LED, OUTPUT);
  Serial.begin(9600);
}

void loop()
{
  lerEntradas();
  funcaoDependente();
  ativarSaidas();
  Serial.print(a);
}

void lerEntradas(){
  a=digitalRead(A);
  b=digitalRead(B);
  f=digitalRead(F);
  ao=digitalRead(AO);
  Serial.print(a);
}

void funcaoDependente(){
   avaria = (a && !f && !ao) || (a && b && !f) || 
        (b && !ao && !f) || (!a && ao && f)|| 
        (!a && f && !b)  || (f && ao && !b);
}

void ativarSaidas(){
  digitalWrite(LED,avaria);
}
