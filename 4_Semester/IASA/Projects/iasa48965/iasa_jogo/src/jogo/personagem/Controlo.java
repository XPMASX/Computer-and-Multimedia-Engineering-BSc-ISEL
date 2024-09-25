package jogo.personagem;

import jogo.ambiente.Evento;
import jogo.maqest.Estado;
import jogo.maqest.MaquinaEstados;

/**
 * Controlo da personagem implementa
 * a dinâmica do comportamento da personagem
 * Vai inicializar e preparar todas as variáveis necessárias para o funcionamento do jogo
 *
 */
public class Controlo {

    private MaquinaEstados<Evento,Accao> maqest;

    /**
     * Construtor da classe Controlo
     * Vai definir todos os estados e transições
     * Inicializa a maquina de estados
     */
    public Controlo() {
        //Definir estados
        Estado<Evento,Accao> procura = new Estado<>("Procura");
        Estado<Evento,Accao> inspeccao = new Estado<>("Inspecção");
        Estado<Evento,Accao> observacao = new Estado<>("Observação");
        Estado<Evento,Accao> registo = new Estado<>("Registo");

        //Definir transições
        procura
                .transicao(Evento.ANIMAL, observacao, Accao.APROXIMAR)
                .transicao(Evento.RUIDO, inspeccao, Accao.APROXIMAR)
                .transicao(Evento.SILENCIO, procura, Accao.PROCURAR);

        inspeccao
                .transicao(Evento.ANIMAL, observacao, Accao.APROXIMAR)
                .transicao(Evento.RUIDO, inspeccao, Accao.PROCURAR)
                .transicao(Evento.SILENCIO, procura);

        observacao
                .transicao(Evento.FUGA, inspeccao)
                .transicao(Evento.ANIMAL, registo, Accao.OBSERVAR);
        registo
                .transicao(Evento.ANIMAL, registo, Accao.FOTOGRAFAR)
                .transicao(Evento.FUGA, procura)
                .transicao(Evento.FOTOGRAFIA, procura);

        //inicializar maquina de estados
        maqest = new MaquinaEstados<>(procura);
    }

    /**
     * Devolve o estado da maquina de estados para que possa ser exibida na consola
     * @return estado da maquina de estados
     */
    public Estado<Evento,Accao> getEstado()
    {
        return this.maqest.getEstado();
    }

    public Accao processar(Percepcao percepcao)
    {
        Evento evento = percepcao.getEvento();

        Accao estado = maqest.processar(evento);

        mostrar();

        return estado;
    }

    /**
     * Mostra o estado atual na consola
     */
    private void mostrar()
    {
        System.out.print("\nEstado: " + getEstado().getNome());
    }
}
