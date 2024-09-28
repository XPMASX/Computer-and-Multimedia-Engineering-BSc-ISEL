package tps.tp4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

//Classe filha da classe Competicao
public class Liga extends Competicao {

    //o número máximo de equipas na liga é 8
    private static final int MAX_EQUIPAS = 8;

    private int njogos = 0;

    private int numEquipas = 0;

    //lista de equipas na liga
    public Equipa[] equipas = new Equipa[MAX_EQUIPAS];

    //Construtor da Liga utiliza o construtor da classe pai
    public Liga(String nome) {super(nome);}

    //determina o resultado do jogo a partir do resultado que o utilizador fornecer
    public void det_jogo(Equipa x, Equipa y,int a, int b) {
        //adiciona o resultado para cada equipa da perspetiva que "a" e "b" são os golos marcados e sofridos pela equipa da casa, respetivamente
        //sendo "x" a equipa da casa e "y" a que joga fora, logo quando chamarmos o método "add_resultado" temos que inverter a ordem em que é recebido o resultado
        x.add_resultado(a, b);
        y.add_resultado(b, a);
        this.njogos++;

        System.out.println("A equipa " + x.nome + prefix(a,b) + y.nome + " por " + a + " a " + b);}

    //determina o resultado do jogo a partir de uma lista de números de 0 a 5 com pesos diferentes
    public void sim_jogo(Equipa x, Equipa y) {
        //recebe um número de 0 a 5 e adiciona o resultado como anteriormente
        int a = get_random_golo();
        int b = get_random_golo();

        x.add_resultado(a, b);
        y.add_resultado(b, a);
        this.njogos++;

        System.out.println("A equipa " + x.nome + prefix(a,b) + y.nome + " por " + a + " a " + b);
    }

    //simula os jogos restantes
    public void sim_todos(){

        //criamos uma variável auxiliar que vai ser igual ao número de jogos da liga + 1, pois queremos o próximo adversário da equipa
        int aux = this.njogos+1;

        //Cada equipa vai fazer 7 jogos seguidos contra as restantes equipas
        for (int i=0; i < MAX_EQUIPAS;i++) {

            for (int j = aux; j < MAX_EQUIPAS; j++) {

                //se i for igual a j quer dizer que duas equipas iguais iriam jogar logo utilizamos "continue" para sair do loop
                if(i==j) continue;

                //utilizamos o metodo "sim_jogo" para o número de jogos restantes
                sim_jogo(this.equipas[i], equipas[j]);
            }
            //colocamos aux a 0 pois só tem utilidade na primeira vez que entra no for
            aux=0;
        }
    }

    //chama o método da classe pai com o mesmo nome
    public String prefix(int a, int b) {return super.prefix(a,b);}

    //Devolve o número total de espetadores da liga
    public int get_espectadores(){
        int espectadores = 0;

        for(int i = 0; i<numEquipas;i++){
             espectadores += equipas[i].adeptos;
        }
        return espectadores;
    }

    //chama o método da classe pai com o mesmo nome
    public int get_random_golo() {return super.get_random_golo();}

    //adiciona ao array de equipas a equipa recebida
    public boolean addEquipa(Equipa equipa) {

        //se a equipa for null ou número de equipas estiver no maximo devolve false
        if(equipa == null || this.numEquipas >= MAX_EQUIPAS ) return false;

        //Percorre o numero de equipas da liga e se existir uma equipa com o mesmo nome ou estádio lança "IllegalArgumentException"
        for (int i=0; i<this.numEquipas;i++){

            if(Objects.equals(equipa.nome, this.equipas[i].nome) || Objects.equals(equipa.estadio, this.equipas[i].estadio)){

                throw new IllegalArgumentException("O nome e o estádio da equipa têm de ser diferentes das equipas já registadas");

            }
        }

        //adiciona a equipa e soma 1 ao numero de equipas
        this.equipas[this.numEquipas] = equipa;
        this.numEquipas++;
        return true;
    }

    //chama o método da classe pai com o mesmo nome
    public int get_jogo_bilhetes(Equipa e1, Equipa e2) {return super.get_jogo_bilhetes(e1,e2);}

