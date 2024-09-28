package tp1.pack2Livros;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Arrays;

/**
 * Classe que deverá suportar um livro
 */
public class Livro {

    // Título do livro
    private String titulo;

    // número de páginas
    private int numPaginas;

    // preço do livro
    private float preco;

    // array de autores, este array não deve ter nulls
    private String[] autores;

    /**
     * Deve criar um novo livro com os dados recebidos. O título não deve ser
     * null nem vazio. O número de páginas não pode ser menor que 1. O preço não
     * pode ser negativo. O array de autores não deve conter nem nulls e deve
     * conter pelo menos um autor válido. Não pode haver repetições dos nomes
     * dos autores, considera-se os nomes sem os espaços extra (ver
     * removeExtraSpaces). Este método deve utilizar os métodos auxiliares
     * existentes. Em caso de nome inválido deve lançar uma excepção de
     * IllegalArgumentException com a indicação do erro ocorrido
     */
    public Livro(String titulo, int numPaginas, float preco, String[] autores) {

        //titulo
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
        this.titulo = titulo;

        // número de paginas
        if (numPaginas < 1)
            throw new IllegalArgumentException("O número de páginas não pode ser negativo");
        this.numPaginas = numPaginas;

        //preço
        if (preco < 0)
            throw new IllegalArgumentException("O preço não pode ser negativo");
        this.preco = preco;

        //array de autores
        if(autores.length < 1) throw new IllegalArgumentException("Tem que haver pelo menos 1 autor");
        if(validarNomes(autores)) throw new IllegalArgumentException("O array não pode conter elementos null");
        if(!haRepeticoes(autores)) throw new IllegalArgumentException("O array de autores contêm autores repetidos");
        this.autores = autores;

    }

    /**
     * Devolve o título do livro
     */
    public String getTitulo() {

        return this.titulo;
    }

    /**
     * Devolve o número de páginas do livro
     */
    public int getNumPaginas() {

        return this.numPaginas;
    }

    /**
     * Devolve o preço do livro
     */
    public float getPreco() {

        return this.preco;
    }

    /**
     * Devolve uma cópia do array de autores do livro
     */
    public String[] getAutores() {

        return this.autores;
    }

    /**
     * Deve devolver true se o array conter apenas nomes válidos. Um nome é
     * válido se conter pelo menos uma letra (Character.isLetter) e só conter
     * letras e espaços (Character.isWhitespace). Deve chamar validarNome.
     */
    public static boolean validarNomes(String[] nomes) {
        //percoreemos o array de strings
        for (int i = 0; i < nomes.length; i++) {
            //chamamos o método validarNome. caso falso, retornamos false
           if(!validarNome(nomes[i])) return false;
        }
        return true;
    }

    /**
     * Um nome válido se não for null e não conter pelo menos uma letra
     * (Character.isLetter) e só conter letras e espaços
     * (Character.isWhitespace)
     */
    public static boolean validarNome(String nome) {
        if (nome != null) {
            // percorremos a string nome
            for (int i = 0; i < nome.length(); i++) {
                //quando não é letra, ou não letra e não espaço, retorna false
                if (!Character.isLetter(nome.charAt(i)) || !(Character.isLetter(nome.charAt(i)) && !Character.isWhitespace(nome.charAt(i)))) return false;
            }
        }
        return true;
    }

    /**
     * Recebe um nome já previamente validado, ou seja só com letras ou espaços.
     * Deve devolver o mesmo nome mas sem espaços (utilizar trim e
     * Character.isWhitespace) no início nem no fim e só com um espaço ' ' entre
     * cada nome. Deve utilizar um StringBuilder para ir contendo o nome já
     * corrigido
     */
    public static String removeExtraSpaces(String nome) {
        StringBuilder nome_final = new StringBuilder();
        // retiramos os espaços á esquerda da string e a direita
        String novo_nome = nome.trim();
        // percorremos o novo nome sem os espaços
        for (int i = 0; i < novo_nome.length(); i++) {
            // se é um espaço e for igual ao caracter anterior, não faz append
            if(Character.isWhitespace(novo_nome.charAt(i)) && novo_nome.charAt(i) == novo_nome.charAt(i-1)) continue;
            nome_final.append(novo_nome);
        }
        return nome_final.toString();
    }

