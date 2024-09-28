import java.util.Scanner;

import java.io.*;

public class TP2_08_DesenhaDegradeEmPPM{

public static void main(String[] args) {

System.out.println("\nPrograma para fazer degrade entre duas cores escolhidas pelo utilizador");
Scanner keyboard = new Scanner(System.in);

System.out.println("Indique dados da imagem");
System.out.println("Nome: ");
String fileName = keyboard.nextLine();

System.out.println("Indique as dimensoes da imagem");
int dimx = keyboard.nextInt();
int dimy = keyboard.nextInt();

System.out.println("Indique a cor da esquerda");
int r_left = keyboard.nextInt();
int g_left = keyboard.nextInt();
int b_left = keyboard.nextInt();

System.out.println("Indique a cor da direita");
double r_right = keyboard.nextInt();
double g_right = keyboard.nextInt();
double b_right = keyboard.nextInt();

desenhaDegrade(fileName, dimx, dimy, r_left, g_left, b_left, r_right, g_right, b_right);

keyboard.close();
}


public static void  desenhaDegrade(String fileName, int dimx, int dimy, int r_left, int g_left, int b_left, double r_right, double g_right, double b_right){
  double r_double=0;
  double g_double=0;
  double b_double=0;
  long r=0;
  long g=0;
  long b=0;
  String degrade="";
  String rgb="";


  for(int y=0;y<dimy;y++){
    for(int x=0;x<dimx;x++){
      r_double = r_left + x * ((r_right - r_left) / (dimx - 1));
      g_double = g_left + x * ((g_right - g_left) / (dimx - 1));
      b_double = b_left + x * ((b_right - b_left) / (dimx - 1));

      r=Math.round(r_double);
      g=Math.round(g_double);
      b=Math.round(b_double);


      rgb= r + " " + g + " " + b+ " ";

      degrade= degrade+ rgb ;

    }
    degrade+="\n";
  }

  System.out.println("Done...");

  writeToPPMFile(String.format("%s.ppm",fileName),dimx,dimy,degrade);


}



public static void writeToPPMFile(String fileName, int xPixeis, int yPixeis,
String content) {
try (PrintWriter pw = new PrintWriter(new File(fileName))) {
pw.println("P3"); // magic PPM P3 number
pw.println(xPixeis + " " + yPixeis); // nXPixels nYPixels
pw.println("255"); // max color value
pw.println(content); // image content
} catch (FileNotFoundException e) {
e.printStackTrace();
}

}

}
