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
import java.util.*;
import java.util.List;

//Classe filha da classe Competicao
public class Torneio extends Competicao{

    //o número máximo de equipas no torneio é 8
    private static final int MAX_EQUIPAS = 8;

    //o número máximo de competições no torneio é 8
    private static final int MAX_COMPS = 4;

    private int numEquipas = 0;

    private int numComps = 0;

    //lista de competições no torneio
    private final Competicao[] comps = new Competicao[MAX_EQUIPAS];

    //lista de equipas no torneio
    private Equipa[] equipas = new Equipa[MAX_EQUIPAS];

    //Construtor do Torneio utiliza o construtor da classe pai
    public Torneio(String nome) {super(nome);}

    //chama o método da classe pai com o mesmo nome
    public int get_jogo_bilhetes(Equipa e1, Equipa e2) {return super.get_jogo_bilhetes(e1,e2);}

    //chama o método da classe pai com o mesmo nome
    public void det_jogo(Equipa x, Equipa y, int a, int b) {super.det_jogo(x,y,a,b);}

    //chama o método da classe pai com o mesmo nome
    public void sim_jogo(Equipa x, Equipa y) {super.sim_jogo(x,y);}

    //simula os jogos restantes
    public void sim_todos() {

        //enquanto o número de equipas for maior que 2 simulamos os jogos do torneio e eliminamos as equipas com menor diferença de golos nos dois jogos
        do{

            //torna os golos das equipas em zero cada vez que entra no loop, ou seja, nos quartos de final e na semifinal
            for (int i = 0; i < equipas.length; i++){
                equipas[i].make_zero();
            }

            //loop para simular a primeira mão
            for (int i = 0; i < equipas.length; i += 2) {

                //se as equipas já tiverem 1 ou 3 jogos jogados quer dizer que este encontro já foi simulado logo continue
                if (equipas[i].get_jogos() == 1 || equipas[i].get_jogos() == 3) continue;

                sim_jogo(equipas[i], equipas[i + 1]);
            }

            //loop para simular a segunda mão
            for (int i = 0; i < equipas.length; i += 2) {

                //se as equipas já tiverem 2 ou 4 jogos jogados quer dizer que este encontro já foi simulado logo continue
                if (equipas[i].get_jogos() == 2 || equipas[i].get_jogos() == 4) continue;

                sim_jogo(equipas[i + 1], equipas[i]);
            }

            //loop para eliminar as equipas com menor diferença de golos nas duas mãos tornando as equipas eliminadas em nulls
            for (int i = 0; i < equipas.length; i += 2) {

                if (equipas[i].get_dif() > equipas[i+1].get_dif()) equipas[i+1]=null;
                else equipas[i] = null;
            }

            //passamos as equipas para lista, eliminamos os null do passo anterior e passamos de volta para array
            List<Equipa> list = new LinkedList<>(Arrays.asList(equipas));
            while (list.remove(null)) {
            }
            equipas = list.toArray(new Equipa[0]);

        }while(equipas.length > 2);

        //torna os golos das equipas em zero para a final
        for (int i = 0; i < equipas.length; i++){
            equipas[i].make_zero();
        }

        //Simulamos apenas uma vez, pois a final é só um jogo
        sim_jogo(equipas[0],equipas[1]);

        //apresentamos o vencedor e eliminamos a equipa derrotada
        if (equipas[0].get_dif() > equipas[1].get_dif()){
            equipas[1] = null;
            System.out.println("\nVencedor do Torneio " + this.nome + " é a equipa -> " + equipas[0].nome);
        }
        else {
            equipas[0] = null;
            System.out.println("\nVencedor do Torneio " + this.nome + " é a equipa -> " + equipas[1].nome);
        }

        //retiramos outra vez os null, desta vez a equipa que perdeu a final
        List<Equipa> list = new LinkedList<>(Arrays.asList(equipas));
        while (list.remove(null)) {
        }
        equipas = list.toArray(new Equipa[0]);
    }

    //chama o método da classe pai com o mesmo nome
    public int get_random_golo() {return super.get_random_golo();}

    //chama o método da classe pai com o mesmo nome
    public String prefix(int a, int b) {return super.prefix(a, b);}

    //Devolve o número total de espetadores do Torneio
    public int get_espectadores() {
        int espectadores = 0;

        for(int i = 0; i<numEquipas;i++){
            espectadores += equipas[i].adeptos;
        }
        return espectadores;
    }

