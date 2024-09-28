package tps.tp4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Main {

    private  JPanel frame;
    private  JButton LigaButton;
    private  JButton TorneioButton;

    //janela de escolha entre liga ou torneio
    public Main() {

            String[] args = new String[0];
            //criar frame
            JFrame frame = new JFrame();

            frame.setContentPane(this.frame);
            //titulo
            frame.setTitle("Simulador Futebol");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 600);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            //se escolher liga corre main da liga e fecha esta janela
            LigaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();
                    Liga.main(args);
                }
            });

            //se escolher torneio corre main do torneio e fecha esta janela
            TorneioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();
                    Torneio.main(args);
                }
            });

    }


    public static void main(String[] args) {

        //criamos 4 ligas e 2 torneios simulamos de forma a dar resultados sempre diferentes cada vez que iniciamos o programa e guardamos a informação no ficheiro ".xml"

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

        Liga aux2 = new Liga("Inglesa");
        Equipa e11 = new Equipa("LEI", "King Power", 20060, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e22 = new Equipa("MNU", "Luz", 51144, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e33 = new Equipa("LIV", "Anfield", 50000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e44 = new Equipa("MNC", "Etihad", 10, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e55 = new Equipa("CHE", "Stamford Bridge", 100, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e66 = new Equipa("TOT", "White Hart Lane", 11000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e77 = new Equipa("ARS", "Emirates", 17004, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e88 = new Equipa("NEW", "St. James' Park", 23900, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        aux2.addEquipa(e11);
        aux2.addEquipa(e22);
        aux2.addEquipa(e33);
        aux2.addEquipa(e44);
        aux2.addEquipa(e55);
        aux2.addEquipa(e66);
        aux2.addEquipa(e77);
        aux2.addEquipa(e88);

        aux2.sim_todos();

        aux2.Tabela_final();

        Liga aux3 = new Liga("Espanhola");
        Equipa e111 = new Equipa("RMA", "Santiago Bernabéu", 75000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e222 = new Equipa("ATM", "Wanda Metropolitano", 33547, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e333 = new Equipa("FCB", "Camp Nou", 60000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e444 = new Equipa("SEV", "Ramón Sánchez Pizjuán", 7000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e555 = new Equipa("BET", "Benito Villamarín", 15555, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e666 = new Equipa("VIL", "El Madrigal", 7000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e777 = new Equipa("VAL", "Mestalla", 10500, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e888 = new Equipa("ATB", "San Mamés", 3000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        aux3.addEquipa(e111);
        aux3.addEquipa(e222);
        aux3.addEquipa(e333);
        aux3.addEquipa(e444);
        aux3.addEquipa(e555);
        aux3.addEquipa(e666);
        aux3.addEquipa(e777);
        aux3.addEquipa(e888);

        aux3.sim_todos();

        aux3.Tabela_final();

        Liga aux4 = new Liga("Italiana");
        Equipa e1111 = new Equipa("JUV", "Juventus", 25000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e2222 = new Equipa("MIL", "San Siro", 33000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e3333 = new Equipa("INT", "San Síro", 33000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e4444 = new Equipa("NAP", "Diego Armando Maradona", 15000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e5555 = new Equipa("ROM", "Olímpico de Roma", 19000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e6666 = new Equipa("LAZ", "Olimpico de Roma", 7500, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e7777 = new Equipa("FIO", "Artemio Franchi", 10500, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        Equipa e8888 = new Equipa("ATT", "Atleti Azzurri d'Italia", 5000, new Pontuacao(0, 0, 0, 0, 0, 0, 0, 0));
        aux4.addEquipa(e1111);
        aux4.addEquipa(e2222);
        aux4.addEquipa(e3333);
        aux4.addEquipa(e4444);
        aux4.addEquipa(e5555);
        aux4.addEquipa(e6666);
        aux4.addEquipa(e7777);
        aux4.addEquipa(e8888);

        aux4.sim_todos();

        aux4.Tabela_final();

        //criamos um torneio com as ligas criadas previamente
        Torneio europa = new Torneio("Europa");

        europa.addComp(aux);
        europa.addComp(aux2);
        europa.addComp(aux3);
        europa.addComp(aux4);

        europa.get_equipas();

        europa.sim_todos();

        //criamos um torneio com o torneio anterior e com mais 3 ligas
        Torneio champions_league = new Torneio("Champions League");

        champions_league.addComp(europa);
        champions_league.addComp(aux2);
        champions_league.addComp(aux3);
        champions_league.addComp(aux4);

        champions_league.get_equipas();

        champions_league.sim_todos();

        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document newDoc = dBuilder.newDocument();
            //criamos um elemento raiz "BaseDados"
            Element rootElement = newDoc.createElement("BaseDados");
            //adicionamos a raiz os elementos "Ligas" e "Torneios"
            Element Ligas = newDoc.createElement("Ligas");
            Element Torneios = newDoc.createElement("Torneios");

            rootElement.appendChild(Ligas);

            //adicionamos ao "Ligas" novos elementos das ligas criadas anteriormente
            Ligas.appendChild(aux.createElement(newDoc));
            Ligas.appendChild(aux2.createElement(newDoc));
            Ligas.appendChild(aux3.createElement(newDoc));
            Ligas.appendChild(aux4.createElement(newDoc));
            rootElement.appendChild(Torneios);

            //adicionamos ao "Torneios" novos elementos dos torneios criados anteriormente
            Torneios.appendChild(europa.createElement(newDoc));
            Torneios.appendChild(champions_league.createElement(newDoc));

            newDoc.appendChild(rootElement);

            FileOutputStream output;
            output = new FileOutputStream("src/tps/tp4/Equipas.xml");
            writeXml(newDoc, output);

        } catch (Exception e) {
            e.printStackTrace();
        }

            new Main();

    }
    public static void writeXml(Document doc, OutputStream output) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // pretty print XML
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }
}
