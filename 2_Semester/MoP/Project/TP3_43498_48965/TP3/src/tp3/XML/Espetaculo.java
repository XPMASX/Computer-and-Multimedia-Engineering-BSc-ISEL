package tp3.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.w3c.dom.*;

/**
 * Classe que representa um Evento do tipo Espetaculo.
 *
 * @version 1.0
 * @author Docentes da Disciplina de Modelação e Programação, LEIM, Instituto Superior de Engenharia de Lisboa
 *
 */
public class Espetaculo extends Evento {

	private static final int MAX_ARTISTAS = 10;
	private String[] artistas = new String[MAX_ARTISTAS];
	private int nArtistas = 0;
	private int numBilhetes;
	private String localidade;

	/**
	 * Constroi um novo Espetaculo
	 *
	 * @param nome        nome do Espetaculo
	 * @param localidade  a localidade do Espetaculo
	 * @param numBilhetes o número de bilhetes disponíveis
	 */
	public Espetaculo(String nome, String localidade, int numBilhetes) {
		super(nome);

		if (localidade == null || localidade.length() == 0)
			throw new IllegalArgumentException("A localidade tem de ter pelo menos um caracter");
		this.localidade = localidade;

		if (numBilhetes < 0)
			throw new IllegalArgumentException("O número de bilhetes não pode ser negativo");
		this.numBilhetes = numBilhetes;
	}

	/**
	 * Informa se um determinado artista irá actuar no Espetaculo.
	 *
	 * @return 1, se actuar e 0 caso contrário.
	 * @Override
	 */
	@Override
	public int numActuacoes(String artista) {
		//percorre o array de artistas e devolve 1 se encontrar o artista pretendido
		for (int i = 0; i < artistas.length; i++) {

			if (Objects.equals(artistas[i], artista)) {
				return 1;
			}
		}

		return 0;
	}

	/**
	 * Permite adicionar un novo artista ao Espetaculo se o artista ainda
	 * não tem actuações e se o número máximo de artistas ainda não foi ultrapassado.
	 *
	 * @param artista representa o novo artista
	 * @return verdadeiro, caso o artista tenha sido adicionado e falso caso contrário.
	 */
	public boolean addArtista(String artista) {
		//se o número de actuacoes do artista for 0 e se o artista na última posição disponivel for null adiciona o artista ao array se não retorna false
		if (numActuacoes(artista) == 0 && artistas[9] == null) {

			artistas[nArtistas] = artista;

			nArtistas++;

			return true;
		}

		//se o artista na última posição do array artistas for diferente de null lança exeção, pois o array não pode ter mais artistas
		if (artistas[9] != null)
			throw new IllegalArgumentException("O espetáculo não pode ter mais artistas");

		return false;
	}

	/**
	 * Devolve o número de bilhetes.
	 *
	 * @Override
	 */
	public int getNumBilhetes() {
		return this.numBilhetes;
	}

	/**
	 * Devolve uma cópia dos artistas que actuam no Espetaculo.
	 *
	 * @Override
	 */
	@Override
	public String[] getArtistas() {
		String[] artistas_final = new String[nArtistas];

		//Percorre o numero de artistas e adiciona a um novo array os artistas
		for (int i = 0; i < nArtistas; i++) {

			artistas_final[i] = artistas[i];

		}

		return artistas_final;
	}

	/**
	 * Devolve a localidade do Espetaculo
	 *
	 * @return a localidade.
	 */
	public String getLocalidade() {
		return this.localidade;
	}

	/**
	 * Devolve uma String a representar o Espetaculo.
	 * Nota: Ver o ficheiro OutputPretendido.txt
	 *
	 * @Override
	 */


	public String toString() {
		return " " + super.toString() + " em " + this.localidade;
	}

	/**
	 * Constroi um novo Evento a partir do objecto Node passado como parâmetro.
	 *
	 * @param nNode
	 * @return um novo Evento
	 */
	public static Espetaculo build(Node nNode) {

		Element eNode = (Element) nNode;

		String nome = eNode.getElementsByTagName("Nome").item(0).getTextContent();
		String localidade = eNode.getElementsByTagName("Localidade").item(0).getTextContent();
		String bilhetes = eNode.getAttribute("numBilhetes");

		Espetaculo newEspetaculo = new Espetaculo(nome, localidade, Integer.parseInt(bilhetes));

		for (int i = 0; i < (eNode.getElementsByTagName("Artista").getLength()); i++)
			newEspetaculo.addArtista(eNode.getElementsByTagName("Artista").item(i).getTextContent());

		return newEspetaculo;
	}

	/**
	 * Constroi um novo Element a partir do Espectaculo actual.
	 *
	 * @param doc - o documento que irá gerar o novo Element
	 */
	public Element createElement(Document doc) {

		Element eEspetaculo = doc.createElement("Espetaculo");
		eEspetaculo.setAttribute("numBilhetes", Integer.toString(getNumBilhetes()));

		Element eName = doc.createElement("Nome");
		eName.appendChild(doc.createTextNode(getNome()));
		eEspetaculo.appendChild(eName);

		Element eArtists = doc.createElement("Artistas");
		eEspetaculo.appendChild(eArtists);

		for (int i = 0; i < nArtistas; i++) {
			if (getArtistas()[i] != null) {
				Element eArtist = doc.createElement("Artista");
				eArtist.appendChild(doc.createTextNode(getArtistas()[i]));
				eArtists.appendChild(eArtist);
			}
		}

		Element eLocalization = doc.createElement("Localidade");
		eLocalization.appendChild(doc.createTextNode(getLocalidade()));
		eEspetaculo.appendChild(eLocalization);

		return eEspetaculo;
	}
}
