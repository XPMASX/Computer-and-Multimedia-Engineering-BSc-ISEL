package tp2.pack1ColeccoesComHeranca;

public interface IObra {
    String getTitulo();

    int getNumPaginas();

    float getPreco();

    String toString();

    void print(String prefix);

    boolean equals(Object l);
}
