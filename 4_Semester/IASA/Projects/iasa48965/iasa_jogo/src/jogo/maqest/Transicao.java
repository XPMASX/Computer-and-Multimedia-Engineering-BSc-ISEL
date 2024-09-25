package jogo.maqest;

/**
 *A dinâmica pode ser expressa como uma função de transformação que,
 * perante o estado actual e as entradas actuais, produz o estado seguinte e as
 * saídas seguintes.
 * Esta classe é a função de transformação, ou seja, vai gerir as saídas e o próximo estado
 * em função das entradas e do estado actual do sistema
 * @param <EV> Tipo Evento
 * @param <AC> Tipo Accao
 */
public class Transicao <EV,AC> {

    private Estado<EV,AC> estadoSucessor;
    private AC accao;

    /**
     * Construtor da Transicao
     * @param estadoSucessor próximo estado
     * @param accao saída
     */
    public Transicao(Estado<EV, AC> estadoSucessor, AC accao) {
        this.estadoSucessor = estadoSucessor;
        this.accao = accao;
    }

    /**
     * Devolve o estado seguinte
     * @return estado seguinte
     */
    public Estado<EV,AC> getEstadoSucessor(){return estadoSucessor;}

    /**
     * Devolve a saída
     * @return saída
     */
    public AC getAccao(){return accao;}
}
