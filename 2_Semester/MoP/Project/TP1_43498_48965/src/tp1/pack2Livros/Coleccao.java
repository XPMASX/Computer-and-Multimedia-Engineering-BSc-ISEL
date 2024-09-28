package tp1.pack2Livros;

import java.util.Arrays;

import static tp1.pack2Livros.Livro.haRepeticoes;
import static tp1.pack2Livros.Livro.validarNomes;

/**
 * Classe Colecca, deve conter a descrição de uma colecção, com título, seus
 * livros e editores
 */
public class Coleccao {

    // número máximo de obras de uma colecção
    private static int MAXOBRAS = 20;

    // prefixo usual
    public static final String GENERALPREFIX = "  ";

    // título da colecção
    private String titulo;

    // Array de livros, em que estas encontram-se sempre nos menores índices e
    // pela ordem de registo
    private Livro[] livros = new Livro[MAXOBRAS];

    // deverá conter sempre o número de livros na colecção
    private int numLivros = 0;

    // Editores, tem as mesmas condicionantes que array de autores na classe
    // livro
    private String[] editores;

    /**
     * Construtor; o título tem de ter pelo menos um caracter que não seja um
     * espaço (Character.isWhitespace); o array de editores devem ser pelo menos
     * um e têm as mesmas restrições que os autores dos livros; o array de
     * livros deve conter os mesmos sempre nos menores índices
     */
    public Coleccao(String titulo, String[] editores) {

        //titulo
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
        this.titulo = titulo;

        // array de editores
        if(editores.length < 1) throw new IllegalArgumentException("Tem que haver pelo menos 1 autor");
        if(validarNomes(editores)) throw new IllegalArgumentException("O array não pode conter elementos null");
        if(!haRepeticoes(editores)) throw new IllegalArgumentException("O array de autores contêm autores repetidos");
        this.editores = editores;
    }

    /**
     *
     */
    public String getTitulo() {

        return this.titulo;
    }

