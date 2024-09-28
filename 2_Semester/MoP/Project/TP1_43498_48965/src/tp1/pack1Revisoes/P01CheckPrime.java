package tp1.pack1Revisoes;

import java.util.Scanner;

public class P01CheckPrime{

    /**
     * Main, método de arranque da execução
     */
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int n;
        //pede ao utlizador um numero enquanto o numero dado for menor que 0
        do {
            n = integerNumber(keyboard);
            //se n for 0 lança IllegalArgumentException
            if(n==0) {
                throw new IllegalArgumentException("Cannot be 0");
            }
            //Se a função isPrime() devolver true então o numero é primo
        }while(n<0);
        if (isPrime(n)) System.out.println("The number "+n+" is prime");
        else System.out.println("The number "+n+" is NOT prime");
        keyboard.close();
    }

    public static boolean isPrime(int number) {
        int i = 2;
        /*enquanto o i for menor que o numero do utilizador a dividir por 2 verificar se o resto desse numero a dividir por i é 0
        se sim devolve false se não o ciclo continua até sair e devolver true*/
        while (i <= number/2){
            if (number % i == 0) return false;
            ++i;
        }
        return true;
    }

    static int integerNumber(Scanner keyboard) {
        boolean done = false;
        int number = 0;
        //ciclo que pede ao utilizador um numero inteiro maior que 0 e que só acaba quando esse for o caso
        do {
            System.out.println("Enter an integer number greater than 0 -> ");
            if (keyboard.hasNextInt()) {
                number = keyboard.nextInt();
                done = true;
            }
            else {
                System.out.println("That's not an integer!");
            }
            keyboard.nextLine();
        } while (!done);
        return number;
    }
}
