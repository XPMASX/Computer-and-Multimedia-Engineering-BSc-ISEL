package tp3.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class Testes_XPATH {

    public static void main(String[] args) {

        try {

            File inputFile = new File("XML/BaseDados.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expression = xpath.compile("//Espetaculo[@numBilhetes>=500]/Nome/text()");
            Object result = expression.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;

            System.out.println();
            System.out.println("a) A Lista de todos os Espetáculos com pelo menos 500 bilhetes disponíveis");

            for (int i = 0; i < nodes.getLength(); i++){
                System.out.println("Resposta-> " + nodes.item(i).getNodeValue());
            }

            System.out.println();
            System.out.println("b) O nome do primeiro Evento no Festival “Músicas do Mundo”");

           expression = xpath.compile("//Festival[Nome='Músicas do Mundo']/Eventos/*[1]/Nome/text()");
           result = expression.evaluate(doc, XPathConstants.NODESET);
           nodes = (NodeList) result;

          for (int i = 0; i < nodes.getLength(); i++){
              System.out.println("Resposta-> " + nodes.item(i).getNodeValue());
          }

          System.out.println();
          System.out.println("c) Os Espetáculos que têm como artista a atuar o “Pablo Milanés”");

          expression = xpath.compile("//Espetaculo[Artistas[Artista='Pablo Milanés']]/Nome/text()");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          nodes = (NodeList) result;

          for (int i = 0; i < nodes.getLength(); i++){
              System.out.println("Resposta-> " + nodes.item(i).getNodeValue());
          }

          System.out.println();
          System.out.println("d) O número de Eventos que existem no Festival “Música Cubana”");

          expression = xpath.compile("count(//Festival[Nome='Música Cubana']/Eventos/*)");
          result = expression.evaluate(doc, XPathConstants.NUMBER);
          double count = (Double) result;

          System.out.println("Resposta-> " + count);

          System.out.println();
          System.out.println("e) O nome do Evento patrocinado pela EDP");

          expression = xpath.compile("//Patrocinador[Nome='EDP']/@codEvento");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          nodes = (NodeList) result;
          String aux = null;

          for (int i = 0; i < nodes.getLength(); i++){
              aux = nodes.item(i).getNodeValue();
          }
          
          expression = xpath.compile("//Eventos//*[@*='"+ aux + "']/Nome/text()");
          result = expression.evaluate(doc, XPathConstants.NODESET);
          nodes = (NodeList) result;

          for (int i = 0; i < nodes.getLength(); i++){
              System.out.println("Resposta-> " + nodes.item(i).getNodeValue());
          }

          System.out.println();
          System.out.println("f) A lista de todos os Espetáculos que seguem ao Evento “Pablo Milanés” que irá ocorrer" +
                  "no Festival “Música Cubana” que faz parte do Festival “Músicas do Mundo”.");

            expression = xpath.compile("//Espetaculo[Nome='Pablo Milanés']/following::Espetaculo/Nome/text()");
            result = expression.evaluate(doc, XPathConstants.NODESET);
            nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++){
                System.out.println("Resposta-> " + nodes.item(i).getNodeValue());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
