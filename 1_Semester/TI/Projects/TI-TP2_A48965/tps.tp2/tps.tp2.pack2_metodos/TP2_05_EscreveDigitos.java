import java.util.Scanner;

import java.nio.ByteBuffer;

public class TP2_05_EscreveDigitos {

public static void main(String[] args) {

  Scanner keyboard = new Scanner(System.in);

  System.out.println("\n O programa vai pedir ao utilizador um numero inteiro positivo e vai escrever por ordem os seus digitos\n");

  System.out.print("Introduza o numero ");

  int numero = keyboard.nextInt();
  while (numero <= 0 && numero % 1 == 0){
    System.out.print("Introduza um numero inteiro maior que 0 ");

    numero = keyboard.nextInt();
  }
  keyboard.close();

  String output_final = mostrarDigitos(numero);
  System.out.println(output_final);
}
 public static String getDigitoEmString(byte n){

   String Digito_String = " ";
   switch (n) {
     case 1:
      Digito_String= " um";
      break;

     case 2:
      Digito_String= " dois";
      break;

     case 3:
      Digito_String= " tres";
      break;

     case 4:
      Digito_String= " quatro";
      break;

     case 5:
      Digito_String= " cinco";
      break;

     case 6:
      Digito_String= " seis";
      break;

     case 7:
      Digito_String= " sete";
      break;

     case 8:
      Digito_String= " oito";
      break;

     case 9:
      Digito_String= " nove";
      break;

     case 0:
      Digito_String= " zero";
      break;
   }

   return Digito_String;

 }

 public static int getDigito(int numero, int idxDigito){
   String number = String.valueOf(numero);
   int digito = Character.digit(number.charAt(idxDigito), 10);


   return digito;
 }

 public static int getNumDigitos(int numero){
   String number = String.valueOf(numero);
   int length = number.length();

  return length;

 }
 public static String mostrarDigitos(int numero){
   int lenght= getNumDigitos(numero);
   String digitos_string= "O numero " +numero + " e composto pelos digitos: ";
   for(int idxDigito=0; idxDigito<lenght; idxDigito++){

     int digito=getDigito(numero, idxDigito);
     byte n= (byte)digito;
     String numString = getDigitoEmString(n);
     digitos_string= digitos_string + numString ;
     if (idxDigito<lenght-2){
       digitos_string= digitos_string + " ,";
     }if (idxDigito == lenght-2){
       digitos_string= digitos_string + " e";
     }
     if (idxDigito == lenght-1){
       digitos_string= digitos_string + ".";
     }
  }

  return digitos_string;
 }

}
