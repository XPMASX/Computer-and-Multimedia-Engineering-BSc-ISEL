package tp1.pack3Coleccoes;

import java.util.Arrays;

import tp1.pack2Livros.Livro;

import static tp1.pack2Livros.Livro.haRepeticoes;
import static tp1.pack2Livros.Livro.validarNomes;

/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores
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

    // array de colecções, estas devem ocupar sempre os menores índices
    private Coleccao[] coleccoes = new Coleccao[MAXOBRAS];

    // deverá conter sempre o número de colecções dentro da colecção
    private int numColeccoes = 0;

    // Editores, tem as mesmas condicionantes que array de autores na classe
    // livro
    private String[] editores;

    /**
     * Construtor; o título tem de ter pelo menos um caracter que não seja um
     * espaço (Character.isWhitespace); o array de editores devem ser pelo menos
     * um e têm as mesmas restrições que os autores dos livros;
     */
    public Coleccao(String titulo, String[] editores) {

        //titulo
        if (titulo == null || titulo.length() == 0)
            throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
        this.titulo = titulo;

        //array de editores
        if (editores.length < 1) throw new IllegalArgumentException("Tem que haver pelo menos 1 autor");
        if (validarNomes(editores)) throw new IllegalArgumentException("O array não pode conter elementos null");
        if (!haRepeticoes(editores)) throw new IllegalArgumentException("O array de autores contêm autores repetidos");
        this.editores = editores;
    }

    /**
     *
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Obtem o número total de páginas da colecção, páginas dos livros e das
     * colecções
     */
    public int getNumPaginas() {
        int count = 0;
        //percorremos as colecções na colecção
        for (int i = 0; i < numColeccoes; i++) {
            //fazemos o somatório do número de páginas total de cada coleção
                count += coleccoes[i].getNumPaginas();
        }
        //percorremos os livros na coleção
        for (int i = 0; i < numLivros; i++) {
            //fazemos o somatório do número de páginas de cada livro
            count += livros[i].getNumPaginas();
        }
        return count;
    }

    /**
     * As colecções com mais de 5000 páginas nos seus livros directos têm um
     * desconto de 20% nesses livros. As colecções em que o somatório de páginas
     * das suas subcolecções directas seja igual ou superior ao quádruplo do nº
     * de páginas da sua subcolecção directa com mais páginas deverão aplicar um
     * desconto de 10% sobre os preços das suas subcolecções
     */
    public float getPreco() {
        float count = 0;
        int pag, aux = 0, max = 0;
        //percorremos as colecções da colecção
        for (int i = 0; i < numColeccoes; i++) {
            //guardamos o número de páginas das colecções diretas da colecção
            pag = coleccoes[i].getNumPaginas();
            //somatório do número e páginas das colecções da colecção
            aux += coleccoes[i].getNumPaginas();
            //guardamos o número de páginas da colecção que tem mais paginas
            if (coleccoes[i].getNumPaginas() > max) {
                max = coleccoes[i].getNumPaginas();
            }
            //se o número de páginas das colecções diretas da colecção for maior que 5000, aplicamos o desconto de 20% e fazemos o somatório
            if (pag > 5000) count += (coleccoes[i].getPreco() * 0.8f);
            //caso contrário, somatório sem desconto
            else count += coleccoes[i].getPreco();
        }
        //se o somatório for maior ou igual ao quadruplo da coleção com o maior número de paginas, então fazemos o somatório com o desconto de 10%
        if(aux >= 4*max) count=count*0.9f;

        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //fazemos o somatório do preço de cada livro
            count+= livros[i].getPreco();
        }

        return count;
    }

    /**
     * Adiciona um livro à colecção se puder e este não seja null e a colecção
     * não ficar com livros iguais ao nível imediato da colecção. Deve utilzar o
     * método getIndexOfLivro e getIndexOfColeccao
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
     * Adiciona uma colecção à colecção se puder, esta não seja null e a
     * colecção não ficar com obras imediatas com títulos repetidos. Deve
     * utilizar o método getIndexOfLivro e getIndexOfColeccao
     */
    public boolean addColeccao(Coleccao col) {
        //caso não null ou não exceder o MAXOBRAS ou a colecção pretendido não existe retorna false
        if(col == null || numColeccoes >= MAXOBRAS || getIndexOfColeccao(col.getTitulo()) != -1) return false;
        // adicionamos a colecção pretendido no array de livros no índice numLivros
        coleccoes[numColeccoes] = col;
        //incrementamos o numColeccoes
        numColeccoes++;
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
     * Devolve o index no array de colecções onde estiver a colecção com o nome
     * pretendido. Devolve -1 caso não o encontre
     */
    private int getIndexOfColeccao(String titulo) {
        //percorremos o número de colecções
        for (int i = 0; i < numColeccoes; i++) {
            //caso o titulo for igual a um título da colecção, retornamos o índice
            if(titulo.equals(coleccoes[i].getTitulo())) return i;
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
     * Remove do array de colecções a colecção com o título igual ao título
     * recebido. Devolve a colecção removida ou null caso não tenha encontrado.
     * Deve-se utilizar o método getIndexOfColeccao. Recorda-se que as colecções
     * devem ocupar sempre os menores índices, ou seja, não pode haver nulls
     * entre elas
     */
    public Coleccao remColeccao(String titulo) {
        //encontramos o índice da colecção pretendido chamando o método getIndexOfColeccao
        int idx = getIndexOfColeccao(titulo);
        //criamos uma instância da colecção com parâmentros null
        Coleccao removeCol = null;

        // caso não tenha encontrado a colecção, retornamos null
        if (idx == -1) return null;
        //caso contrário
        else {
            //guardamos a colecção que é para remover no removeCol
            removeCol = coleccoes[idx];
            //passamos a colecção que é preteindida remover para null
            coleccoes[idx] = null;

            //percorremos o número de colecções apartir do índice da colecção removida
            for (int i = idx; i < numColeccoes; i++) {
                //caso é o ultimo, metemo-lo a null
                if(i==coleccoes.length-1)coleccoes[i]=null;
                // caso contrário empurramos tudo para a esquerda e  decrementamos
                else {
                    coleccoes[i] = coleccoes[i + 1];
                    numColeccoes--;
                }
            }
        }
        return removeCol;
    }
    /**
     * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se
     * como uma obra para os editores.
     */
    public int getNumObrasFromPerson(String autorEditor) {
        int count = 0;
        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //percorremos os autores dos livros
            for (int j = 0; j < livros[i].getAutores().length; j++) {
                //caso seja igual ao autor pretendido, incrementamos
                if(autorEditor.equals(livros[i].getAutores()[j])){
                    count++;
                }
            }
        }
        //percorremos as coleccoes
        for (int i = 0; i < numColeccoes; i++) {
            //percorremos os editores das coleções
            for (int j = 0; j < coleccoes[i].editores.length; j++) {
                //caso seja igual ao autor pretendido, incrementamos
                if(autorEditor.equals(this.editores[i])) count++;
                if(autorEditor.equals(coleccoes[i].editores[j])) count ++;
            }
        }
        // percorremos o array de editores
        for (int i = 0; i < editores.length; i++) {
            //caso seja igual ao autor pretendido, incrementamos
            if(autorEditor.equals(editores[i])) count++;
        }
        return count;
    }

    /**
     * Devolver um novo array (sem nulls) com os livros de que a pessoa recebida
     * é autor. Não deve conter repetições, para excluir as repetições devem
     * utilizar o método mergeWithoutRepetitions
     */
    public Livro[] getLivrosComoAutor(String autorNome) {
        int new_array_pos = 0;
        // criamos um array de livros com o tamanho das obras do autor pretendido
        Livro[] novo_livros = new Livro[getNumObrasFromPerson(autorNome)];

        //percorremos os livros
        for (int i = 0; i < numLivros; i++) {
            //percorremos os autores do livros
            for (int j = 0; j < livros[i].getAutores().length; j++) {
                //caso seja igual ao autor pretendido
                if(autorNome.equals(livros[i].getAutores()[j])){
                    //guardamos no array de livros o livro com o autor pretendido
                    novo_livros[new_array_pos]=livros[i];
                    new_array_pos++;
                    break;
                }
            }
        }
        //percorremos as coleções da coleção
        for (int z = 0; z < numColeccoes; z++) {
            int new_array_pos_2=0;
            //criamos um novo array de liros com o tamanho das coleções do autor pretendido
            Livro[] novo_livros_2 = new Livro[coleccoes[z].getNumObrasFromPerson(autorNome)];
            //percorremos o numero de livros
            for (int i = 0; i < numLivros; i++) {
                //percorremos os autores dos livros da coleção dentro da coleção
                for (int j = 0; j < coleccoes[z].livros[i].getAutores().length; j++) {
                    //caso seja igual ao autor prentendido
                    if(autorNome.equals(coleccoes[z].livros[i].getAutores()[j])){
                        //guardamos no array de livros os livros com o autor pretendido da coleção
                        novo_livros_2[new_array_pos_2]=coleccoes[z].livros[i];
                        new_array_pos_2++;
                        break;
                    }
                }
            }
            //juntamos os arrays de livros dos livros e o array de livros dentro das coleções
            novo_livros=mergeWithoutRepetitions(novo_livros_2,novo_livros);
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
        //percorremos as colecções da colecção
        for (int i = 0; i < numColeccoes; i++) {
            //guardamos no array de string criado a junção sem repetições (mergeWithoutRepetitions) do array de editores com os autores de cada colecção
            aux = mergeWithoutRepetitions(aux,coleccoes[i].editores);
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
     * Método idêntico ao método anterior mas agora com arrays de livros
     */
    private static Livro[] mergeWithoutRepetitions(Livro[] a1, Livro[] a2) {
        //igual ao mergeWithoutRepetitions mas com arrays do tipo Livro
        Livro[] c = new Livro[a1.length + a2.length];
        for (int i = 0; i < a1.length; i++) {
            c[i] = a1[i];
        }
        for (int i = 0; i < a2.length; i++) {
            c[a1.length + i] = a2[i];
        }
        int len = c.length;
        for (int i = 0; i < len - 1; i++)
            for (int j = i + 1; j < len; j++)
                if(c[i] != null && c[j] != null) {
                    if (c[i].equals(c[j])) {
                        for (int k = j; k < len - 1; k++)
                            c[k] = c[k + 1];
                        --len;
                    }
                }
        Livro[] r = new Livro[len];
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
        System.out.println(prefix + toString());
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

        // Outra colecção
        Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f,
                new String[]{"João Mendonça", "Mário Andrade"});
        Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f,
                new String[]{"João Mendonça", "Júlio Pomar"});


        Coleccao cx2 = new Coleccao("Outono",
                new String[]{"João Mendonça", "Manuel Antunes"});
        cx2.addLivro(l21);
        cx2.addLivro(l22);
        System.out.println("cx2 -> " + cx2);
        cx2.print("");
        System.out.println(l21);
        System.out.println(l22);
        System.out.println();

        // adicioná-la a c1
        c1.addColeccao(cx2);
        System.out.println("c1 após adição da colecção cx2 -> " + c1);
        c1.print("");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(cx2);
        System.out.println(l21);
        System.out.println(l22);
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
        System.out.println();
    }
}