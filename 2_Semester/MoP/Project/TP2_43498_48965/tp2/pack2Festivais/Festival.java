package tp2.pack2Festivais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.List;

public class Festival extends Evento {

    //array de eventos com max 20 eventos
    private Evento[] eventos = new Evento[20];

    //numero de eventos
    private int numEventos = 0;

    //Construtor usa as condições do evento para verificar o nome
    public Festival(String nome) {

        super(nome);
    }

    //Devolve o numero de bilhetes
    public int getNumBilhetes() {
        int count = 0;

        //Percorre o número de eventos e adiciona o número de bilhetes à variavel count
        for (int i = 0; i < numEventos; i++) {

            count += eventos[i].getNumBilhetes();

        }

        return count;
    }

    //Devolve o número de actuações
    public int numActuacoes(String artista) {

        int count = 0;

        //Percorre o numero de eventos e se o numero de actuacoes do artista for 1 adiciona à variavel count
        for (int i = 0; i < numEventos; i++) {

            if (eventos[i].numActuacoes(artista) == 1) count++;

            else if (eventos[i] instanceof Festival) count += eventos[i].numActuacoes(artista);

        }
        return count;
    }

    //Retorna uma string identica ao evento mas adiciona o nome do festival no inicio
    public String toString(){

        return "Festival " + super.toString() ;
    }

    //Devolve uma string com os artistas do festival sem repetiçoes
    public String[] getArtistas(){

        //inicia um array aux onde vão ser guardados os nomes dos artistas
        String[] aux = new String[50];
        int count=0;

        //Percorre o numero de eventos
        for (int i=0; i < numEventos; i++){

            //Percorre o tamanho do get artistas de cada evento dentro do festival e adiciona ao array aux os artistas aumentando o count por 1 para que este acompanhe
            for (int j=0; j< eventos[i].getArtistas().length; j++){

                aux[count] = eventos[i].getArtistas()[j];

                count++;
            }

        }
        //Criamos uma lista auxiliar e enquanto existirem null no array a lista remove-os
        List<String> list_aux = new LinkedList<>(Arrays.asList(aux));
        while (list_aux.remove(null)) {
        }

        //Criamos o array artistas_final onde vão ser guardados os artistas sem repetições
        ArrayList<String> artistas_final = new ArrayList<>();

        //Percorre a lista auxiliar e se o array artistas_final não conter o artista da lista auxiliar adiciona-o
        for (String element : list_aux) {

            if (!artistas_final.contains(element)) {

                artistas_final.add(element);
            }
        }

        return artistas_final.toArray(new String[0]);

    }

    //Devolve o número de festivais dentro do festival
    public int getDeepFestival(){

        int count = 0;

        //Percorre o numero de eventos e caso o evento seja uma instância do Festival adicionar um ao count
        for (int i = 0; i < numEventos; i++) {

            if (eventos[i] instanceof  Festival ) {

                count++;
                //chamamos outra fez o getDeepFestival pois podem existir festivais dentro de festivais
                count+=((Festival) eventos[i]).getDeepFestival();

            }
        }
        return count;
    }

    //adiciona o evento, caso para nenhum
    //dos artistas do novo evento se verifique que o seu número de atuações no novo evento (a
    //adicionar) supere em mais de duas o número de atuações no festival corrente.
    public boolean addEvento(Evento evento){

        //se o evento recebido for null ou se o numero de eventos for igual ou superior a 20 retorna false
        if(evento == null || numEventos >= 20 ) return false;

        //Percorre o tamanho do array de artistas do evento
        for (int i=0; i<evento.getArtistas().length;i++){

            //caso o numero de um dos artistas do novo evento superar em mais de duas o número de atuações no festival corrente retorna false
            if (evento.numActuacoes(evento.getArtistas()[i]) > numActuacoes(evento.getArtistas()[i])+2 ) return false;

        }

        //adiciona o evento ao array eventos e incrementa o numero de eventos
        eventos[numEventos] = evento;

        numEventos++;

        return true;
    }

    // remove o evento, com o nome recebido, em qualquer profundidade do Festival corrente e
    //devolver true se removeu. O array de eventos deve ser gerido tal que nas remoções não se
    //realizem deslocamentos (shifts) dos eventos existentes, ou seja, o array poderá conter nulls entre
    //elementos
    public boolean delEvento(String nome) {

        //se o evento for encontrado retorna um valor idx diferente de -1 o que vai retornar true
        int idx = getIndexOfEvento(nome);

        return idx != -1;

    }

