package jogo;

import jogo.ambiente.Ambiente;
import jogo.ambiente.Evento;
import jogo.personagem.Personagem;

/**
 * Este jogo baseia-se no paradigma comportamental, ou seja, a inteligência resulta do comportamento individual e
 * conjunto de múltiplos sistemas a diferentes escalas de organização tendo por base relações entre estímulos e respostas.
 * Sendo a sua arquitetura um modelo reactivo o comportamento do sistema é gerado de forma reactiva, com base em
 * associações entre estímulos (referentes às percepções) e respostas (referentes às acções)
 */
public class Jogo
{
    private Personagem personagem;
    private Ambiente ambiente;


    public static void main(String[] args)
    {
        new Jogo();
    }

    /**
     * Inicializa o ambiente e a personagem e depois começa o jogo
     */
    public Jogo()
    {
        this.ambiente = new Ambiente();
        this.personagem = new Personagem(ambiente);
        executar();
    }

    /**
     * Enquanto o evento não for terminar o jogo continua
     * O jogo começa por o jogador oferecer um estimulo e de seguida o ambiente reage
     */
    private void executar()
    {
        //Ambiente tem dependência de Evento (Ambiente depende de Evento)
        Evento evento;
        do{
            personagem.executar();
            ambiente.evoluir();
            evento = ambiente.getEvento();
        }while (evento != Evento.TERMINAR);
    }
}

