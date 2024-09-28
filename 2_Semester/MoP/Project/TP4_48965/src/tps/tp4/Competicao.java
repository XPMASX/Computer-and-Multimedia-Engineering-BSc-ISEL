package tps.tp4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.Random;

//Classe abstrata pai das classes Liga e Torneio
public abstract class Competicao {

    public String nome;

    public int njogos;

    //Construtor do objeto Competicao pai dos objetos Liga e Torneio
    public Competicao(String nome) {
        this.nome = nome;
    }

    //retorna a soma dos adeptos das duas equipas que vão jogar
    public int get_jogo_bilhetes(Equipa e1, Equipa e2){
        return e1.adeptos + e2.adeptos;
    }

    //determina o resultado do jogo a partir do resultado que o utilizador fornecer
    public void det_jogo(Equipa x, Equipa y,int a, int b){

        //adiciona o resultado para cada equipa da perspetiva que "a" e "b" são os golos marcados e sofridos pela equipa da casa, respetivamente
        //sendo "x" a equipa da casa e "y" a que joga fora, logo quando chamarmos o método "add_resultado" temos que inverter a ordem em que é recebido o resultado
        x.add_resultado(a, b);
        y.add_resultado(b, a);
        njogos++;

        System.out.println("A equipa " + x.nome + prefix(a,b) + y.nome + " por " + a + " a " + b);
    }

    //determina o resultado do jogo a partir de uma lista de números de 0 a 5 com pesos diferentes
    public  void sim_jogo(Equipa x, Equipa y){
        //recebe um número de 0 a 5 e adiciona o resultado como anteriormente
        int a = get_random_golo();
        int b = get_random_golo();

        x.add_resultado(a, b);
        y.add_resultado(b, a);
        this.njogos++;

        System.out.println("A equipa " + x.nome + prefix(a,b) + y.nome + " por " + a + " a " + b);
    }

    //simula os jogos restantes
    public abstract void sim_todos();

    //devolve o número de golos de 0 a 5
    public int get_random_golo(){
        Random rand = new Random();

        //array com números de 0 a 5 em que os números menores aparecem mais vezes que os outros, pois a sua probabilidade de acontecer num jogo real é maior que números maiores
        int[] resultados =  {0,0,0,0,0,1,1,1,1,1,2,2,2,3,3,4,4,5};

        //devolve um número da lista resultado escolhido pela função do java Random()
        return resultados[rand.nextInt(resultados.length)];
    }

    //Devolve em String o resultado por extenso da prespetiva da equipa da casa
    public String prefix(int a, int b){
        String s = null;

        if(a > b)
            s = " venceu a equipa ";

        if(a < b)
            s = " perdeu contra a equipa ";

        if(a==b)
            s = " empatou com a equipa ";

        return s;
    }

    //devolve o número de espetadores total da competição
    public abstract int get_espectadores();

    //adiciona a equipa à competição
    public abstract boolean addEquipa(Equipa equipa);

    //Devolve o nome da liga + o número total de espetadores
    public String toString() {

        String Nome;

        //se for uma liga escreve "Liga" se não "Torneio"
        if (this instanceof Liga)Nome = "Liga";
        else Nome = "Torneio";

        return "\n" + Nome + " " + this.nome + " com " + this.get_espectadores() + " espectadores" ;
    }

    public abstract Element createElement(Document doc);
}
