package jogo.ambiente;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Ambiente
{
    private Evento evento;

    private Map<String, Evento> eventos;

    private Scanner sc = new Scanner(System.in);

    /**
     * Construtor do Ambinete onde atribuimos uma letra a cada evento
     * que o utilizador possa escolher
     */
    public Ambiente()
    {
        eventos = new HashMap<String,Evento>();
        eventos.put("s",Evento.SILENCIO);
        eventos.put("r",Evento.RUIDO);
        eventos.put("a",Evento.ANIMAL);
        eventos.put("f",Evento.FUGA);
        eventos.put("o",Evento.FOTOGRAFIA);
        eventos.put("t",Evento.TERMINAR);

    }

    /**
     * Devolve o evento escolhido
     * @return evento escolhido pelo jogador
     */
    public Evento getEvento()
    {
        return evento;
    }

    /**
     * Evolução do ambiente a partir da escolha do utilizador no método gerar evento
     * Apresentamos o evento escolhido pelo utilizador
     */
    public void evoluir()
    {
        evento = gerarEvento();
        mostrar();
    }

    /**
     * O utilizador gera um evento
     * @return Devolvemos o evento de acordo com a letra escolhida pelo utilizador
     */
    private Evento gerarEvento()
    {
        System.out.print("\nEvento?");
        String comando = sc.next();
        return eventos.get(comando);
    }

    /**
     * Apresentamos a escolha do utilizador
     */
    private void mostrar()
    {
        System.out.print("Evento: " + evento);
    }
}