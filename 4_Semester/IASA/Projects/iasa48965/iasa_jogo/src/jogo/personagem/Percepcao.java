package jogo.personagem;

import jogo.ambiente.Evento;
import jogo.maqest.Estado;

public class Percepcao
{
    private Evento evento;

    public Percepcao(Evento evento) {
        this.evento = evento;
    }

    public Evento getEvento()
    {
        return evento;
    }
}
