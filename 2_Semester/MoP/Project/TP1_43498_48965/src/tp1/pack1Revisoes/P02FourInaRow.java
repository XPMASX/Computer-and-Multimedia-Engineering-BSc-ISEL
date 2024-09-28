package tp1.pack1Revisoes;

import java.util.Scanner;

public class P02FourInaRow {

    /**
     * Shows (prints) the board on the console
     *
     * @param board The board
     */
    private static void showboard(char[][] board) {
        // ciclo que percorre a grelha e adiciona a string "linha" os caracteres de cada posição
        for(int i=0;i<6;i++){
            String linha="";
            for(int j=0;j<7;j++){
                linha = linha + (board[j][i]) + " ";
            }
            System.out.println(linha);
        }
        System.out.println("");
    }

    /**
     * Puts one piece for the received player. First asks the user to choose one
     * column, then validates it and repeat it until a valid column is chosen.
     * Finally, puts the player character on top of selected column.
     *
     * @param player   The player: 'A' or 'B'. Put this character on the board
     * @param board    The board
     * @param keyboard The keyboard Scanner
     * @return The column selected by the user.
     */
    private static int play(char player, char[][] board, Scanner keyboard) {

        System.out.println("Escolha uma coluna entre 1 e 7 em que seja possivel jogar");
        //fazemos col-1 pois as listas comecam no 0 mas o utilizador vai introduzir numeros a partir do 1 entao coluna -1
        int col = keyboard.nextInt()-1,linha=0;

        while(col<0 || col>6 ){
            System.out.println("Escolha uma coluna entre 1 e 7 em que seja possivel jogar");
            col = keyboard.nextInt()-1;
        }
        //se na coluna escolhida a primeira linha for diferente de 0 quer dizer que esta cheia logo pede um numero outra vez ao utilizador
        while(board[col][0]!=0){
            System.out.println("Escolha uma coluna vazia");
            col = keyboard.nextInt()-1;
        }
        //ciclo que descobre qual a linha vazia
        for(int i=0;i<6;i++){
            if(board[col][i]==0){
                linha = i;
            }
        }
        board[col][linha]=player;

        return col;
    }

    /**
     * Checks if the player, with the character on top on the received column, won
     * the game or not. It will get the top move on that column, and check if there
     * are 4 pieces in a row, in relation to that piece and from the same player.
     * Returns true is yes, false is not.
     *
     * @param board The board
     * @param col   The last played column
     * @return True is that player won the game, or false if not.
     */
    private static boolean lastPlayerWon(char[][] board, int col) {
        // VERTICAL
        for(int lin = 0; lin<6; lin++){
            for (int coluna = 0;coluna < 7-3;coluna++){
                if ((board[coluna][lin] == 'A'  &&
                        board[coluna+1][lin] == 'A'  &&
                        board[coluna+2][lin] == 'A'  &&
                        board[coluna+3][lin] == 'A') ||
                        board[coluna][lin] == 'B' &&
                                board[coluna+1][lin] == 'B' &&
                                board[coluna+2][lin] == 'B' &&
                                board[coluna+3][lin] == 'B'  ){
                    return true;}
            }
        }
        //HORIZONTAL
        for(int lin = 0; lin<6-3; lin++){
            for (int coluna = 0;coluna < 7;coluna++){
                if ((board[coluna][lin] == 'A'  &&
                        board[coluna][lin+1] == 'A'  &&
                        board[coluna][lin+2] == 'A'  &&
                        board[coluna][lin+3] == 'A') ||
                        board[coluna][lin] == 'B' &&
                                board[coluna][lin+1] == 'B' &&
                                board[coluna][lin+2] == 'B' &&
                                board[coluna][lin+3] == 'B'  ){
                    return true;}
            }
        }
        //DIAGONAL positiva
        for(int lin = 0; lin<6-3; lin++){
            for (int coluna = 0;coluna < 7-3;coluna++){
                if ((board[coluna][lin] == 'A'  &&
                        board[coluna+1][lin+1] == 'A'  &&
                        board[coluna+2][lin+2] == 'A'  &&
                        board[coluna+3][lin+3] == 'A') ||
                        board[coluna][lin] == 'B' &&
                                board[coluna+1][lin+1] == 'B' &&
                                board[coluna+2][lin+2] == 'B' &&
                                board[coluna+3][lin+3] == 'B'  ){
                    return true;}
            }
        }
        //DIAGONAL negativa
        for(int lin = 0; lin<6; lin++){
            for (int coluna = 0;coluna < 7-3;coluna++){
                //System.out.println(lin+" "+coluna);
                if ((board[coluna][lin] == 'A'  &&
                        board[coluna+1][lin-1] == 'A'  &&
                        board[coluna+2][lin-2] == 'A'  &&
                        board[coluna+3][lin-3] == 'A') ||
                        board[coluna][lin] == 'B' &&
                                board[coluna+1][lin-1] == 'B' &&
                                board[coluna+2][lin-2] == 'B' &&
                                board[coluna+3][lin-3] == 'B'  ){
                    return true;}
            }
        }
        return false;
    }

    /**
     * Check if there are at least one free position on board.
     *
     * @param board The board
     * @return True if there is, at least, one free position on board
     */
    private static boolean existsFreePlaces(char[][] board) {
        // Se todas as colunas no topo forem diferente de 0 devolve false ou seja não existe jogadas possiveis originando empate
        if (board [0][0] != 0 && board [1][0] != 0 && board [2][0] != 0 &&
                board [3][0] != 0 && board [4][0] != 0 && board [5][0] != 0 &&
                board [6][0] != 0){
            return false;
        }
        return true;
    }

    /**
     * Main method - this method should not be changed
     */
    public static void main(String[] args) {
        final int NCOLs = 7;
        final int NROWs = 6;

        // program variables
        Scanner keyboard = new Scanner(System.in);
        char[][] board = new char[NCOLs][NROWs];
        char winner = ' ';

        // show empty board
        showboard(board);

        // game cycle
        do {
            int col = play('A', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'A';
                break;
            }
            if (!existsFreePlaces(board))
                break;

            col = play('B', board, keyboard);
            showboard(board);
            if (lastPlayerWon(board, col)) {
                winner = 'B';
                break;
            }

        } while (existsFreePlaces(board));

        // show final result
        switch (winner) {
            case ' ':
                System.out.println("We have a draw....");
                break;
            case 'A':
                System.out.println("Winner: Player A. Congratulations...");
                break;
            case 'B':
                System.out.println("Winner: Player B. Congratulations...");
                break;
        }

        // close keyboard
        keyboard.close();
    }
}
