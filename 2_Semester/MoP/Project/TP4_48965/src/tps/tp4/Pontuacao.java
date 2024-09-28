package tps.tp4;

public class Pontuacao {

    private int njogos;

    private int P, V, E, D, GM, GS, DG;

    public Pontuacao(int njogos , int P , int V, int E, int D, int GM, int GS, int DG) {

        if (njogos < 0 || njogos > 14)
            throw new IllegalArgumentException("O número de jogos tem de estar entre 0 e 14");
        this.njogos = njogos;

        if (P < 0 || P > 3*njogos)
            throw new IllegalArgumentException("O número de pontos tem de estar entre 0 e 3 vezes o numero de jogos,ou seja, o número máximo de pontos possível");
        this.P = P;

        if (V < 0 || V > 14)
            throw new IllegalArgumentException("O número de vitórias tem de estar entre 0 e 14");
        this.V = V;

        if (E < 0 || E > 14)
            throw new IllegalArgumentException("O número de empates tem de estar entre 0 e 14");
        this.E = E;

        if (D < 0 || D > 14)
            throw new IllegalArgumentException("O número de derrotas tem de estar entre 0 e 14");
        this.D = D;

        if (V + E + D != njogos)
            throw new IllegalArgumentException("A soma de vitórias, empates derrotas é diferente do número de jogos jogados");

        if(GM < 0)
            throw new IllegalArgumentException("O número de golos marcados não pode ser negativo");
        this.GM = GM;

        if(GS < 0)
            throw new IllegalArgumentException("O número de golos sofridos não pode ser negativo");
        this.GS = GS;

        if(DG != GM-GS)
            throw new IllegalArgumentException("A diferença de golos tem que ser igual ao número de golos marcados menos os sofridos");
        this.DG = DG;
    }

    //devolve o número de jogos
    public int get_jogos(){

        return this.njogos;
    }

    //dá reset aos golos
    public void make_zero(){
        this.GM = 0;
        this.GS = 0;
        this.DG = 0;
    }

    //devolve o número de pontos
    public int get_pontos(){

        return this.P;
    }

    //devolve num vitorias
    public int get_V(){

        return  this.V;
    }

    //devolve num empates
    public int get_E(){

        return  this.E;
    }

    //devolve num derrotas
    public int get_D(){

        return  this.D;
    }

    //devolve golos marcados
    public int get_GM(){

        return  this.GM;
    }

    //devolve golos sofridos
    public int get_GS(){

        return  this.GS;
    }

    //devolve a diferença de golos
    public int get_dif(){

        return  this.DG;
    }

    //atualiza a pontuação a partir do resultado recebido
    public void add_resultado(int a, int b){

        //Se o numero de golos marcados pela equipa que chama o método (a) for superior ao de golos sofridos (b) soma uma vitória e 3 pontos
        if(a > b) {
            this.V++;
            this.P += 3;
        }

        //Se o numero de golos marcados pela equipa que chama o método (a) for igual ao de golos sofridos (b) soma um empate e 1 ponto
        if(a==b) {
            this.E++;
            this.P += 1;
        }

        //Se o numero de golos marcados pela equipa que chama o método (a) for inferior ao de golos sofridos (b) soma uma derrota
        if (a < b) this.D++;

        //adiciona a restante informação
        this.njogos++;

        this.GM += a;

        this.GS += b;

        this.DG += a-b;

    }

    //Devolve a pontuação identada de acordo com uma tabela de uma liga
    public String toString(){

        return String.format(" %5d %3d %2d %1d %1d %1d %2d %1d",this.njogos, this.P, this.V, this.E,  this.D,  this.GM , this.GS ,this.DG);
    }

}

