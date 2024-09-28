package tp3.XML;

import java.util.*;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Classe que representa um Evento do tipo Festival.
 *
 * @version 1.0
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Festival extends Evento {

	private static final int MAX_EVENTOS = 20;

	private Evento[] eventos = new Evento[MAX_EVENTOS];
	private int numEventos = 0;

	public Festival(String nome) {

		super(nome);
	}

	/**
	 * Devolve todos os bilhetes existentes no Festival (somando e devolvendo todos os bilhetes dos seus Eventos).
	 * @return o número de bilhetes existentes no Festival.
	 */
	@Override
	public int getNumBilhetes() {
		int count = 0;

		//Percorre o número de eventos e adiciona o número de bilhetes à variavel count
		for (int i = 0; i < numEventos; i++) {

			count += eventos[i].getNumBilhetes();

		}

		return count;
	}



	@Override
	public int numActuacoes(String artista) {
		int count = 0;

		//Percorre o numero de eventos e se o numero de actuacoes do artista for 1 adiciona à variavel count
		for (int i = 0; i < numEventos; i++) {

			if (eventos[i].numActuacoes(artista) == 1) count++;

			else if (eventos[i] instanceof Festival) count += eventos[i].numActuacoes(artista);

		}
		return count;
	}

	/**
	 *  Devolve uma string representativa do Festival.
	 *  Nota: Ver o ficheiro OutputPretendido/OutputPretendido.txt
	 */
	public String toString() {
		StringBuilder festival = new StringBuilder();
		festival.append("Festival ").append(super.toString());
		for (int i = 0; i<eventos.length;i++) {
			if (eventos[i]!=null)
				festival.append("\n ").append(eventos[i]);
		}
		return festival.toString();
	}

	/**
	 * Devolve um array contendo todos, de forma não repetida, os nomes de todos os artistas quer irão
	 * actuar no Festival.
	 * @return um array contendo os nomes dos artistas.
	 */
	@Override
	public String[] getArtistas() {
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

	/**
	 * Retorna a profundidade maxima da "árvore" que contém Festivais dentro de Festivais. O próprio Festival não conta.
	 * @return a profundidade máxima.
	 */
	public int getDeepFestival() {
		int count = 0,count_aux=0;

		//Percorre o numero de eventos e caso o evento seja uma instância do Festival adicionar um ao count
		for (int i = 0; i < numEventos; i++) {

			if (eventos[i] instanceof  Festival ) {

				count++;
				count_aux=count;
				//chamamos outra fez o getDeepFestival pois podem existir festivais dentro de festivais
				if(((Festival) eventos[i]).getDeepFestival()>count_aux) count++;

			}
		}
		return count;
	}

	/**
	 * Adiciona um novo Evento ao Festival, caso para nenhum dos artistas do novo evento se verifique que o seu número de atuações no
	 * novo evento (a adicionar) supere em mais de duas o número de atuações no festival corrente.
	 * @param evento
	 * @return verdadeiro, se o novo Evento foi adicionado.
	 */
	public boolean addEvento(Evento evento) {
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

	/**
	 * Remove um evento em qualquer profundidade do Festival corrente.
	 * @param nomeEvento nome do Evento a remover.
	 * @return verdadeiro, se o Evento foi removido.
	 */
	public boolean delEvento(String nomeEvento) {
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

	/**
	 * Imprime na consola informações sobre o Festival.
	 * Nota: Ver o output pretendido em OutputPretendido/OutputPretendido.txt.
	 *  prefixo para identar o Festival de acordo com a sua profundidade.
	 */
	public void print(String prefix) {
		System.out.println(" " + toString());
	}

	/**
	 * Constroi um novo Festival a partir de um nó contendo as infomações lidas do documento XML.
	 * @param nNode o nó associado ao Festival
	 * @return um novo Festival
	 */
	public static Festival build(Node nNode) {
		Element eNode = (Element) nNode;
		String nome = eNode.getElementsByTagName("Nome").item(0).getTextContent();
		Festival newFestival = new Festival(nome);

		NodeList eventos = eNode.getElementsByTagName("Eventos");

		for (int i=0;i< eventos.getLength();i++){
			if(eventos.item(i).getNodeName().equalsIgnoreCase("Eventos")){
				NodeList eventosChild = eventos.item(i).getChildNodes();

				for(int j=0;j<eventosChild.getLength();j++){

					if((eventosChild.item(j).getNodeType() == 1) && (newFestival.numEventos < 3)){
						Evento evento = Evento.build(eventosChild.item(j));
						if(evento!=null) newFestival.addEvento(evento);
					}
				}
			}
		}
		return newFestival;
	}

	/**
	 * Cria um novo Elemento quer irá representar, no documento XML, o Festival associado ao Festival corrente.
	 * @param doc o Documento que irá ser usado para gerar o novo Element.
	 */
	public Element createElement(Document doc) {

		Element eFestival = doc.createElement("Festival");

		Element eName = doc.createElement("Nome");
		eName.appendChild(doc.createTextNode(getNome()));
		eFestival.appendChild(eName);

		Element eEvents = doc.createElement("Eventos");
		eFestival.appendChild(eEvents);

		for (int i = 0; i < numEventos; i++){
			if(eventos[i] != null && eventos[i] instanceof Festival){
				Element festival = eventos[i].createElement(doc);
				eEvents.appendChild(festival);
			}
			else if(eventos[i] != null && eventos[i] instanceof Espetaculo){
				Element espetaculo = eventos[i].createElement(doc);
				eEvents.appendChild(espetaculo);
			}
		}

		return eFestival;
	}

	/**
	 * Método main que gera no output o que está no ficheiro OutputPretendido/OutputPretendido.txt e cria um novo
	 * documento XML/Eventos.xml, com a mesma estrutura que o documento OutputPretendido/Eventos.xml.
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			File inputFile = new File("XML/BaseDados.xml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			XPath xpath = XPathFactory.newInstance().newXPath();
			String expression = "/BaseDados/Eventos/*";
			NodeList nList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);

			Node nNode = nList.item(0);
			Evento evento = Evento.build(nNode);
			if(evento != null) evento.print("");

			Festival fNovo = new Festival("Bollywood Music Festival");

			Espetaculo e1_1 = new Espetaculo("Suna Hai", "Sines", 500);
			e1_1.addArtista("Suna Hai");
			fNovo.addEvento(e1_1);

			Espetaculo e1_2 = new Espetaculo("Rait Zara", "Sines", 400);
			e1_2.addArtista("Rait Zara");
			fNovo.addEvento(e1_2);



			if(evento instanceof Festival) {

				Festival festival = (Festival)evento;
				festival.addEvento(fNovo);


				// root elements
				Document newDoc = dBuilder.newDocument();
				Element rootElement = newDoc.createElement("Eventos");

				rootElement.appendChild(festival.createElement(newDoc));

				newDoc.appendChild(rootElement);

				FileOutputStream output = new FileOutputStream("XML/Eventos.xml");
				writeXml(newDoc, output);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Escreve, para o OutputStream, o documento doc.
	 * @param doc o documento contendo os Elementos a gravar on ficheiro output
	 * @param output o ficheiro de saída.
	 */
	private static void writeXml(Document doc, OutputStream output) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		// pretty print XML
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);
	}


}







