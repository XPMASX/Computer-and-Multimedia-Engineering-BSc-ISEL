import java.util.Scanner;

import java.io.*;

public class TP3_01_Palindromo{

  public static void main (String[] args){
    System.out.println("Programa para verificar se uma palavra e palindromo ou nao");
    System.out.println("Indique a palavra");
    Scanner keyboard = new Scanner(System.in);
    String palavra = keyboard.nextLine();
    keyboard.close();

    if(palindromo(palavra)){
      System.out.println(palavra+" e um palindromo");
    }else{
        System.out.println(palavra+" nao e um palindromo");
      }

    }

  static boolean palindromo(String palavra){

        if(palavra.length() == 0 || palavra.length() == 1)
            return true;

        if(palavra.charAt(0) == palavra.charAt(palavra.length()-1))
           return palindromo(palavra.substring(1, palavra.length()-1));
       return false;

    }
  }
