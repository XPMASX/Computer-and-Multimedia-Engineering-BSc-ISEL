package  tp2.pack2Festivais;

import java.util.Arrays;


public abstract class Evento {

    public String nome;

    /**
     * Construtor; O nome tem de ter pelo menos um caracter e ser diferente de null;
     */
    public Evento(String nome) {

        if (nome == null || nome.length() == 0)
            throw new IllegalArgumentException("O nome tem de ter pelo menos um caracter");
        this.nome = nome;

    }

    public abstract int getNumBilhetes();

    public abstract String[] getArtistas();

    public abstract int numActuacoes(String artista);

    public  String toString(){

        return this.nome + " com " + getNumBilhetes() + " bilhetes e com os artistas " + Arrays.toString(getArtistas());
    }



}