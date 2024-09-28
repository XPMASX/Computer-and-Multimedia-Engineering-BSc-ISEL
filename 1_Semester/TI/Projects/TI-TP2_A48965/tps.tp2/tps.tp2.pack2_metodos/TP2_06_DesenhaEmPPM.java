import java.util.Scanner;

import java.io.*;

public class TP2_06_DesenhaEmPPM{

  private static int dimx;
  private static int dimy;

public static void main(String[] args) {

System.out.println("\nPrograma para desenhar retangulos e triangulos");
Scanner keyboard = new Scanner(System.in);

System.out.println("Indique dados da imagem");
System.out.println("Nome: ");
String filename = keyboard.nextLine();
int n =0;

System.out.println("Indique as dimensoes da imagem");
 dimx = keyboard.nextInt();
 dimy = keyboard.nextInt();

System.out.println("Indique a cor do background");
int r = keyboard.nextInt();
int g = keyboard.nextInt();
int b = keyboard.nextInt();

String background = r + " " + g + " " + b + " ";
String image= "";
for (int j = 0; j< dimy; j++){
  for(int i = 0; i< dimx;i++){
    image += background;

    }
    image += "\n";

  }

  char op= 'l';
do{
op= 'l';
    System.out.println("Indique se quer criar um retangulo, um triangulo ou terminar.\n");
while(op!='R' && op!='T' && op!='Q'){
    System.out.println("Para o retangulo selecione 'R'\nPara o triangulo selecione 'T'\nPara terminar selecione 'Q'");
  op = keyboard.next().charAt(0);
  op = Character.toUpperCase(op);
}
switch(op){

  case 'R':
    System.out.println("Indique as coordenadas do canto superior esquerdo do retangulo");
   int x_ret = keyboard.nextInt();
   int y_ret = keyboard.nextInt();
   System.out.println("Indique as dimensoes do retangulo");
   int comprimento = keyboard.nextInt();
   int largura = keyboard.nextInt();
    System.out.println("Indique a cor do retangulo");
   int r_ret = keyboard.nextInt();
   int g_ret = keyboard.nextInt();
   int b_ret = keyboard.nextInt();
   String rgb_ret= r_ret + " " + g_ret + " " + b_ret + " ";

   String new_image = drawRectangle(dimx,dimy,image,x_ret,y_ret,comprimento,largura,rgb_ret);

   if(new_image!=null){
     n++;
   writeToPPMFile(String.format("%s_%d.ppm", filename, n), dimx, dimy,new_image);
   System.out.println("Done...");
  }else{
   System.out.println("Dados do retangulo invalidos, a imagem nao foi formada\n");
  }
   break;

   case 'T':
   System.out.println("Indique as coordenadas do vertice reto do triangulo (x,y)");
    int x_tri = keyboard.nextInt();
    int y_tri = keyboard.nextInt();
   System.out.println("Indique as dimensoes do triangulo");
    int deltax = keyboard.nextInt();
    int deltay = keyboard.nextInt();
    System.out.println("Indique a cor do triangulo");
    int r_tri = keyboard.nextInt();
    int g_tri = keyboard.nextInt();
    int b_tri = keyboard.nextInt();
    String rgb_tri= r_tri + " " + g_tri + " " + b_tri+ " ";

    new_image = drawTriangle(dimx,dimy,image,x_tri,y_tri,deltax,deltay,rgb_tri);

    if(new_image!=null){
      n++;
    writeToPPMFile(String.format("%s_%d.ppm", filename, n), dimx, dimy,new_image);
      System.out.println("Done...");
    }else{
      System.out.println("Dados do triangulo invalidos, a imagem nao foi formada\n");
    }

   break;

   case 'Q':
    System.out.println("End...\n");
   break;

}

}while(op!='Q');

keyboard.close();

 }

public static String drawRectangle(int dimx,int dimy,String image, int x_ret, int y_ret, int comprimento, int largura, String rgb_ret){
  String new_image ="";
  if(image==null){
    for (int j = 0; j< dimy; j++){
      for(int i = 0; i< dimx;i++){
        new_image += rgb_ret;
      }
        new_image += "\n";
    }
    }else{

  int startPointX = x_ret;
  int endpointX = x_ret+comprimento;
  int startPointY = y_ret;
  int endpointY = y_ret+largura;

  if (startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy
  || startPointX<0 || comprimento<0 || startPointY<0 || largura<0){

    return null;
  }

  String background="";
  int p=0;
  for(int i=0;p<3;i++){
  if(image.charAt(i)==' ' ){
    background+=" ";
     p++;

  }else{
    background+=image.charAt(i);
  }
  }
  for(int y = 0; y<dimy; y++){
    for(int x = 0; x< dimx; x++){
      if (x >= startPointX && x< endpointX && y >= startPointY && y < endpointY)  {

            new_image +=  rgb_ret;
          }else{
            new_image+= background;
          }
        }
          new_image+="\n";
        }
      }
  return new_image;
}

public static String drawTriangle(int dimx,int dimy,String image, int x_tri, int y_tri, int deltax, int deltay, String rgb_tri){
  int startPointX = x_tri;
  int startPointY = y_tri;
  int endpointX = 0;
  int endpointY = 0;
  StringBuilder new_image = new StringBuilder();
  String background="";
  int p=0;

  if(endpointX<0 || endpointY<0 || startPointX<0 || startPointY<0 ||
  startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy  ){
    return null;
  }

  for(int i=0;p<3;i++){
  if(image.charAt(i)==' ' ){
    background+=" ";
     p++;
   }else{
     background+=image.charAt(i);
   }
 }
 rgb_tri= " "+ rgb_tri;
 background= " "+ background;


  if(deltax>0 && deltay>0){
   endpointX = startPointX+deltax;
   endpointY = y_tri+deltay;
   int j=0;
   String rgb=rgb_tri;

   if(endpointX<0 || endpointY<0 || startPointX<0 || startPointY<0 ||
   startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy  ){
     return null;
   }

   for(int y = 0; y<dimy; y++){
     for(int x = 0; x< dimx; x++){
       if (x == startPointX && y >= startPointY && y < endpointY )  {
             new_image.append(rgb);
             rgb+=rgb_tri;
             x+=j;
             j++;
           }else{
               new_image.append(background);
             }
           }
           new_image.append("\n");
   }

 }else if(deltax<0 && deltay>0){
   endpointX = x_tri-deltax;
   endpointY = y_tri+deltay;
   int j=0;
   String rgb=rgb_tri;

   if(endpointX<0 || endpointY<0 || startPointX<0 || startPointY<0 ||
   startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy  ){
     return null;
   }

   for(int y = 0; y<dimy; y++){
     for(int x = 0; x< dimx; x++){
       if (x == startPointX && y >= startPointY && y < endpointY )  {
             new_image.append(rgb);
             rgb+=rgb_tri;
             x+=j;
             j++;
             startPointX--;
           }else{
               new_image.append(background);
             }
           }
           new_image.append("\n");
   }

 }else if(deltax>0 && deltay<0){
   endpointX = x_tri+deltax;
   endpointY = y_tri-deltay;
   startPointY=y_tri;

   int j=deltax-1;

   if(endpointX<0 || endpointY<0 || startPointX<0 || startPointY<0 ||
   startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy  ){
     return null;
   }

   for(int y = 0; y<dimy; y++){
     for(int x = 0; x< dimx; x++){
       if (x == startPointX && y >= startPointY && y < endpointY )  {
         for(int i =0;i <deltax;i++){
           new_image.append(rgb_tri);
         }
             x+=j;
             j--;
             deltax--;
           }else{
               new_image.append(background);
             }
           }
           new_image.append("\n");;
   }

 }else if(deltax<0 && deltay<0){
   endpointX = x_tri-deltax;
   endpointY = y_tri-deltay;
   startPointY=y_tri;
   deltax=-deltax;
   int j=deltax-1;

   if(endpointX<0 || endpointY<0 || startPointX<0 || startPointY<0 ||
   startPointX>=dimx || endpointX>dimx || startPointY>=dimy || endpointY>dimy  ){
     return null;
   }

   for(int y = 0; y<dimy; y++){
     for(int x = 0; x< dimx; x++){
       if (x == startPointX && y >= startPointY && y < endpointY )  {
         for(int i =0;i <deltax;i++){
           new_image.append(rgb_tri);
         }
             x+=j;
             j--;
             deltax--;
             startPointX++;
           }else{
               new_image.append(background);
             }
           }
           new_image.append("\n");;
   }
}
 return new_image.toString();
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
