import java.util.Scanner;

import java.io.*;

public class TP3_03_RemoveEspacosConsecutivos{

  public static void main (String[] args){
    System.out.println("Programa que vai eliminar da frase escrita pelo utilizador todos os espacos brancos a mais deixando apenas um");
    System.out.println("Escreva a frase");
    Scanner keyboard = new Scanner(System.in);
    String frase = keyboard.nextLine();
    keyboard.close();

    String nova_frase= removerEspacosConsecutivos(frase);

    System.out.println("A frase sem espacos e ->"+nova_frase);

    }

  static String removerEspacosConsecutivos(String frase){

    if (frase.length() == 1 && frase.charAt(0) == ' ' )
   {
       return " ";
   }

    if (frase.length() == 0)
   {
       return "";
   }

    if (frase.charAt(0) == ' ' && frase.charAt(1)== ' ')
{
    return removerEspacosConsecutivos(frase.substring(1));
}
return frase.charAt(0) + removerEspacosConsecutivos(frase.substring(1));
}

  }
