import java.util.Scanner;

import java.io.*;

public class TP2_04_PPMCross{

  public static final String RED = "255 0 0 ";
  public static final String WHITE = "255 255 255 ";

  public static void main (String[] args){

    System.out.println("Indique a dimensao da imagem");
    Scanner keyboard = new Scanner(System.in);
    int dim = keyboard.nextInt();
    StringBuilder image = new StringBuilder();
    for (int y=0; y<dim;y++){
      for (int x=0; x<dim;x++){
        if ((x<(dim)/3 && y<dim/3) || (x>(dim*2)/3 && y<dim/3) || (x>(dim*2)/3 && y>(dim*2)/3) || (x<(dim)/3 && y>(dim*2)/3) ){

          image.append(WHITE);
        }else{

          image.append(RED);
        }


      }
      image.append("\n");


    }
    writeToPPMFile("CROSS.ppm",dim,dim,image.toString());
    keyboard.close();
    System.out.println("Done...");


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
