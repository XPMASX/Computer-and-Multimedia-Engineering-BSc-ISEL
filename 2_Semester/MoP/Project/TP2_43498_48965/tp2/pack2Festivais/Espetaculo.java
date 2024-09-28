package tp2.pack2Festivais;

import java.util.Objects;

public class Espetaculo extends Evento {

    //número de artistas
    private int nArtistas = 0;

    //número de bilhetes
    public int numBilhetes;

    //array de artistas com max 10 artistas
    private String[] artistas = new String[10];

    //nome da localidade
    public String localidade;

    /**
     * Construtor; a localidade não pode ser null e tem que existir; o número de bilhetes
     * não pode ser negativo;
     */
    public Espetaculo(String nome, String localidade, int numBilhetes) {

        super(nome);

        if (localidade == null || localidade.length() == 0)
            throw new IllegalArgumentException("A localidade tem de ter pelo menos um caracter");
        this.localidade = localidade;

        if (numBilhetes < 0)
            throw new IllegalArgumentException("O número de bilhetes não pode ser negativo");
        this.numBilhetes = numBilhetes;
    }

    //Devolve o número de bilhetes do espetáculo
    public int getNumBilhetes() {

        return this.numBilhetes;
    }

    //Obtém o numero de actuacoes do artista pretendido, 1 se ele sido adicionado ao array 0 se não
    public int numActuacoes(String artista){

        //percorre o array de artistas e devolve 1 se encontrar o artista pretendido
        for (int i=0; i < artistas.length; i++){

            if (Objects.equals(artistas[i], artista)) {
                return 1;
            }
        }

        return 0;
    }

    //Adiciona o artista ao array de artistas
    public boolean addArtista(String artista){

        //se o número de actuacoes do artista for 0 e se o artista na última posição disponivel for null adiciona o artista ao array se não retorna false
        if (numActuacoes(artista) == 0 && artistas[9]==null){

            artistas[nArtistas] = artista;

            nArtistas++;

            return true;
        }

        //se o artista na última posição do array artistas for diferente de null lança exeção, pois o array não pode ter mais artistas
        if (artistas[9]!=null)
            throw new IllegalArgumentException("O espetáculo não pode ter mais artistas");

        return false;
    }

    //Devolve os artistas do espetaculo
    public String[] getArtistas(){

        String[] artistas_final = new String[nArtistas];

        //Percorre o numero de artistas e adiciona a um novo array os artistas
        for (int i=0; i < nArtistas; i++) {

            artistas_final[i] = artistas[i];

        }

        return artistas_final;

    }

    //Retorna uma string identica ao evento mas adiciona a localidade no final
    public String toString(){

        return super.toString() + " em " + this.localidade;
    }

    public static void main(String[] args) {

        //Construtor
        Espetaculo l = new Espetaculo("Viagem aos Himalaias", "Loures", 100);

        //testar o numActuacoes quando não foi adicionado nenhum artista e quando são adicionados dois com o mesmo nome
        System.out.println(l.numActuacoes("0"));
        System.out.println(l.addArtista("0"));

        //retorna false pois já foi adicionado um artista com o mesmo nome
        System.out.println(l.addArtista("0"));
        System.out.println(l.numActuacoes("0"));
        System.out.println(l);

        //lança exceçao quando são adicionados mais de 10 artistas
        System.out.println(l.addArtista("1"));
        System.out.println(l.addArtista("2"));
        System.out.println(l.addArtista("3"));
        System.out.println(l.addArtista("4"));
        System.out.println(l.addArtista("5"));
        System.out.println(l.addArtista("6"));
        System.out.println(l.addArtista("7"));
        System.out.println(l.addArtista("8"));
        System.out.println(l.addArtista("9"));
        System.out.println(l.addArtista("10"));

    }
}

