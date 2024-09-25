package jogo.maqest;

import jogo.ambiente.Evento;
import jogo.personagem.Accao;

/**
 * Máquina de estados genérica que serve de biblioteca independente da personagem para utilização em contextos gerais
 * O comportamento do sistema corresponde à forma como o sistema age, ou seja, activa as
 * suas saídas (gera informação de saída), em função das suas entradas (informação de entrada,
 * proveniente do exterior) e do seu estado interno.
 * O comportamento do sistema corresponde à forma como o sistema age, ou seja, activa as
 * suas saídas (gera informação de saída), em função das suas entradas (informação de entrada,
 * proveniente do exterior) e do seu estado interno.
 * @param <EV> Tipo Evento
 * @param <AC> Tipo Accao
 */
public class MaquinaEstados <EV,AC>{

    private Estado<EV,AC> estado;

    /**
     * Construtor da maquina de estados
     * @param estado estado actual
     */
    public MaquinaEstados(Estado<EV,AC> estado) {
       this.estado = estado;
    }

    /**
     * Devolve o estado actual
     * @return estado actual
     */
    public Estado<EV,AC> getEstado()
    {
        return estado;
    }

    /**
     * Método que processa o evento recebido, ou seja, vai descobrir quais são os passos a seguir
     * Começa por ir buscar a transicao ao processar o estado atual de acordo com o evento recebido
     * De seguida, se existir uma transição guardamos o estado que sucede na variável estado e devolvemos a accao a ser realizada
     * Se não devolvemos null
     * @param evento Evento enviado pelo utilizador
     * @return Accao que decorre
     */
    public AC processar(EV evento)
    {
        Transicao<EV,AC> transicao = getEstado().processar(evento);

        if (transicao!=null)
        {
            estado = transicao.getEstadoSucessor();

            return transicao.getAccao();
        }
        else
            return null;
    }
}
