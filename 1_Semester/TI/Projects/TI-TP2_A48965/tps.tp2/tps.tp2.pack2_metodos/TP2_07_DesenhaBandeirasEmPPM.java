import java.util.Scanner;

import java.lang.*;

import java.io.*;

public class TP2_07_DesenhaBandeirasEmPPM{
  public static void main(String[] args) {
    System.out.println("\nPrograma para bandeiras");
    Scanner keyboard = new Scanner(System.in);

    char bandeira='l';

    do{
    System.out.println("Indique qual bandeira vai querer desenhar:");

    bandeira='l';
    while(bandeira!='P' && bandeira!='E' && bandeira!='A' && bandeira!='B' && bandeira!='F' && bandeira!='Q'){
      System.out.println("Portugal (P)\nEspanha (E)\nAlemanha (A)\nBelgica (B)\nFilipinas (F)\nQuit (Q)\n");
        bandeira = keyboard.next().charAt(0);
        bandeira = Character.toUpperCase(bandeira);
    }
    if(bandeira=='Q'){
      System.out.println("End...");
      System.exit(0);
    }
    int altura =0;
    while(altura<=0){
    System.out.println("Indique a altura da bandeira");
     altura = keyboard.nextInt();
    }
    switch(bandeira){

      case 'P':
      gerarBandeiraPortugal(altura);
      break;

      case 'A':
      gerarBandeiraAlemanha(altura);
      break;

      case 'E':
      gerarBandeiraEspanha(altura);
      break;

      case 'B':
      gerarBandeiraBelgica(altura);
      break;

      case 'F':
      gerarBandeiraFilipinas(altura);
      break;


    }
  }while(bandeira!='Q');

  keyboard.close();

}


  public static void gerarBandeiraPortugal(int altura){

    float aux=Math.round(altura*1.5);
    int dimx= Math.round(aux);
    int dimy= altura;
    String rgb_ret="255 0 0 ";
    String image = null;
    int x_ret = 0;
    int y_ret = 0;
    int comprimento = (dimx*2)/5;
    int largura = altura;


    image= TP2_06_DesenhaEmPPM.drawRectangle(dimx,dimy,image,x_ret , y_ret, comprimento, largura, rgb_ret);

    rgb_ret="0 255 0 ";

    String Portugal= TP2_06_DesenhaEmPPM.drawRectangle(dimx,dimy,image,x_ret , y_ret, comprimento, largura, rgb_ret);

    writeToPPMFile("Portugal.ppm", dimx, dimy, Portugal);

    System.out.println("Done...");


  }

  public static void gerarBandeiraEspanha(int altura){

    float aux=Math.round(altura*1.5);
    int dimx= Math.round(aux);
    int dimy= altura;
    String rgb_ret="255 0 0 ";
    String image = null;
    int x_ret = 0;
    int y_ret = (altura*1)/4;
    int comprimento = dimx;
    int largura = (altura*2)/4;


     image= TP2_06_DesenhaEmPPM.drawRectangle(dimx,dimy,image,x_ret , y_ret, comprimento, largura, rgb_ret);

     rgb_ret="241 191 0 ";



    String Espanha= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    writeToPPMFile("Espanha.ppm", dimx, dimy, Espanha);

    System.out.println("Done...");

  }

  public static void gerarBandeiraAlemanha(int altura){

    float aux=Math.round(altura*1.5);
    int dimx= Math.round(aux);
    int dimy= altura/2;
    String rgb_ret="0 0 0 ";
    String image = null;
    int x_ret = 0;
    int y_ret = (altura*1)/3;
    int comprimento = dimx;
    int largura = (y_ret/2)+1;

    image= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    rgb_ret="255 0 0 ";

    String Up= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    image = null;

    rgb_ret="255 255 0 ";

    image= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    rgb_ret="255 0 0 ";

    largura = (altura)/6;

    y_ret = 0;

    String Down= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    String Alemanha= Up+  Down;

    dimy=altura;

    writeToPPMFile("Alemanha.ppm", dimx, dimy, Alemanha);

    System.out.println("Done...");


  }

  public static void gerarBandeiraBelgica(int altura){

    float aux=Math.round(altura*1.5);
    int dimx= Math.round(aux);
    int dimy= altura;
    String black="0 0 0 ";
    String red = "255 0 0 ";
    String yellow = "255 255 0 ";
    String Belgica ="";

    for(int y=0;y<dimy;y++){
      for(int x=0;x<dimx;x++){
        if(x<=dimx/3){
          Belgica+=black;

        }else if (x>dimx/3 && x<(dimx*2)/3) {
          Belgica+=yellow;

        }else{
          Belgica+=red;
        }


      }
    }


    writeToPPMFile("Belgica.ppm", dimx, dimy, Belgica);

    System.out.println("Done...");


  }


  public static void gerarBandeiraFilipinas(int altura){

    float aux=Math.round(altura*1.5);
    int dimx= Math.round(aux);
    int dimy= altura/2;
    String rgb_ret="0 56 168 ";
    String image = null;
    int x_tri = 0;
    int y_tri = 0;
    int deltax = dimx/3;
    int deltay = dimy;
    int x_ret=0;
    int y_ret=0;
    int comprimento=0;
    int largura=0;


    image= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    String rgb_tri="255 255 255";

    String Up= TP2_06_DesenhaEmPPM.drawTriangle(dimx,dimy,image,x_tri,y_tri,deltax,deltay,rgb_tri);

    image = null;

    rgb_ret="206 17 38 ";

    image= TP2_06_DesenhaEmPPM.drawRectangle(dimx, dimy, image, x_ret, y_ret, comprimento, largura, rgb_ret);

    deltay= -deltay;

    String Down= TP2_06_DesenhaEmPPM.drawTriangle(dimx,dimy,image,x_tri,y_tri,deltax,deltay,rgb_tri);

    String Filipinas= Up+  Down;

    dimy=altura;

    writeToPPMFile("Filipinas.ppm", dimx, dimy, Filipinas);

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