    //Escreve a tabela da liga
    public void Tabela(){

        //criamos uma lista igual à lista de equipas da liga, mas utilizamos o clone para não interferir com a lista original
        Equipa[] tabela = equipas.clone();
        //Usamos o "Arrays.sort" para organizar a nova lista pelos pontos de cada equipa em ordem decrescente
        Arrays.sort(tabela, Comparator.comparingInt(Equipa::get_pontos).reversed());

        //percorremos as equipas e se duas equipas tiverem os mesmos pontos verificamos quem tem uma maior diferença de golos e organizamos
        for (int j=0; j<MAX_EQUIPAS-1;j++){
            if(tabela[j].get_pontos()==tabela[j+1].get_pontos()){

                if (tabela[j].get_dif()<tabela[j+1].get_dif()) {

                    //criamos uma equipa auxiliar para ajudar na troca equipas
                    Equipa aux = tabela[j];
                    tabela[j] = tabela[j + 1];
                    tabela[j + 1] = aux;
                }
            }
        }

        this.display_tabela(tabela);

    }

    //Este método é igual ao anterior, mas vai afetar a ordem das equipas da liga para que quando for preciso guardar no ficheiro .xml elas estão organizadas pela ordem final da classificação
    public void Tabela_final(){

        Arrays.sort(equipas, Comparator.comparingInt(Equipa::get_pontos).reversed());

        for (int j=0; j<MAX_EQUIPAS-1;j++){
            if(equipas[j].get_pontos()==equipas[j+1].get_pontos()){

                if (equipas[j].get_dif()<equipas[j+1].get_dif()) {

                    Equipa aux = equipas[j];
                    equipas[j] = equipas[j + 1];
                    equipas[j + 1] = aux;
                }
            }
        }
        System.out.println("\n"+this);
        System.out.println("\nEquipa  J   P  V E D GM GS DG");

        for (int i = 0;i<MAX_EQUIPAS;i++) System.out.println(equipas[i].nome + equipas[i].pontos);

    }

    //exibe a tabela numa janela
    public void display_tabela(Equipa[] tabela){

        JPanel panel = new JPanel();

        String[][] dados = new String[MAX_EQUIPAS][9];

        //para todas as equipas guardar as suas informações na String[][] dados
        for (int i = 0; i< MAX_EQUIPAS;i++){
            dados[i][0] = tabela[i].nome;
            dados[i][1] = String.valueOf(tabela[i].pontos.get_jogos());
            dados[i][2] = String.valueOf(tabela[i].pontos.get_pontos());
            dados[i][3] = String.valueOf(tabela[i].pontos.get_V());
            dados[i][4] = String.valueOf(tabela[i].pontos.get_E());
            dados[i][5] = String.valueOf(tabela[i].pontos.get_D());
            dados[i][6] = String.valueOf(tabela[i].pontos.get_GM());
            dados[i][7] = String.valueOf(tabela[i].pontos.get_GS());
            dados[i][8] = String.valueOf(tabela[i].pontos.get_dif());
        }

        String[] colunas = { "Equipa", "Jogos", "Pontos", "V", "E", "D", "GM", "GS", "DG" };

        JTable j = new JTable(dados, colunas);

        JScrollPane sp = new JScrollPane(j);

        panel.add(sp);

        //utilizamos um option pane para que seja necessario o input do utilizador para prosseguir
        JOptionPane.showMessageDialog(null,panel,this.toString(),JOptionPane.PLAIN_MESSAGE);
    }