    /**
     * Obtem o número total de páginas da colecção
     */
    public int getNumPaginas() {
        int count = 0;
        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //fazemos o somatório do número de páginas de cada livro percorrido
           count += livros[i].getNumPaginas();
        }
        return count;
    }

    /**
     * Devolve o preço da colecção tendo em conta que as colecções com 4 ou mais
     * livros têm um desconto de 20% nos livros que custam pelo menos 10 euros e
     * que têm mais de 200 páginas
     */
    public float getPreco() {
        float count = 0;
        //caso a coleção tiver menos que 4 livros
        if (numLivros < 4) {
            //percorremos os livros
            for (int i = 0; i < numLivros; i++) {
                //fazemos o somatório do preço de cada livro percorrido
                count += livros[i].getPreco();
            }
            //caso a coleção ter 4 ou mais livros
        } else {
            //percorremos os livros
            for (int i = 0; i < numLivros; i++) {
                //se o livro tiver o custo de 10 ou mais e o número de páginas do mesmo livro for superior a 200
                if (livros[i].getPreco() >= 10 && livros[i].getNumPaginas() > 200) {
                    //é feito o somatório do preço com o desconto
                    count += (livros[i].getPreco() * 0.8f);
                } else {
                    // caso contrário, é feito o somatório sem o desconto
                    count += livros[i].getPreco();
                }
            }
        }
        return count;
    }

    /**
     * Adiciona um livro se puder e este não seja null e a colecção não ficar
     * com livros iguais. Deve utilzar o método getIndexOfLivro.
     */
    public boolean addLivro(Livro livro) {
        //caso não null ou não exceder o MAXOBRAS ou o livro pretendido não existe retorna false
        if(livro == null || numLivros >= MAXOBRAS || getIndexOfLivro(livro.getTitulo()) != -1) return false;
        // adicionamos o livro pretendido no array de livros no índice numLivros
        livros[numLivros] = livro;
        //incrementamos o numLivors
        numLivros++;
        return true;
    }

    /**
     * Devolve o index no array de livros onde estiver o livro com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfLivro(String titulo) {
        //percorremos o número de livros
        for (int i = 0; i < numLivros; i++) {
            //caso o titulo for igual a um título dos livros, retornamos o índice
            if(titulo.equals(livros[i].getTitulo())) return i;
        }
        //caso contrário, se não existir esse título, retornamos -1
        return -1;
    }

    /**
     * Remove do array o livro com o título igual ao título recebido. Devolve o
     * livro removido ou null caso não tenha encontrado o livro. Deve-se
     * utilizar o método getIndexOfLivro. Recorda-se que os livros devem ocupar
     * sempre os menores índices, ou seja, não pode haver nulls entre os livros
     */
    public Livro remLivro(String titulo) {
        //encontramos o índice do livro pretendido chamando o método getIndexOflivro
        int idx = getIndexOfLivro(titulo);
        //criamos uma instância do livro com parâmentros null
        Livro removeBook = null;

        // caso não tenha encontrado o livro, retornamos null
        if (idx == -1) return null;
        //caso contrário
        else {
            //guardamos o livro que é para remover no removeBook
            removeBook = livros[idx];
            //passamos o livro que é preteindido remover para null
            livros[idx] = null;

            //percorremos o número de livros apartir do índice do livro removido
            for (int i = idx; i < numLivros; i++) {
                //caso é o ultimo, metemo-lo a null
                if(i==livros.length-1)livros[i]=null;
                // caso contrário empurramos tudo para a esquerda e  decrementamos
                else {
                    livros[i] = livros[i + 1];
                    numLivros--;
                }
            }
        }
        return removeBook;
    }

    /**
     * Devolve o nº de obras de uma pessoa. A colecção deve contabilizar-se como
     * uma obra para os editores.
     */
    public int getNumObrasFromPerson(String autorEditor) {
        int count = 0;
        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //percorremos os autores dos livros
            for (int j = 0; j < livros[i].getAutores().length; j++) {
                //caso o autor pretendido for igual a um autor de um livro
                if(autorEditor.equals(livros[i].getAutores()[j])){
                    //incrementamos o count
                   count++;
                }
            }
        }
        //percorremos o array de editores
        for (int i = 0; i < editores.length; i++) {
            //se o autor pretendido for igual a um editor da coleção, incrementamos
            if(autorEditor.equals(editores[i])) count++;
        }
        return count;
    }

    /**
     * Devolver um novo array (sem nulls) com os livros de que a pessoa recebida
     * é autor
     */
    public Livro[] getLivrosComoAutor(String autorNome) {
        // criamos um array novo_livros do tipo Livro, com o tamanho das obras do autor sem a coleção
        Livro[] novo_livros = new Livro[getNumObrasFromPerson(autorNome)-1];
        //percorremos os livros do autor
        for (int i = 0; i < getNumObrasFromPerson(autorNome)-1; i++) {
            //percorremos os autores dos livros
            for (int j = 0; j < livros[i].getAutores().length; j++) {
                //se o autor pretendido for igual ao um autor de um livro
                if(autorNome.equals(livros[i].getAutores()[j])){
                    //adicionamos o livro ao array de Livros novos
                    novo_livros[i]=livros[i];
                }
            }
        }
        return novo_livros;
    }

    /**
     * Deve devolver uma string compatível com os outputs desejados
     */
    public String toString() {
        // criamos a string do output pretendido
        return "Colecção " +this.titulo + ", editores " + Arrays.toString(this.editores)  + " " +this.numLivros + " Livros " + getNumPaginas()+ "p, " + getPreco();
    }

    /**
     * Deve devolver um array, sem nulls, com todos os autores e editores
     * existentes na colecção. O resultado não deve conter repetições. Deve
     * utilizar o método mergeWithoutRepetitions
     */
    public String[] getAutoresEditores() {
        // criamos um array de strings
        String[] aux ;
        //guardamos o array dos editores no array de string criado
        aux = editores;
        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //guardamos no array de string criado a junção sem repetições (mergeWithoutRepetitions) do array de editores com os autores de cada livro
                aux = mergeWithoutRepetitions(aux, livros[i].getAutores());
        }
        return aux;
    }


    /**
     * Método que recebendo dois arrays sem repetições devolve um novo array com
     * todos os elementos dos arrays recebidos mas sem repetições
     */
    private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
        //criamos um array de strings com o tamanho dos arrays que é para juntar
        String[] c = new String[a1.length + a2.length];
        //adicionamos a c os conteudos do primeiro array
        for (int i = 0; i < a1.length; i++) {
            c[i] = a1[i];
        }
        //adicionamos a c os conteudos do segundo array
        for (int i = 0; i < a2.length; i++) {
            c[a1.length + i] = a2[i];
        }
        int len = c.length;
        //percorremos o array c e retiramos os repetidos
        for (int i = 0; i < len - 1; i++)
            for (int j = i + 1; j < len; j++)
                if (c[i].equals(c[j])) {
                    for (int k = j; k < len - 1; k++)
                        c[k] = c[k + 1];
                    --len;
                }
        //guardamos os elementos não repetidos num novo array de strings
        String[] r = new String[len];
        for (int i = 0; i < r.length; i++)
            r[i] = c[i];


        return r;
    }

    /**
     * Devolve true caso a colecção recebida tenha o mesmo título e a mesma
     * lista de editores. Para verificar verificar se os editores são os mesmos
     * devem utilizar o método mergeWithoutRepetitions
     */
    public boolean equals(Coleccao c) {
        return Arrays.equals(mergeWithoutRepetitions(this.editores, c.editores), c.editores) && this.titulo.equals(c.titulo);
    }

    /**
     * Mostra uma colecção segundo os outputs desejados
     */
    public void print(String prefix) {
        System.out.println(prefix+toString());
    }

    /**
     * main
     */
    public static void main(String[] args) {
        Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});

        Coleccao c1 = new Coleccao("Primavera",
                new String[]{"João Mendonça", "Manuel Alfazema"});

        boolean res;

        res = c1.addLivro(l1);
        res = c1.addLivro(l2);
        System.out.println("c1 -> " + c1);
        c1.print("");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println();

        // adicionar um livro com nome de outro já existente
        res = c1.addLivro(l2);
        System.out.println(
                "adição novamente de Viagem aos Pirinéus a c1 -> " + res);
        System.out.println("c1 -> " + c1);
        System.out.println();

        // get editores autores
        String[] ae = c1.getAutoresEditores();
        System.out.println("Autores editores of c1 -> " + Arrays.toString(ae));
        System.out.println();

        // getNumObrasFromPerson
        String nome = "João Mendonça";
        int n = c1.getNumObrasFromPerson(nome);
        System.out.println("Nº de obras de " + nome + " -> " + n);
        System.out.println();

        // getLivrosComoAutor
        nome = "João Mendonça";
        Livro[] obras = c1.getLivrosComoAutor(nome);
        System.out
                .println("Livros de " + nome + " -> " + Arrays.toString(obras));
        System.out.println();

        // rem livro
        String nomeLivro = "Viagem aos Himalaias";
        Livro l = c1.remLivro(nomeLivro);
        System.out.println("Remoção de " + nomeLivro + " -> " + l);
        c1.print("");
        System.out.println(l2);
        System.out.println();

        // equals
        Coleccao c2 = new Coleccao("Primavera",
                new String[]{"João Mendonça", "Manuel Alfazema"});
        boolean same = c1.equals(c2);
        System.out.println("c2:");
        c2.print("");
        System.out.println("c1.equals(c2) -> " + same);
        System.out.println();

        Coleccao c3 = new Coleccao("Primavera",
                new String[]{"João Mendonça"});
        same = c1.equals(c3);
        System.out.println("c3:");
        c3.print("");
        System.out.println("c1.equals(c3) -> " + same);
    }
}