    /**
     * Método que verifica se há elementos repetidos. O array recebido não
     * contém nulls.
     */
    public static boolean haRepeticoes(String[] elems) {
        //percorremos o array de strings
        for (int i = 0; i < elems.length; i++) {
            //percorremos o array de strings a começar do i para a frente
            for (int j =i+1; j < elems.length; j++) {
                //caso houver repetidos, returnamos false (chamamos o removeExtraSpaces para os arrays de string)
                if(removeExtraSpaces(elems[i]).equals(removeExtraSpaces(elems[j]))) return false;
            }
        }
        return true;
    }

    /**
     * Devolve true se o autor recebido existe como autor do livro. O nome
     * recebido não contém espaços extra.
     */
    public boolean contemAutor(String autorNome) {
        //percorremos o array de autores
        for (int i = 0; i < this.autores.length; i++) {
            // caso o array de autores tiver o autorNome, retornamos true
            if(autorNome.equals(this.autores[i])) return true;
        }
        return false;
    }

    /**
     * Devolve uma string com a informação do livro (ver outputs desejados)
     */
    public String toString() {
        // criamos a string do output pretendido
        return this.titulo + ", " + this.numPaginas + "p " + this.preco + " " + Arrays.toString(this.autores);
    }

    /**
     * Deve mostrar na consola a informação do livro precedida do prefixo
     */
    public void print(String prefix) {
        System.out.println(prefix + toString());
    }

    /**
     * O Livro recebido é igual se tiver o mesmo título que o título do livro
     * corrente
     */
    public boolean equals(Livro l) {
        return (l.titulo.equals(this.titulo));
    }

    /**
     * main
     */
    public static void main(String[] args) {

        // constructor e toString
        Livro l = new Livro("Viagem aos Himalaias", 340, 12.3f, new String[]{"João Mendonça", "Mário Andrade"});
        System.out.println("Livro -> " + l);
        l.print("");
        l.print("-> ");
        System.out.println();

        // contém autor
        String autorNome = "Mário Andrade";
        System.out.println("Livro com o autor " + autorNome + "? -> " + l.contemAutor(autorNome));
        autorNome = "Mário Zambujal";
        System.out.println("Livro com o autor " + autorNome + "? -> " + l.contemAutor(autorNome));
        System.out.println();

        // equals
        System.out.println("Livro: " + l);
        System.out.println("equals Livro: " + l);
        System.out.println(" -> " + l.equals(l));

        Livro l2 = new Livro("Viagem aos Himalaias", 100, 10.3f, new String[]{"Vitor Záspara"});
        System.out.println("Livro: " + l);
        System.out.println("equals Livro: " + l2);
        System.out.println(" -> " + l.equals(l2));
        System.out.println();

        // testes que dão excepção - mostra-se a excepção

        // livro lx1
        System.out.println("Livro lx1: ");
        try {
            Livro lx1 = new Livro("Viagem aos Himalaias", -1, 12.3f, new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx1: " + lx1);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx2
        System.out.println("Livro lx2: ");
        try {
            Livro lx2 = new Livro("Viagem aos Himalaias", 200, -12.3f,
                    new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx2: " + lx2);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx3
        System.out.println("Livro lx3: ");
        try {
            Livro lx3 = new Livro(null, 200, -12.3f, new String[]{"João Mendonça", "Mário Andrade"});
            System.out.println("Livro lx3: " + lx3);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        System.out.println();

        // livro lx4
        System.out.println("Livro lx4: ");
        try {
            Livro lx4 = new Livro("Viagem aos Himalaias", 200, 12.3f,
                    new String[]{"João Mendonça", "Mário Andrade", "João Mendonça"});
            System.out.println("Livro lx4: " + lx4);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}

