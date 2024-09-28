import java.util.Scanner;

public class TP2_03_TrianguloCardinais {

public static void main(String[] args) {

  Scanner keyboard = new Scanner(System.in);

  System.out.println("\n O programa vai pedir ao utilizador o numero de linhas em que vao ser introduzidos mais um cardinal que na linha anterior \n");

  System.out.print("Introduza o numero de linhas -> ");

  int n = keyboard.nextInt();

  int i,p,l=1;

  keyboard.close();

  for (i=1;i<n+1;i++){
    int space=50-i;
    String format = "%" + space + "c";

    System.out.printf(format,' ');

    for (p=0;p<l;p++){
      System.out.print("#");
    }
    l=l+2;
    System.out.print("\n");
  }
 }
}