    //exibe o podio no final da liga
    public void display_podio(){

        //usamos a imagem podio como uma label
        ImageIcon podio = new ImageIcon("imagens/podio.png");
        JLabel imagem = new JLabel(podio);
        imagem.setAlignmentY(0.5f);

        //criamos labels para cada uma das equipas no top 3 e damos uma cor de acordo com a sua posicao
        JLabel e1 = new JLabel(this.equipas[0].nome);
        e1.setForeground(Color.GREEN);
        e1.setFont(new Font("SansSerif", Font.BOLD, 40));
        e1.setSize( e1.getPreferredSize() );
        //escolhemos a localizacao de acordo com a imagem do podio
        e1.setLocation(420, 10);

        JLabel e2 = new JLabel(this.equipas[1].nome);
        e2.setForeground(Color.ORANGE);
        e2.setFont(new Font("SansSerif", Font.BOLD, 40));
        e2.setSize( e2.getPreferredSize() );
        e2.setLocation(200, 120);

        JLabel e3 = new JLabel(this.equipas[2].nome);
        e3.setForeground(Color.RED);
        e3.setFont(new Font("SansSerif", Font.BOLD, 40));
        e3.setSize( e3.getPreferredSize() );
        e3.setLocation(650, 150);

        JPanel panel = new JPanel();
        //adicionamos a imagem as labels das equipas
        imagem.add(e1);
        imagem.add(e2);
        imagem.add(e3);
        panel.add(imagem);

        String[][] dados = new String[MAX_EQUIPAS][9];

        //para todas as equipas guardar as suas informações na String[][] dados
        for (int i = 0; i< MAX_EQUIPAS;i++){
            dados[i][0] = equipas[i].nome;
            dados[i][1] = String.valueOf(equipas[i].pontos.get_jogos());
            dados[i][2] = String.valueOf(equipas[i].pontos.get_pontos());
            dados[i][3] = String.valueOf(equipas[i].pontos.get_V());
            dados[i][4] = String.valueOf(equipas[i].pontos.get_E());
            dados[i][5] = String.valueOf(equipas[i].pontos.get_D());
            dados[i][6] = String.valueOf(equipas[i].pontos.get_GM());
            dados[i][7] = String.valueOf(equipas[i].pontos.get_GS());
            dados[i][8] = String.valueOf(equipas[i].pontos.get_dif());
        }

        String[] colunas = { "Equipa", "Jogos", "Pontos", "V", "E", "D", "GM", "GS", "DG" };

        JTable j = new JTable(dados, colunas);

        JScrollPane sp = new JScrollPane(j);

        panel.add(sp);

        //utilizamos um option pane para que seja necessario o input do utilizador para prosseguir
        JOptionPane.showMessageDialog(null,panel,"Pódio",JOptionPane.PLAIN_MESSAGE);

    }

    //Pergunta ao utilizador se quer adicionar as equipas manualmente ou se quer que isso seja feito automaticamente
    public void Pergunta() {

        int resposta;
        Equipa new_equipa;

        //enquanto a resposta à pergunta for sim e o número de equipas for menor que o máx pergunta ao utilizador
        do {
            //cria uma caixa de confirmação que pergunta ao utilizador se quer adicionar mais equipas à liga ou se quer que isso seja feito manualmente
            resposta = JOptionPane.showConfirmDialog(null,"Existem " + this.numEquipas + " equipas na liga " + this.nome + " deseja adicionar a próxima equipa manualmente?");

            //se o número de equipas for maior que o max sai do loop
            if (this.numEquipas >= MAX_EQUIPAS){
                break;}

            //se a resposta for sim independentemente de ser maiúsculo ou minúsculo pede ao utilizador as informações da nova equipa
            if (resposta == 0) {

                new_equipa = this.criar_equipa();

                if (this.addEquipa(new_equipa))
                    System.out.println("Equipa adicionada com sucesso");
                else
                    System.out.println("Não foi possível adicionar a equipa");

            }

        }while (resposta == 0 && this.numEquipas<MAX_EQUIPAS);

        //se o utilizador não inserir o número max de equipas inserir automaticamente as equipas restantes a partir de equipas auxiliares criadas no main
        int length = MAX_EQUIPAS - this.numEquipas;

        //se build_equipa devolver false, quer dizer que o nome ou o estádio já foi utilizado logo temos que escolher outra equipa por isso aumenta o length
        for(int i=0;i <length;i++) {if (!build_equipa(this, i)) length++;}
    }

