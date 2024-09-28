import java.util.Scanner;

import java.io.*;

public class TP3_02_IdxPrimeiraOcorrencia{

  public static void main (String[] args){
    System.out.println("Programa para encontar a primeira posicao de um valor num array");
    System.out.println("Indique o valor");
    Scanner keyboard = new Scanner(System.in);
    int valor = keyboard.nextInt();
    int[] lista ={1,2,2,2,2,3,4,5};
    keyboard.close();

    int indice=0;
    int i = IdxPrimeiraOcorrencia(lista, valor,indice);

    if(i != -1){
      System.out.println("O valor " + valor + " existe na posicao -> "+ i);
    }else{
      System.out.println("O valor nao existe no array");
      }
    }

  	private static int IdxPrimeiraOcorrencia(int[] lista, int valor, int i) {

  		if (i >= lista.length)
  			return -1;

  		if (lista[i] == valor)
  			return i;

  		return IdxPrimeiraOcorrencia(lista, valor, i + 1);

  	}


}
