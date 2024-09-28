#include <Wire.h>
#define RS 0x01
#define RW 0x02
#define EN 0x04
#define LUZ 0x08
#define ENDERECO 0x27



void setup() {
  Serial.begin(9600);
  Wire.begin();
  iniciaDisplay();
  setCursorAdress(1,0);
  printString("1 2 3 4 5 6 7 8 9 10");
  //clearDisplay();
}

void loop() {
}


void escreverDados4(byte quatroBits) {
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ| RS);
  Wire.endTransmission();
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ | RS | EN );
  Wire.endTransmission();
  delayMicroseconds(1); // enable ativo >450ns
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ | RS);
  Wire.endTransmission();
  delayMicroseconds(40); // tempo > 37us para comando fazer efeito}
}

void escreverDados8(byte oitoBits){
    escreverDados4((oitoBits >> 4) & 0xF);
    escreverDados4(oitoBits & 0xF);
      
  }

  void findI2CDevices() {
  const int MAX= 128; byte endereco[MAX]; byte identificador[MAX];
  int nDevices= 0;
  for (int i= 0 ; i<MAX ; ++i) {
    identificador[nDevices]= readRegisto8bits((byte)i, 0x0F); // 0x0F – registo que contém o identificador
    if (identificador[nDevices]!= 255)
      endereco[nDevices++]= (byte) i;
  }
  Serial.print("Detetados "); Serial.print(nDevices); Serial.println(" dispositivos I2C.");
  Serial.println("Endereço - Identificador");
  for (int i= 0 ; i <nDevices ; ++i) {
    Serial.print(endereco[i], 16);
    Serial.print("h - ");
    Serial.print(identificador[i], 16);
    Serial.println("h");
  }
 }


byte readRegisto8bits(byte addressI2C, byte addressReg){
  Wire.beginTransmission(addressI2C);
  Wire.write(addressReg);
  Wire.endTransmission();
  Wire.requestFrom(addressI2C, (byte) 1);
  byte valor= Wire.read();
  Wire.endTransmission();
  return valor;
}

void escreverComandos4(byte quatroBits) {
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ);
  Wire.endTransmission();
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ | EN);
  Wire.endTransmission();
  delayMicroseconds(10); // enable ativo >450ns
  Wire.beginTransmission(ENDERECO);
  Wire.write((quatroBits << 4) | LUZ);
  Wire.endTransmission();
  delayMicroseconds(400); // tempo > 37us para comando fazer efeito}
}

void escreverComandos8(byte oitoBits){
    escreverComandos4((oitoBits >> 4) & 0xF);
    escreverComandos4(oitoBits & 0xF);
  }

void setCursorAdress(int linha, int coluna){
    escreverComandos8(0x80 | linha << 6 | coluna); 
    //escreverComandos4(0x80 | linha << 6);
    //escreverComandos4(coluna);
    delayMicroseconds(150);


  }

void printChar(char c){
  escreverDados8(c);
 }

void printString(String s){
  for(int i=0; s[i] != '\0';i++){
    printChar(s[i]);
  
    }
}

void clearDisplay(){
  escreverComandos4(0x0);
  escreverComandos4(0x1);
  delay(5);
  }

void iniciaDisplay(){
  display4bits();
  display2linhas();
  dispCursorBlink();
  inicializaCursor();
}

void display4bits(){
  delay(60); 
  escreverComandos4(0x3);
  delay(7);
  escreverComandos4(0x3);
  delayMicroseconds(250);
  escreverComandos4(0x3); 
  escreverComandos4(0x2);
 }

 void display2linhas(){
  escreverComandos4(0x2);
  escreverComandos4(0x8);
  delayMicroseconds(150);
  }

void dispCursorBlink(){
  escreverComandos4(0x0);
  escreverComandos4(0xF);
  delayMicroseconds(150);
  }


void inicializaCursor(){
  escreverComandos4(0x8);    
  escreverComandos4(0x0); 
  delayMicroseconds(150);
  }