    private int getIndexOfEvento(String nome) {

        int j;

        //percorremos o número de eventos
        for (int i = 0; i < numEventos; i++) {
            //se o nome for igual a um nome de um evento
            if(Objects.equals(eventos[i].nome, nome)) {

                //passamos o evento que é preteindido remover para null
                eventos[i] = null;

                //Criamos uma lista auxiliar e enquanto existirem null no array a lista remove-os
                List<Evento> list = new LinkedList<>(Arrays.asList(eventos));
                while (list.remove(null)) {
                }
                eventos = list.toArray(new Evento[20]);

                //decrementamos 1 ao numero de eventos e retornamos o indice
                numEventos--;

                return i;
            }
            //se não for o caso procuramos nos festivais dentro do principal utilizando instanceof o que nos diz se o evento é uma instancia da classe festival
            if (eventos[i] instanceof  Festival ) {

                //vamos recursivamente procurando em todos os festivais até encontrar o evento pretendido
                j= ((Festival) eventos[i]).getIndexOfEvento(nome);

                //se o j for -1 é porque não existem mais eventos para ser eliminados então retorna 0 que significa que encontrou um evento e eliminiou-o
                if (j==-1) return 1;
            }
        }

        //caso contrário, se não existir esse evento, retornamos -1
        return -1;
    }

    //funçao adicional que ao acrescentar ao toString mostra todos os eventos dentro do festival, util para testes
    public Evento[] getEventos(){

        Evento[] eventos_final;

        List<Evento> list = new LinkedList<>(Arrays.asList(eventos));
        while (list.remove(null)) {
        }
        eventos_final = list.toArray(new Evento[list.size()]);

        return eventos_final;

    }

    public static void main(String[] args) {
        Espetaculo e1 = new Espetaculo("e1", "Loures", 100);
        Espetaculo e2 = new Espetaculo("e2", "Lisboa", 200);
        Festival f1 = new Festival("f1");

        //vamos testar o getArtistas ao adicionar a um festival dois espetaculos em que participa o mesmo artista
        e1.addArtista("a1");
        e2.addArtista("a1");
        f1.addEvento(e1);
        f1.addEvento(e2);
        //como podemos ver o numero de actuaçoes do artista "a1" é 2 e ele só aparece uma vez no array de artistas do festival
        System.out.println(f1);
        System.out.println("Número de actuações do artista a1 ->" + f1.numActuacoes("a1"));


        System.out.println();
        //para testarmos a condiçao do addEvento criamos um novo espetaculo onde adicionamos o mesmo artista dos espetaculos anteriores e adicionamos o espetaculo ao festival f1
        Espetaculo e3 = new Espetaculo("e3", "Porto", 300);
        //tambem é criado um novo festival em que o nosso objetivo é adicionar-lhe o primeiro festival
        Festival f2 = new Festival("f2");
        e3.addArtista("a1");
        f1.addEvento(e3);
        //como o numero de actuacoes de um dos artistas supera em 2 o numero de actuacoes do novo festival retorna false quando tentamos adicionar o f1
        System.out.println("Foi possivel adicionar o festival f1 ao festival f2 -> " + f2.addEvento(f1));
        //mas quando eliminamos do f1 o espetaculo e3 o numero de actuacoes do a1 volta para 2 logo não supera em mais de 2 o número de actuacoes do f2
        f1.delEvento("e3");
        //retorna true logo foi adicionado com sucesso o festival f1 ao festival f2
        System.out.println("Foi possivel adicionar o festival f1 ao festival f2 -> " + f2.addEvento(f1));
        System.out.println(f2);


        System.out.println();
        //para testar festivais dentro de festivais criamos o festival f3
        Festival f3 = new Festival("f3");
        //adicionamos ao festival f3 o espetaculo e3 que tem um artista a1
        f3.addEvento(e3);
        //adicionamos ao festival f3 o festival f2 que dentro dele tem o festival f1 que contem dois espetaculos cada um com o artista a1
        f3.addEvento(f2);
        System.out.println(f3);
        //O numero de actuacoes do a1 vai ser 3 pois no f3 tem uma e no f1 tem duas
        System.out.println("Número de actuações do artista a1 -> " + f3.numActuacoes("a1"));
        //Devolve dois pois dentro do f3 esta o f2 e dentro deste o f1
        System.out.println("Profundidade máxima de festivais dentro do festival f3 -> " + f3.getDeepFestival());
        //para testar o delEvento eliminamos o e1 que esta num festival de profundidade 2
        System.out.println("Foi possivel eliminar o festival e1 do festival f3 -> " + f3.delEvento("e1"));
        //o numero de actuacoes desce para 2 pois foi eliminado o e1
        System.out.println("Número de actuações do artista a1 -> " + f3.numActuacoes("a1"));
        System.out.println(f3);


        System.out.println();
        //Para testar o getArtistas() criamos um espetaculo e4 adicionamos dois artistas a2 e a3 e adicionamos o espetaculo ao f2
        Espetaculo e4 = new Espetaculo("e4", "Odivelas", 400);
        e4.addArtista("a2");
        e4.addArtista("a3");
        //Adicionamos ao espetaculo e3 um artista igual ao e4 para testar as repetições no array
        e3.addArtista("a2");
        f2.addEvento(e4);
        //Fazemos print e observamos que estão num array todos os artistas que atuam no festival f3 sem repetições
        System.out.println("Artistas que atuam no festival f3 -> " + Arrays.toString(f3.getArtistas()));



    }

}
