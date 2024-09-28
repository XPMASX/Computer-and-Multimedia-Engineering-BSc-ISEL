#include <Wire.h>
# define ENDERECO 0x77
short s_ac1, s_ac2, s_ac3, s_b1, s_b2, s_mb, s_mc, s_md;
long ac1, ac2, ac3, b1, b2, mb, mc, md;
short oss = 0;
unsigned short s_ac4, s_ac5, s_ac6;
unsigned long ac4, ac5, ac6;
unsigned long b4, b7;
long ut, up, x1, x2, b5, t, b6, x3, b3, p, a;

void setup(){
  Serial.begin(9600);
  Wire.begin();
  sensorTemperatura();
  sensorPressao();
  calcularAltitude();
  escreverSaidas();
}

void loop(){

}


void sensorTemperatura(){
  lerRegistosTemp();
  calcularUT();
  
  //lerUTSim();
  //lerUPSim();
 
  calcularTemperatura();

}

void sensorPressao(){
  lerRegistosTemp();
  calcularUP();
  calcularPressao();
  escreverSaidas();
  }

void lerRegistosTemp(){
  
  s_ac1= readRegisto16bits(ENDERECO, 0xAA);
  ac1 = (long) s_ac1;

  s_ac2 = readRegisto16bits(ENDERECO, 0xAC);
  ac2 = (long) s_ac2;
 
   
  s_ac3 = readRegisto16bits(ENDERECO, 0xAE); 
  ac3  = (long) s_ac3;
  
  
  s_ac4 = readRegisto16bits(ENDERECO, 0xB0);
  ac4 = (long) s_ac4;
  
  
  s_ac5 = readRegisto16bits(ENDERECO, 0xB2);
  ac5 = (long) s_ac5;
  
  
  s_ac6 = readRegisto16bits(ENDERECO, 0xB4);
  ac6 = (long) s_ac6;
  
  s_b1  = readRegisto16bits(ENDERECO, 0xB6);
  b1 = (long) s_b1;
 
  
  
  s_b2  = readRegisto16bits(ENDERECO, 0xB8);
  b2 = (long) s_b2;
  
  
  s_mb  = readRegisto16bits(ENDERECO, 0xBA);
  mb = (long) s_mb;
  
  
  s_mc  = readRegisto16bits(ENDERECO, 0xBC);
  mc = (long) s_mc;
   
  
  s_md  = readRegisto16bits(ENDERECO, 0xBE);
  md = (long) s_md;
  
  }

void writeReg8bits(byte endr, byte reg){
  Wire.beginTransmission(ENDERECO);
  Wire.write(reg);
  Wire.write(endr);
  Wire.endTransmission();
  
  }

void calcularUT(){
  writeReg8bits(0x2E, 0xF4);
  delay(4.5);
  
  byte MSB = readRegisto8bits(ENDERECO, 0xF6);
  byte LSB = readRegisto8bits(ENDERECO, 0xF7);
 
  ut = (MSB << 8) | LSB;
  }  
  
void calcularUP(){
  writeReg8bits(0x34|(oss<<6), 0xF4);
  delay(4.5);
  
  byte MSB = readRegisto8bits(ENDERECO, 0xF6);
  byte LSB = readRegisto8bits(ENDERECO, 0xF7);
  byte XLSB = readRegisto8bits(ENDERECO, 0xF8);

  up = (((long)MSB << 16) | (long)LSB << 8| (long)XLSB) >> (8-oss);
 
  }  
  

void calcularTemperatura(){
  x1 = (ut - ac6) * ac5 >> 15;
  x2 = (mc << 11) / (x1 + md);
  b5 = x1 + x2;
  t = (b5 + 8) >> 4;

}

void calcularPressao(){
  b6 = b5-4000;
  x1 = (b2 * (b6 * b6 >> 12))>>11;
  x2 = ac2 * b6 >>11;
  x3 = x2 + x1;
  b3 = (((ac1 * 4 + x3) << oss ) + 2) /4;
  x1 = ac3 * b6>>13;
  x2 = (b1 * (b6 * b6 >> 12))>>16;
  x3 = ((x1 + x2) + 2) >> 2;
  b4 =  ac4 * (x3 + 32768) >> 15;
  b7 = (up - b3) * (50000 >> oss);
  if (b7 < 0x80000000) {
    p = (b7*2)/b4;
    }else {
      p=(b7/b4)*2;
      }
   x1 = (p>>8) * (p>>8);
   x1 = (x1*3038) >> 16;
   x2 = (-7357 * p) >> 16;
   p += (x1+x2+3791) >> 4;
  }


void calcularAltitude(){
  float p0 = 101325;
  float base = p/p0;
  float expoente = pow(5.255,-1);
  a = 44330 * (1 -pow(base,expoente));
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

short readRegisto16bits(byte addressI2C,  byte addressReg){
  
  short high = readRegisto8bits(addressI2C, addressReg);
  short low  = readRegisto8bits(addressI2C, addressReg + 1);

  
  return (high << 8) | low;
}


void lerRegistosTempSim(){
  ac1 = 408;
  ac2 = -72;
  ac3 = -14383;
  ac4 = 32741;
  ac5 = 32757;
  ac6 = 23153;
  b1 = 6190;
  b2 = 4;
  mb = -32768;
  mc = -8711;
  md = 2868;
}

void lerUTSim(){ 
  ut = 27898;
}

void lerUPSim(){
  up = 23843;
}

void escreverSaidas(){
  
  Serial.print("temperatura: ");Serial.println(t*0.1);
  Serial.print("pressure: ");Serial.println(p);
  Serial.print("altitude: ");Serial.println(a);

  /*
  Serial.println(ac2);Serial.println(ac3);Serial.println(ac4);
  Serial.println(ac5);Serial.println(ac6);Serial.println(b1);Serial.println(b2);Serial.println(mb);
  Serial.println(mc);Serial.println(md);Serial.println(ut);Serial.println(t);
  */
  /*Serial.println(x2);Serial.println(b5);Serial.println(t);
  Serial.println(b6);Serial.println(x1);Serial.println(x2);Serial.println(x3);Serial.println(b3);
  Serial.println(x1);Serial.println(x2);Serial.println(x3);Serial.println(b4);Serial.println(b7);
  Serial.println(p);Serial.println(x1);Serial.println(x2);Serial.println(p);
*/
}