    //Constrói uma nova equipa a partir de um ficheiro ".xml" e adiciona á liga recebida. Utilizando o número "i" recebido é adicionado sempre equipas diferentes
    public boolean build_equipa(Liga liga, int i){
        try {

            File inputFile = new File("src/tps/tp4/Equipas.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            XPath xpath = XPathFactory.newInstance().newXPath();
            //Vai buscar o nome da equipa dentro de "Equipas" com o indice "i" e guarda na string "Nome"
            XPathExpression expression = xpath.compile("//Equipas/*['" + i + "']/Nome/text()");
            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nNome = (NodeList) result;
            String Nome = nNome.item(i).getNodeValue();

            //Vai buscar o estádio da equipa dentro de "Equipas" com o indice "i" e guarda na string "Estadio"
            expression = xpath.compile("//Equipas/*['" + i + "']/Estadio/text()");
            result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nEstadio = (NodeList) result;
            String Estadio = nEstadio.item(i).getNodeValue();

            //Vai buscar o número de adeptos da equipa dentro de "Equipas" com o indice "i" e guarda no int "Adeptos"
            expression = xpath.compile("//Equipas/*['" + i + "']/@Adeptos");
            result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nAdeptos = (NodeList) result;
            int Adeptos = Integer.parseInt(nAdeptos.item(i).getNodeValue());

            //percorre as equipas e devolve false se encontrar uma com o nome ou o estádio igual a que pretendemos construir
            for (int j=0; j<liga.numEquipas;j++) if(Objects.equals(Nome, liga.equipas[j].nome) || Objects.equals(Estadio, liga.equipas[j].estadio) ) return false;

            //Cria uma equipa com os dados recolhidos e adiciona à liga recebida
            Equipa new_equipa = new Equipa(Nome,Estadio,Adeptos,new Pontuacao(0,0,0,0,0,0,0,0));

            liga.addEquipa(new_equipa);
            System.out.println("Equipa-> " + Nome + " adicionada com sucesso");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    //Devolve uma equipa criada pelu utilizador
    public Equipa criar_equipa(){

        String nome;
        String estadio;
        int adeptos;

        //Pede ao utilizador o nome, estádio e número de adeptos
        nome = JOptionPane.showInputDialog("Introduza a sigla de 3 caracteres da nova equipa");
        estadio = JOptionPane.showInputDialog("Introduza o nome do estádio da nova equipa");
        adeptos = Integer.parseInt(JOptionPane.showInputDialog("Introduza o número de adeptos da nova equipa"));

       return new Equipa(nome,estadio,adeptos,new Pontuacao(0,0,0,0,0,0,0,0));

    }

    //Devolve um elemento "eLiga" que vai ser guardado posteriormente no ficheiro ".xml" dentro do elemento "Ligas"
    public Element createElement(Document doc) {

        Element eLiga = doc.createElement("Liga");
        eLiga.setAttribute("Comp","L");
        eLiga.setIdAttribute("Comp",true);
        Element eName = doc.createElement("Nome");
        eName.appendChild(doc.createTextNode(this.nome));
        eLiga.appendChild(eName);

        //Percorre as equipas cria o seu elemento e adiciona ao elemento eLiga
        for (int i=0;i< this.numEquipas;i++) {
            eLiga.appendChild(this.equipas[i].createElement(doc));
        }

        return  eLiga;
    }

    public String toString(){return super.toString();}

    public static void main(String[] args) {

        //cria, adiciona, simula e guarda no ficheiro ".xml" no elemento Equipas uma equipa auxiliar que vai ser utilizada no caso de o utilizador não criar todas as equipas necessárias para criar a liga
        Liga aux = new Liga("Portuguesa");
        Equipa e1 = new Equipa("SCP", "Alvalade", 37500, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e2 = new Equipa("SLB", "Luz", 35000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e3 = new Equipa("FCP", "Dragão", 29400, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e4 = new Equipa("BOA", "Bessa", 7000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e5 = new Equipa("SCB", "Pedreira", 100, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e6 = new Equipa("VSC", "D.Afonso Henriques", 15000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e7 = new Equipa("CFB", "Restelo", 10500, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e8 = new Equipa("VFC", "Bonfim", 3000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        aux.addEquipa(e1);
        aux.addEquipa(e2);
        aux.addEquipa(e3);
        aux.addEquipa(e4);
        aux.addEquipa(e5);
        aux.addEquipa(e6);
        aux.addEquipa(e7);
        aux.addEquipa(e8);

        aux.sim_todos();

        aux.Tabela_final();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/tps/tp4/Equipas.xml");
            //criamos um elemento "root" com a raiz do ficheiro
            Element root = doc.getDocumentElement();

            //adicionamos a raiz o elemento "Equipas" que contém as equipas auxiliares
            Element Equipas = doc.createElement("Equipas");

            for (int i = 0; i < aux.numEquipas; i++) {
                Equipas.appendChild(aux.equipas[i].createElement(doc));
            }

            root.appendChild(Equipas);

            FileOutputStream output = new FileOutputStream("src/tps/tp4/Equipas.xml");
            Main.writeXml(doc, output);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Liga liga = new Liga(JOptionPane.showInputDialog("Bem vindo ao simulador da Liga\nIntroduza o nome da liga que deseja criar"));

        liga.Pergunta();
        liga.Tabela();

        //Menu de seleção com o mesmo conceito do método "sim_todos" em que é criada uma variável auxiliar com o número de jogos + 1, pois queremos o próximo adversário
        int aux2 = liga.njogos + 1;
        first:
        for (int i = 0; i < MAX_EQUIPAS; i++) {
            for (int j = aux2; j < MAX_EQUIPAS; j++) {
                //se o jogo for entre a mesma equipa salta
                if (i == j) continue;

                int op ;
                String[] opcoes = {"Determinar", "Simular", "Simular todos", "Tabela", "Info", "Sair"};

                op = JOptionPane.showOptionDialog(
                        null
                        ,"Próximo jogo: " + liga.equipas[i].nome + " vs " + liga.equipas[j].nome + " no estádio " + liga.equipas[i].estadio + " com " + liga.get_jogo_bilhetes(liga.equipas[i], liga.equipas[j]) + " espectadores\n" +
                                "\nDeseja determinar, simular, simular jogos restantes, ver tabela, exibir info das equipas ou sair?"// Mensagem
                        , "Menu Liga"   // Titulo
                        , JOptionPane.YES_NO_OPTION
                        , JOptionPane.PLAIN_MESSAGE
                        , null // Icone.
                        , opcoes //
                        , "Sair"    // Label do botão Default
                );

                switch (op) {
                    //o utilizador determina o resultado do jogo
                    case 0:
                        int gm = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + liga.equipas[i].nome + " "));
                        int gs = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + liga.equipas[j].nome + " "));
                        liga.det_jogo(liga.equipas[i], liga.equipas[j], gm, gs);
                        break;

                    //o resultado do jogo é aleatorio
                    case 1:
                        liga.sim_jogo(liga.equipas[i], liga.equipas[j]);
                        break;

                    //simula os jogos restantes
                    case 2:
                        liga.sim_todos();
                        //break para a label "first" ou seja saí deste nested for
                        break first;

                    //exibe a tabela
                    case 3:
                        liga.Tabela();
                        //faz j-- pois o jogo não se realizou
                        j--;
                        break;

                    //exibe informacao das equipas que vão jogar
                    case 4:
                        JOptionPane.showMessageDialog(null,"\nEquipa -> " + liga.equipas[i].nome + "\n" + "Estádio  -> " + liga.equipas[i].estadio + "\n" + "Nº Adeptos -> " + liga.equipas[i].adeptos +
                                "\n\nEquipa -> " + liga.equipas[j].nome + "\n" + "Estádio  -> " + liga.equipas[j].estadio + "\n" + "Nº Adeptos -> " + liga.equipas[j].adeptos);
                        //faz j-- pois o jogo não se realizou
                        j--;
                        break;

                    //sai do programa
                    default: System.exit(1);
                            break;
                        }
                    }
                    aux2 = 0;
                }

                liga.Tabela_final();
                liga.display_podio();

                try {

                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse("src/tps/tp4/Equipas.xml");
                    Element root = doc.getDocumentElement();

                    //recebe o node "Ligas"
                    Node Ligas = doc.getElementsByTagName("Ligas").item(0);
                    //junta como filho a nova liga criada
                    Ligas.appendChild(liga.createElement(doc));

                    root.appendChild(Ligas);

                    //guarda no xml
                    FileOutputStream output = new FileOutputStream("src/tps/tp4/Equipas.xml");
                    Main.writeXml(doc, output);

                } catch (Exception e) {
                    e.printStackTrace();
                }

        new Main();
    }
}
