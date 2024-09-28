import java.util.Scanner;

public class TP2_01_MostrarNumerosEntre {

 public static void main(String[] args) {

  Scanner keyboard = new Scanner(System.in);

  System.out.println("O programa vai mostrar os numeros entre dois valores introduzidos pelo utilizador");

  System.out.print("Introduza o primeiro valor -> ");

  int val1 = keyboard.nextInt();

  System.out.print("Introduza o segundo valor -> ");

  int val2 = keyboard.nextInt();

  int i=1, aux=0 ;

    keyboard.close();

    if(val1>val2){
      aux=val1;
      val1=val2;
      val2=aux;
    }

  System.out.println(String.format("O valores entre %d e %d sao: ", val1, val2));

  for (i=1;val1!=val2+1;i++ ) {

    System.out.print(" " + val1);
    val1++;
    if(i%10==0){
    System.out.println("\n");
    }

  }
  }
}