    //Constrói uma nova competição a partir de um ficheiro ".xml" e adiciona ao torneio recebido. Recebemos uma string com o nome da competição que vai ser adicionada ao torneio
    public void build_comp(Torneio torneio, String nome) {

        Competicao new_comp;

        try {

            File inputFile = new File("src/tps/tp4/Equipas.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            XPath xpath = XPathFactory.newInstance().newXPath();
            //Vai buscar o tipo de competição utilizando o nome da competição recebido
            XPathExpression expression = xpath.compile("//*[Nome = '" + nome + "']/@Comp");
            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nNome = (NodeList) result;
            String comp = nNome.item(0).getNodeValue();

            //Se a competição tiver o atributo comp como "L" sabemos que a nova competição é do tipo Liga se não Torneio
            if (Objects.equals(comp, "L")) new_comp = new Liga(nome);

            else new_comp = new Torneio(nome);

            //Se a competição for adicionada com sucesso informa o utilizador
            if (torneio.addComp(new_comp))
                System.out.println("Competição-> " + nome + " adicionada com sucesso");
            //se não lança exceção
            else throw new IllegalArgumentException("Competição não existe");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  //adiciona a competição recebida ao torneio que chamar o metodo
  public boolean addComp(Competicao competicao) {

      //se a competição recebida for null ou não existir mais espaço retorna false
      if(competicao == null || this.numComps >= MAX_COMPS ) return false;

      //percorre as competições e se tiverem o mesmo nome que a que pretendemos criar lança exceção
      for (int i=0; i<this.numComps;i++){

          if(Objects.equals(competicao.nome, this.comps[i].nome) ){

              throw new IllegalArgumentException("Tem que escolher uma competição diferente");

          }
      }
      //Se a competição for uma instância de Liga construimos a liga com o "build_liga" e adicionamos à lista de competições
      if (competicao instanceof Liga liga){
          Liga new_liga = build_liga(liga);
          this.comps[this.numComps] = new_liga;}
      //Se não, é instância de Torneio logo construimos o torneio com o "build_torneio", fazendo primeiro um cast da competição para o tipo Torneio e adicionamos à lista de competições
      else{
          Torneio new_torneio = build_torneio((Torneio) competicao);
          this.comps[this.numComps] = new_torneio;
      }
      //somamos 1 ao número de competições e retornamos true
      this.numComps++;
      return true;
  }

  //Constrói a liga com os dados da liga com o mesmo nome no ficheiro ".xml" e devolve-a preenchida. Recebemos uma Liga com o nome de uma liga já existente e usamos esse nome para procurar no ficheiro a sua informação
  public Liga build_liga(Liga liga){
      try {

          File inputFile = new File("src/tps/tp4/Equipas.xml");

          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          dbFactory.setNamespaceAware(true);
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);

          XPath xpath = XPathFactory.newInstance().newXPath();
          XPathExpression expression;
          //contamos o numero de equipas dentro da liga recebida
          expression= xpath.compile("count(//Ligas/Liga[Nome='" + liga.nome + "']/Equipa)");

          Object result = expression.evaluate(doc, XPathConstants.NUMBER);
          double count = (Double) result;

          //percorre o numero de equipas da liga cria-as e adiciona a liga recebida
          for (int i=0;i<count;i++){
               expression = xpath.compile("//Ligas/Liga[Nome='" + liga.nome + "']/*['" + i + "']/Nome/text()");
               result = expression.evaluate(doc, XPathConstants.NODESET);
               NodeList nNome = (NodeList) result;
               String Nome = nNome.item(i).getNodeValue();

              expression = xpath.compile("//Ligas/Liga[Nome='" + liga.nome + "']/*['" + i + "']/Estadio/text()");
              result = expression.evaluate(doc, XPathConstants.NODESET);
              NodeList nEstadio = (NodeList) result;
              String Estadio = nEstadio.item(i).getNodeValue();

              expression = xpath.compile("//Ligas/Liga[Nome='" + liga.nome + "']/*['" + i + "']/@Adeptos");
              result = expression.evaluate(doc, XPathConstants.NODESET);
              NodeList nAdeptos = (NodeList) result;
              int Adeptos = Integer.parseInt(nAdeptos.item(i).getNodeValue());

              liga.addEquipa( new Equipa(Nome,Estadio,Adeptos,new Pontuacao(0,0,0,0,0,0,0,0)));
          }

          //descomentar se quisermos ver a ordem das equipas da liga
          //liga.Tabela();

      } catch (Exception e) {
          e.printStackTrace();
      }
      //devolve a liga preenchida com as equipas
      return liga;
  }

  //Constrói o torneio com os dados do torneio com o mesmo nome no ficheiro ".xml" e devolve-a preenchida. Recebemos um Torneio com o nome de um torneio já existente e usamos esse nome para procurar no ficheiro a sua informação
  public Torneio build_torneio(Torneio torneio){

      try {
          File inputFile = new File("src/tps/tp4/Equipas.xml");

          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
          dbFactory.setNamespaceAware(true);
          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
          Document doc = dBuilder.parse(inputFile);

          XPath xpath = XPathFactory.newInstance().newXPath();
          XPathExpression expression;
          //contamos o numero de ligas
          expression= xpath.compile("count(//Torneios/Torneio[Nome='" + torneio.nome + "']/Liga)");

          Object result = expression.evaluate(doc, XPathConstants.NUMBER);
          double nligas = (Double) result;

          //contamos o número de torneios
          expression= xpath.compile("count(//Torneios/Torneio[Nome='" + torneio.nome + "']/Torneio)");
          result = expression.evaluate(doc, XPathConstants.NUMBER);
          double ntorneios = (Double) result;

          //guardamos os nomes das ligas do torneio que pretendemos criar
          expression= xpath.compile("//Torneios/Torneio[Nome='" + torneio.nome + "']/Liga/Nome/text()");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          NodeList nNomes = (NodeList) result;

          //para o número de ligas criamos uma liga com o nome guardado anteriormente e adicionamos ao torneio que recebemos
          for (int i = 0; i< nligas;i++){
              Competicao new_comp = new Liga(nNomes.item(i).getNodeValue());
              torneio.addComp(new_comp);
          }

          //guardamos os nomes dos torneios do torneio que pretendemos criar
          expression= xpath.compile("//Torneios/Torneio[Nome='" + torneio.nome + "']/Torneio/Nome/text()");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          nNomes = (NodeList) result;

          //para o número de torneios criamos um torneio com o nome guardado anteriormente e adicionamos ao torneio que recebemos
          for (int i = 0; i< ntorneios;i++){
              Torneio new_comp = new Torneio(nNomes.item(i).getNodeValue());
              torneio.addComp(new_comp);
          }

          //criamos a única equipa no torneio, ou seja, o vencedor ao guardarmos numa variável o nome da equipa que está no atributo vencedor
          expression = xpath.compile("//*[Nome = '" + torneio.nome + "']/@Vencedor");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          NodeList nEquipa = (NodeList) result;
          String Nome = nEquipa.item(0).getNodeValue();

          //Procuramos em todas equipas com o nome guardado o estádio e o número de adeptos
          expression = xpath.compile("//Equipa[Nome='" + Nome + "']/Estadio/text()");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          NodeList nEstadio = (NodeList) result;
          String Estadio = nEstadio.item(0).getNodeValue();

          expression = xpath.compile("//Equipa[Nome='" + Nome + "']/@Adeptos");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          NodeList nAdeptos = (NodeList) result;
          int Adeptos = Integer.parseInt(nAdeptos.item(0).getNodeValue());

          //adicionamos a equipa ao torneio
          torneio.addEquipa(new Equipa(Nome,Estadio,Adeptos,new Pontuacao(0,0,0,0,0,0,0,0)));

      } catch (Exception e) {
          e.printStackTrace();
      }

      //devolvemos o torneio
        return torneio;
    }

    //Devolve um elemento "eTorneio" que vai ser guardado posteriormente no ficheiro ".xml" dentro do elemento "Torneios"
    public Element createElement(Document doc) {

        Element eTorneio = doc.createElement("Torneio");
        eTorneio.setAttribute("Comp","T");
        eTorneio.setIdAttribute("Comp",true);
        Element eName = doc.createElement("Nome");
        eName.appendChild(doc.createTextNode(this.nome));
        eTorneio.appendChild(eName);

        //Percorre as competições cria o seu elemento e adiciona ao elemento eTorneio
        for (int i=0;i< this.numComps;i++) {
             eTorneio.appendChild(this.comps[i].createElement(doc));
        }

        //adiciona o atributo vencedor com a unica equipa existente no torneio
        eTorneio.setAttribute("Vencedor",this.equipas[0].nome);

        return  eTorneio;
    }

    //adiciona a equipa recebida a lista de equipas participantes neste torneio
    public boolean addEquipa(Equipa equipa) {

            //se a equipa for null ou número de equipas estiver no maximo devolve false
            if(equipa == null || this.numEquipas >= MAX_EQUIPAS ) return false;

            //adiciona a equipa e soma 1 ao numero de equipas
            this.equipas[this.numEquipas] = equipa;
            this.numEquipas++;
            return true;
    }

    //adiciona a lista de equipas deste torneio as equipas a partir das competições presentes neste torneio
    public void get_equipas(){

        int nLigas = 0;

        //contamos o numero de ligas existentes neste torneio
        for (int i = 0; i<numComps;i++) if (comps[i] instanceof Liga) nLigas++;

        //dependendo do numero de ligas o número de equipas adicionadas por liga varia, mas o por torneio mantém-se sendo apenas o vencedor adicionado
        switch (nLigas){
            //Se existirem 2 ligas no torneio quer dizer que tem também dois torneios logo precisamos de 3 equipas de cada liga e do vencedor de cada torneio
            case 2:
                for (int i = 0; i<numComps;i++){
                    if (comps[i] instanceof Liga) {
                        this.addEquipa(((Liga)comps[i]).equipas[0]);
                        this.addEquipa(((Liga)comps[i]).equipas[1]);
                        this.addEquipa(((Liga)comps[i]).equipas[2]);
                    }else {
                        this.addEquipa(((Torneio)comps[i]).equipas[0]);
                    }
                }break;

            //Se existirem 3 ligas no torneio quer dizer que tem um torneio logo precisamos de 3 equipas de duas ligas, 1 de uma liga e do vencedor do torneio
            case 3:
                int aux = 0;
                for (int i = 0; i<numComps;i++){
                    if (comps[i] instanceof Liga && aux < 2) {
                        aux++;
                        this.addEquipa(((Liga)comps[i]).equipas[0]);
                        this.addEquipa(((Liga)comps[i]).equipas[1]);
                        this.addEquipa(((Liga)comps[i]).equipas[2]);
                    }else {
                        if (comps[i] instanceof Liga) this.addEquipa(((Liga)comps[i]).equipas[0]);
                            else {
                                this.addEquipa(((Torneio)comps[i]).equipas[0]);
                            }
                        }
                    }break;

            //Se só existirem ligas no torneio quer dizer precisamos de 2 equipas de cada liga
            case 4:

                for (int i = 0; i<numComps;i++){
                    if (comps[i] instanceof Liga ) {

                        this.addEquipa(((Liga) comps[i]).equipas[0]);
                        this.addEquipa(((Liga) comps[i]).equipas[1]);

                    }
                }break;

             //se não existir pelo menos 2 ligas lança exceção
            default: throw new IllegalArgumentException("Tem que existir pelo menos duas ligas no torneio");
        }

    }

    //Escreve os jogos em cada fase do torneio
    public void Tabela(){

        //print para os quartos de final
        if (this.numEquipas == 8){
            //usamos a imagem bracket como uma label
            ImageIcon bracket = new ImageIcon("imagens/bracket_8.png");
            JLabel imagem = new JLabel(bracket);
            imagem.setAlignmentY(0.5f);

            //criamos labels para todas as equipas do torneio e posicionamos segundo a imagem
            JLabel e1 = new JLabel(this.equipas[0].nome);
            e1.setFont(new Font("SansSerif", Font.BOLD, 18));
            e1.setSize( e1.getPreferredSize() );
            e1.setLocation(50, 45);
            JLabel e2 = new JLabel(this.equipas[1].nome);
            e2.setFont(new Font("SansSerif", Font.BOLD, 18));
            e2.setSize( e2.getPreferredSize() );
            e2.setLocation(50, 145);
            JLabel e3 = new JLabel(this.equipas[2].nome);
            e3.setFont(new Font("SansSerif", Font.BOLD, 18));
            e3.setSize( e3.getPreferredSize() );
            e3.setLocation(50, 260);
            JLabel e4 = new JLabel(this.equipas[3].nome);
            e4.setFont(new Font("SansSerif", Font.BOLD, 18));
            e4.setSize( e4.getPreferredSize() );
            e4.setLocation(50, 360);
            JLabel e5 = new JLabel(this.equipas[4].nome);
            e5.setFont(new Font("SansSerif", Font.BOLD, 18));
            e5.setSize( e5.getPreferredSize() );
            e5.setLocation(775, 45);
            JLabel e6 = new JLabel(this.equipas[5].nome);
            e6.setFont(new Font("SansSerif", Font.BOLD, 18));
            e6.setSize( e6.getPreferredSize() );
            e6.setLocation(775, 145);
            JLabel e7 = new JLabel(this.equipas[6].nome);
            e7.setFont(new Font("SansSerif", Font.BOLD, 18));
            e7.setSize( e7.getPreferredSize() );
            e7.setLocation(775, 260);
            JLabel e8 = new JLabel(this.equipas[7].nome);
            e8.setFont(new Font("SansSerif", Font.BOLD, 18));
            e8.setSize( e8.getPreferredSize() );
            e8.setLocation(775, 360);

            JPanel panel = new JPanel();
            //adicionamos a imagem as labels das equipas
            imagem.add(e1);
            imagem.add(e2);
            imagem.add(e3);
            imagem.add(e4);
            imagem.add(e5);
            imagem.add(e6);
            imagem.add(e7);
            imagem.add(e8);
            panel.add(imagem);

            //utilizamos um option pane para ser necessario o input do utilizador para prosseguir
            JOptionPane.showMessageDialog(null,panel,"Quartos de Final do " + this,JOptionPane.PLAIN_MESSAGE);
        }
        //print para a semi final
        else {
            //usamos a imagem bracket como uma label
            ImageIcon bracket = new ImageIcon("imagens/bracket_4.png");
            JLabel imagem = new JLabel(bracket);
            imagem.setAlignmentY(0.5f);

            JLabel e1 = new JLabel(this.equipas[0].nome);
            e1.setFont(new Font("SansSerif", Font.BOLD, 18));
            e1.setSize( e1.getPreferredSize() );
            e1.setLocation(20, 80);
            JLabel e2 = new JLabel(this.equipas[1].nome);
            e2.setFont(new Font("SansSerif", Font.BOLD, 18));
            e2.setSize( e2.getPreferredSize() );
            e2.setLocation(20, 300);
            JLabel e3 = new JLabel(this.equipas[2].nome);
            e3.setFont(new Font("SansSerif", Font.BOLD, 18));
            e3.setSize( e3.getPreferredSize() );
            e3.setLocation(570, 83);
            JLabel e4 = new JLabel(this.equipas[3].nome);
            e4.setFont(new Font("SansSerif", Font.BOLD, 18));
            e4.setSize( e4.getPreferredSize() );
            e4.setLocation(570, 295);

            JPanel panel = new JPanel();
            //adicionamos a imagem as labels das equipas
            imagem.add(e1);
            imagem.add(e2);
            imagem.add(e3);
            imagem.add(e4);
            panel.add(imagem);

            //utilizamos um option pane para que seja necessario o input do utilizador para prosseguir
            JOptionPane.showMessageDialog(null,panel,"SemiFinal do " + this,JOptionPane.PLAIN_MESSAGE);
        }
    }

    //Pergunta ao utilizador se quer adicionar as competições manualmente ou se quer que isso seja feito automaticamente
    public void Pergunta(Torneio torneio){

        int r1,r2;
        String min_ligas, r3;
        int count = 0;
        try {

            File inputFile = new File("src/tps/tp4/Equipas.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expression;
            Object result;
            NodeList nNome;
            String[] nome;

        do {
            //perguntamos ao utilizador se quer adicionar equipas manualmente ou não sendo o minimo de ligas 2
            r1 = JOptionPane.showConfirmDialog(null,"Existem " + this.numComps + " competições no Torneio " + this.nome + " deseja adicionar a próxima competição manualmente? Necessita de pelo menos duas ligas");

            //se sim
            if (r1 == 0) {

                //percorremos o número de competicoes do torneio e incrementamos a variavel auxiliar count se encontrarmos uma liga
                for (int i = 0; i<torneio.numComps; i++) if (torneio.comps[i] instanceof Liga) count++;

                //se count for maior ou igual a 2 informamos o utilizador que o número minimo de ligas foi adicionado
                if (count>=2) min_ligas = "Mínimo de ligas atingido";
                else min_ligas = "O mínimo de ligas ainda não foi atingido";

                String[] opcoes = new String[2];
                opcoes[0] = "Liga";
                opcoes[1] = "Torneio";
                r2 = JOptionPane.showOptionDialog(null, min_ligas + "\nDeseja adicionar uma Liga ou um Torneio?", "Liga ou Torneio", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, null);

                    //se quiser adicionar uma liga
                    if (r2 == 0) {

                        //guardamos num array os nomes das ligas disponiveis para o utilizador escolher
                        expression = xpath.compile("//Ligas/Liga/Nome/text()");
                        result = expression.evaluate(doc, XPathConstants.NODESET);
                        nNome =(NodeList) result;
                        nome = new String[nNome.getLength()];

                        //mostramos as ligas disponiveis
                        for (int i = 0; i < nNome.getLength(); i++){
                            nome[i] = nNome.item(i).getNodeValue();
                        }

                        r3 = (String) JOptionPane.showInputDialog(null, "Ligas disponiveis:",
                                "Escolha Liga", JOptionPane.QUESTION_MESSAGE, null,nome,null);

                        //a liga escolhida vai ser construida e adicionada ao torneio através do "build_comp"
                        build_comp(torneio,r3);}

                    //se quiser adicionar um torneio
                    else if (r2 == 1){

                        //guardamos num array os nomes dos torneios disponiveis para o utilizador escolher
                        expression = xpath.compile("//Torneios/Torneio/Nome/text()");
                        result = expression.evaluate(doc, XPathConstants.NODESET);
                        nNome =(NodeList) result;
                        nome = new String[nNome.getLength()];

                        //mostramos os torneios disponiveis
                        for (int i = 0; i < nNome.getLength(); i++){
                            nome[i] = nNome.item(i).getNodeValue();
                        }

                        r3 = (String) JOptionPane.showInputDialog(null, "Torneios disponiveis:",
                                "Escolha Torneio", JOptionPane.QUESTION_MESSAGE, null,nome,null);

                        //o torneio escolhida vai ser construida e adicionada ao torneio através do "build_comp"
                        build_comp(torneio,r3);}

                    //se nenhuma opção for escolhida é adicionado automaticamente o número de competições que falta
                    else break;

            }

        //se a resposta for diferenete de sim ou se tivermos atingido o número maximo de competições saimos do loop
        }while (r1 == 0 && torneio.numComps<MAX_COMPS);

        //se o utilizador não inserir o número max de competições inserir automaticamente as competições restantes a partir do ficheioro xml, mais especificamente as ligas
        int length = MAX_COMPS - torneio.numComps;

        //temos o mesmo método de procurar e guardar num array o nome das ligas disponiveis
        expression = xpath.compile("//Ligas/Liga/Nome/text()");
        result = expression.evaluate(doc, XPathConstants.NODESET);
        nNome =(NodeList) result;
        nome = new String[nNome.getLength()];

        for (int i = 0; i < nNome.getLength(); i++){
            nome[i] = nNome.item(i).getNodeValue();
        }

        //vamos adicionando competições até chegar a 4
        for(int i=0;i <length;i++){

            //se o nome da competição já existir aumentamos o length pois queremos que o programa continue a acrescentar
            for (int j=0; j<torneio.numComps;j++) if(Objects.equals(nome[i], torneio.comps[j].nome) ){
                length++;
                break;
            }

            //as ligas escolhidas vão ser construidas e adicionadas ao torneio através do "build_comp"
            build_comp(torneio, nome[i]);
        }

         } catch (Exception e) {
                e.printStackTrace();
        }
    }

    //Menu em que o user pode escolher determinar ou simular um jogo, simular os restantes, ver o estado da fase do torneio ou a informação das equipas que vão jogar.
    // Retorna um boolean true se for selecionada a opção de simular os restantes o que nos vai ajudar a saltar passos que seguem
    public boolean Menu(int i, int j){

        //criamos uma variavel "again" que vai nos ajudar a repetir o loop, pois quando as opções da tabela e da informação são selecionadas o jogo não é realizado logo queremos repetir o ciclo
        boolean again = true;
        while (again) {
            //colocamos "again" a false
            again = false;
            String[] opcoes = {"Determinar", "Simular", "Simular todos", "Tabela", "Info", "Sair"};

            int op = JOptionPane.showOptionDialog(
                    null
                    ,"Próximo jogo: " + this.equipas[i].nome + " vs " + this.equipas[j].nome + " no estádio " + this.equipas[i].estadio + " com " + this.get_jogo_bilhetes(this.equipas[i], this.equipas[j]) + " espectadores\n" +
                            "\nDeseja determinar, simular, simular jogos restantes, ver tabela, exibir info das equipas ou sair?"// Mensagem
                    , "Menu Torneio"   // Titulo
                    , JOptionPane.YES_NO_OPTION
                    , JOptionPane.PLAIN_MESSAGE
                    , null // Icone.
                    , opcoes //
                    , "Sair"    // Label do botão Default
            );

            switch (op) {

                //pede ao utilizador os golos marcados por cada equipa
                case 0:
                    int gm = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + this.equipas[i].nome + " "));
                    int gs = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + this.equipas[j].nome + " "));
                    this.det_jogo(this.equipas[i], this.equipas[j], gm, gs);
                    break;

                // simula o jogo
                case 1:
                    this.sim_jogo(this.equipas[i], this.equipas[j]);
                    break;

                //simula os jogos restantes
                case 2:
                    this.sim_todos();
                    return true;

                //exibe os jogos da fase do torneio
                case 3:
                    this.Tabela();
                    again = true;
                    break;

                //  mostra as informações de cada equipa
                case 4:
                    JOptionPane.showMessageDialog(null,"Equipa -> " + this.equipas[i].nome + "\n" + "Estádio  -> " + this.equipas[i].estadio + "\n" + "Nº Adeptos -> " + this.equipas[i].adeptos +
                            "\n\nEquipa -> " + this.equipas[j].nome + "\n" + "Estádio  -> " + this.equipas[j].estadio + "\n" + "Nº Adeptos -> " + this.equipas[j].adeptos);
                    again = true;
                    break;

                // acaba o programa
                default:
                    System.exit(1);
                    break;
            }
        }
        return false;
    }

    public void display_vencedor(){
        //usamos a imagem podio como uma label
        ImageIcon bracket = new ImageIcon(new ImageIcon("imagens/trofeu.png").getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));
        JLabel imagem = new JLabel(bracket);
        imagem.setAlignmentY(0.5f);

        JLabel e1 = new JLabel(this.equipas[0].nome);
        e1.setFont(new Font("SansSerif", Font.BOLD, 25));
        e1.setForeground(Color.BLACK);
        e1.setSize( e1.getPreferredSize() );
        e1.setLocation(123, 240);

        JPanel panel = new JPanel();
        //adicionamos a imagem as labels das equipas
        imagem.add(e1);
        panel.add(imagem);

        //utilizamos um option pane para que seja necessario o input do utilizador para prosseguir
        JOptionPane.showMessageDialog(null,panel,"Vencedor do Torneio " + this.nome,JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {

        //cria um novo torneio com o nome dado pelo utilizador
        Torneio torneio = new Torneio(JOptionPane.showInputDialog("Bem vindo ao simulador do Torneio\nIntroduza o nome do torneio que deseja criar"));

        //pergunta como quer adicionar as competições
        torneio.Pergunta(torneio);

        //adiciona as equipas ao torneio
        torneio.get_equipas();
        //Exibe as equipas participantes no torneio
        System.out.println("Torneio " + torneio.nome + " com os participantes:");
        for (int i = 0; i< torneio.numEquipas;i++) System.out.println(torneio.equipas[i].nome);
        torneio.Tabela();

        out:
        do{

            //torna os golos das equipas em zero cada vez que entra no loop, ou seja, nos quartos de final e na semifinal
            for (int i = 0; i < torneio.equipas.length; i++){
                torneio.equipas[i].make_zero();
            }

            //loop para simular a primeira mão
            for (int i = 0; i < torneio.equipas.length; i += 2) {

                //se as equipas já tiverem 1 ou 3 jogos jogados quer dizer que este encontro já foi simulado logo continue
                if (torneio.equipas[i].get_jogos() == 1 || torneio.equipas[i].get_jogos() == 3) continue;

                //se o menu devolver true saimos do loop, pois todos os jogos restantes foram simulados
                if(torneio.Menu(i, i + 1))break out;
            }

            //loop para simular a segunda mão
            for (int i = 0; i < torneio.equipas.length; i += 2) {

                //se as equipas já tiverem 2 ou 4 jogos jogados quer dizer que este encontro já foi simulado logo continue
                if (torneio.equipas[i].get_jogos() == 2 || torneio.equipas[i].get_jogos() == 4) continue;

                //se o menu devolver true saimos do loop, pois todos os jogos restantes foram simulados
                if(torneio.Menu(i + 1, i))break out;
            }

            for (int i = 0; i < torneio.equipas.length; i += 2) {

                //eliminamos as equipas no final de cada fase
                if (torneio.equipas[i].get_dif() > torneio.equipas[i+1].get_dif()) torneio.equipas[i+1]=null;
                else torneio.equipas[i] = null;
            }

            //retiramos os null
            List<Equipa> list = new LinkedList<>(Arrays.asList(torneio.equipas));
            while (list.remove(null)) {
            }
            torneio.equipas = list.toArray(new Equipa[0]);

            torneio.numEquipas = torneio.equipas.length;

        //fazer este loop enquanto houverem mais de duas equipas, ou seja, que a fase do torneio não seja a final
        }while(torneio.equipas.length > 2);

        for (int i = 0; i < torneio.equipas.length; i++){
            torneio.equipas[i].make_zero();
        }

        //Se estivermos na final entra no loop
        if (torneio.equipas.length == 2) {

            //Inicializamos um numero random
            Random rand = new Random();

            //array com capitais onde vai ser a final
            String[] capitais = {"Lisboa","Londres","Berlim","Madrid","Viena","Praga","Paris","Estocolmo"};

            //escolhe ao acaso uma das capitais da lista
            String capital = capitais[rand.nextInt(capitais.length)];

            //criamos uma variavel "again" que vai nos ajudar a repetir o loop, pois quando as opções da tabela e da informação são selecionadas o jogo não é realizado logo queremos repetir o ciclo
            boolean again = true;
            while (again) {
                again = false;
                //Apresentamos as opções ao user sendo estas determinar o resultado, simular ou ver a informação das equipas
                String[] opcoes = {"Determinar", "Simular", "Info", "Sair"};

                int op = JOptionPane.showOptionDialog(
                        null
                        ,"\nFINAL: " + torneio.equipas[0].nome + " vs " + torneio.equipas[1].nome + " em " + capital + " com "
                                + torneio.get_jogo_bilhetes(torneio.equipas[0], torneio.equipas[1]) + " espectadores"// Mensagem
                        , "Final Torneio " + torneio.nome   // Titulo
                        , JOptionPane.YES_NO_OPTION
                        , JOptionPane.PLAIN_MESSAGE
                        , null // Icone.
                        , opcoes //
                        , "Sair"    // Label do botão Default
                );

                switch (op) {

                    case 0:
                        int gm = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + torneio.equipas[0].nome + " "));
                        int gs = Integer.parseInt(JOptionPane.showInputDialog("Insira os golos marcados pela equipa " + torneio.equipas[1].nome + " "));
                        torneio.det_jogo(torneio.equipas[0], torneio.equipas[1], gm, gs);
                        break;

                    case 1:
                        torneio.sim_jogo(torneio.equipas[0], torneio.equipas[1]);
                        break;

                    case 2:

                        JOptionPane.showMessageDialog(null,"\nEquipa -> " + torneio.equipas[0].nome + "\n" + "Estádio  -> " + torneio.equipas[0].estadio + "\n" + "Nº Adeptos -> " + torneio.equipas[0].adeptos
                        + "\n\nEquipa -> " + torneio.equipas[1].nome + "\n" + "Estádio  -> " + torneio.equipas[1].estadio + "\n" + "Nº Adeptos -> " + torneio.equipas[1].adeptos);
                        again = true;
                        break;

                    default:
                        System.exit(1);
                        break;
                }
            }

            //o vencedor é aquele que tiver maior diferença de golos
            if (torneio.equipas[0].get_dif() > torneio.equipas[1].get_dif()){
                torneio.equipas[1] = null;
                System.out.println("\nVencedor do Torneio " + torneio.nome + " é a equipa -> " + torneio.equipas[0].nome);
            }
            else {
                torneio.equipas[0] = null;
                System.out.println("\nVencedor do Torneio " + torneio.nome + " é a equipa -> " + torneio.equipas[1].nome);
            }

            //retiramos outra vez os null, desta vez a equipa que perdeu a final
            List<Equipa> list = new LinkedList<>(Arrays.asList(torneio.equipas));
            while (list.remove(null)) {
            }
            torneio.equipas = list.toArray(new Equipa[0]);
        }

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse("src/tps/tp4/Equipas.xml");
            Element root = doc.getDocumentElement();

            //recebe o node "Torneios"
            Node Torneio = doc.getElementsByTagName("Torneios").item(0);

            //junta como filho o novo torneio criado
            Torneio.appendChild(torneio.createElement(doc));

            root.appendChild(Torneio);

            //guarda no xml
            FileOutputStream output = new FileOutputStream("src/tps/tp4/Equipas.xml");
            Main.writeXml(doc, output);

        } catch (Exception e){
            e.printStackTrace();
        }

        torneio.display_vencedor();
        new Main();
    }

}
