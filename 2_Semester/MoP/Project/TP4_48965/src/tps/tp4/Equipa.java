package tps.tp4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Equipa {

    public final String nome;

    public final String estadio;

    public final int adeptos;

    public Pontuacao pontos;

    //Construtor Equia
    public Equipa(String nome, String estadio, int adeptos, Pontuacao pontos) {

        //A sigla do clube só pode ter 3 caracteres
        if (nome == null || nome.length() != 3)
            throw new IllegalArgumentException("A sigla do clube tem de ter três caracteres");
        this.nome = nome;


        if (estadio == null || estadio.length() == 0)
            throw new IllegalArgumentException("O nome do estádio tem de ter pelo menos um caracter");
        this.estadio = estadio;

        if (adeptos < 0)
            throw new IllegalArgumentException("O número de adeptos não pode ser negativo");
        this.adeptos = adeptos;

        this.pontos = pontos;
    }

    //Devolve o numero de adeptos da equipa
     public int get_adeptos(){

        return this.adeptos;
     }

    //Devolve o numero de pontos da equipa
     public int get_pontos(){

        return this.pontos.get_pontos();
     }

    //Devolve o numero de jogos da equipa
     public int get_jogos(){

        return this.pontos.get_jogos();
     }

     //Dá reset aos golos da equipa
     public void make_zero(){

        this.pontos.make_zero();
     }

    //Devolve a difernça de golos da equipa
    public int get_dif(){

        return this.pontos.get_dif();
    }

    public String toString(){
        return "Equipa " + this.nome + " com o estádio " + this.estadio;
    }

    //adiciona o resultado recebido à puntuação da equipa
    public void add_resultado(int a, int b){

        this.pontos.add_resultado(a, b);

    }

    //cria o elemento Equipa e devolve
    public Element createElement(Document doc) {

        //cria um elemento "Equipa" e dá-lhe o atributo adeptos
        Element eEquipa = doc.createElement("Equipa");
        eEquipa.setAttribute("Adeptos", Integer.toString(get_adeptos()));

        //cria o elemento nome, filho de "Equipa
        Element eName = doc.createElement("Nome");
        eName.appendChild(doc.createTextNode(this.nome));
        eEquipa.appendChild(eName);

        //cria o elemento Estadio, filho de "Equipa
        Element eEstadio = doc.createElement("Estadio");
        eEstadio.appendChild(doc.createTextNode(this.estadio));
        eEquipa.appendChild(eEstadio);

        return  eEquipa;
    }

}
