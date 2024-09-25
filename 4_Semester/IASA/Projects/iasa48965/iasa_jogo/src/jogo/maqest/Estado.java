package jogo.maqest;

import java.util.HashMap;
import java.util.Map;

/**
 *A configuração das partes do sistema (os seus valores em memória) podem mudar e evoluir
 * ao longo do tempo, determinando o comportamento do sistema. As configurações de
 * valores que um sistema, ou parte de um sistema, pode assumir que determinam os
 * comportamentos possíveis, constituem o seu estado.
 * @param <EV> Tipo Evento
 * @param <AC> Tipo Accao
 */
public class Estado <EV,AC>{

    private String nome;

    private Map<EV,Transicao<EV,AC>> transicoes;

    /**
     * Construtor da classe estado
     * @param nome nome do estado
     */
    public Estado(String nome) {
        this.nome = nome;
        transicoes = new HashMap<EV,Transicao<EV,AC>>();
    }

    /**
     * Permite-nos ir buscar o nome do estado para apresentar na consola
     * @return devolve o nome do estado
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método que devolve a transição que vai permitir à maquina de estados saber qual o estado sucessor e a accao a realizar
     * @param evento Evento o qual queremos saber qual a transição
     * @return Devolvemos a transição
     */
    public Transicao<EV,AC> processar(EV evento)
    {
        return transicoes.get(evento);
    }

    /**
     * Vamos associar determinado evento a uma determinada transição de acordo com os modelos
     * @param evento Vai ser a chave onde associamos a transição
     * @param estadoSucessor O estado que reage ao evento
     * @param accao A accao que o personagem realiza
     * @return Devolvemos o proprio objeto, ou seja, o estado
     */
    public Estado<EV,AC> transicao(EV evento, Estado<EV,AC> estadoSucessor, AC accao){
        transicoes.put(evento,new Transicao<>(estadoSucessor,accao));
        return this;
    }

    /**
     * Este metodo tem o mesmo proposito que o anterior mas com uma assinatura diferente pois este serve para os casos onde a accao é nula
     * De forma a evitar redundancia damos return ao metodo anterior mas passamos no componente accao: null
     * @param evento Vai ser a chave onde associamos a transição
     * @param estadoSucessor O estado que reage ao evento
     * @return Devolvemos uma chamada ao metodo anterior com os parametros recebidos e com a accao null
     */
    public Estado<EV,AC> transicao(EV evento, Estado<EV,AC> estadoSucessor){
        return transicao(evento,estadoSucessor,null);
    }


}
