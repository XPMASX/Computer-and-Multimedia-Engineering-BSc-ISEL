import java.util.Scanner;

public class TP2_02_CapitalFirstChar {

 public static void main(String[] args) {

  Scanner keyboard = new Scanner(System.in);

  System.out.println("O programa vai transformar a frase introduzida pelo utilizador mudando a primeira letra de cada palavra para maiuscula e as restantes minusculas\n");

  System.out.print("Introduza a frase -> ");

  String frase = keyboard.nextLine();

  String frasef="";

  int i,p=1;

  char space= ' ';

    keyboard.close();

  for (i=0;i<frase.length();i++ ) {

    char c=frase.charAt(i);

    if(i==0){
      c= Character.toUpperCase(c);
      frasef= frasef+c;
      continue;
    }

    if (Character.isDigit(c)==true && p==i){
      frasef= frasef+c;
      i++;
      p++;
      c=frase.charAt(i);
      if(Character.isLetter(c)==true){
        c= Character.toUpperCase(c);
        frasef= frasef+c;
        continue;
      }
      else{
        frasef= frasef+c;
        continue;

    }
    }

      if(c==space){
        frasef= frasef+c;
        i++;
        c=frase.charAt(i);
        if(Character.isLetter(c) && Character.isLetter(frase.charAt(i+1))  && Character.isLetter(frase.charAt(i+2)))
          c= Character.toUpperCase(c);
          frasef= frasef+c;
          continue;
      }
      if (i>=frase.length()){
        continue;
      }

          c=frase.charAt(i);
          c= Character.toLowerCase(c);
          frasef= frasef+c;

  }

  System.out.println(frasef);
}
}
