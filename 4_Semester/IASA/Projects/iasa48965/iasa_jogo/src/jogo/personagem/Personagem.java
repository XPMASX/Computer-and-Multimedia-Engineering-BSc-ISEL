package jogo.personagem;

import jogo.ambiente.Ambiente;
import jogo.ambiente.Evento;
import jogo.maqest.Estado;
import jogo.maqest.MaquinaEstados;

/**
 * Agente inteligente com um modelo reactivo, ou seja,
 * o comportamento do sistema é gerado de forma reactiva, com base em
 * associações entre estímulos (referentes às percepções) e respostas (referentes às acções)
 */
public class Personagem
{
    private Ambiente ambiente;
    private Controlo controlo;
    public Personagem(Ambiente ambiente)
    {
        this.ambiente = ambiente;
        this.controlo = new Controlo();
    }

    /**
     * A partir da sua percecao executamos descobrimos a transição seguinte
     * e atuamos de acordo com esta
     */
    public void executar()
    {
        actuar(controlo.processar(percepcionar()));
    }

    /**
     * O agente percepciona o ambiente a sua volta, ou seja,
     * recebe o evento atual do ambiente e devolve essa percepcao
     * @return Devolve a percepcao do ambiente com o evento atual
     */
    public Percepcao percepcionar()
    {
        Evento evento = ambiente.getEvento();
        return new Percepcao(evento);
    }

    /**
     * Se existir uma accao a realizar indicamos-a na consola
     * @param accao Accao realizada pelo personagem
     */
    public void actuar(Accao accao)
    {
        if (accao!=null)
            System.out.print("\nAccao: " + accao);
    }

}