package tp2.pack1ColeccoesComHeranca;

import tp2.pack1ColeccoesComHeranca.Livro;
import tp2.pack1ColeccoesComHeranca.Obra;
import tp2.pack2Festivais.Evento;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe Coleccao, deve conter a descrição de uma colecção, com título, os seus
 * livros, colecções e editores. Deve utilizar herança para guardar os livros e
 * as colecções num só array
 */
public class Coleccao extends Obra {
	// prefixo a colocar no início de cada print mais interno que o corrente
	public static final String GENERALPREFIX = "  ";

	// número máximo de obras de uma colecção
	private static int MAXOBRAS = 20;

	// Array de obras, de Livros ou Coleccçõe, em que estas devem encontrar-se
	// sempre nos menores índices e pela ordem de registo
	 private Obra[] obras = new Obra[MAXOBRAS];

	// deverá conter sempre o número de obras na colecção
	private int numObras = 0;

	// Editores, tem as mesmas condicionantes que array de autores na classe
	// livro
	private String[] editores;

	/**
	 * Construtor; o título deve ser guardado e validado na clase obra; o array de
	 * editores devem ser pelo menos um e tem as mesmas restrições que os autores
	 * dos livros;
	 */
	public Coleccao(String titulo, String[] editores) {

		//titulo
		super(titulo);

		//editores
		if(editores.length < 1) throw new IllegalArgumentException("Tem que haver pelo menos 1 autor");
		if(validarNomes(editores)) throw new IllegalArgumentException("O array não pode conter elementos null");
		if(!haRepeticoes(editores)) throw new IllegalArgumentException("O array de autores contêm autores repetidos");
		this.editores = editores;
	}

	/**
	 * Obtem o número total de páginas da colecção, páginas dos livros e das
	 * colecções
	 */
	public int getNumPaginas() {
		int count=0;
		// percorremos o número e obras e somamos o número de páginas de cada obra
		for (int i = 0; i < numObras; i++) {
			count+= obras[i].getNumPaginas();
		}
		return count;
	}

	/**
	 * As colecções com mais de 5000 páginas nos seus livros directos têm um
	 * desconto de 20% nesses livros. As colecções em que o somatório de páginas das
	 * suas subcolecções directas seja igual ou superior ao quádruplo do nº de
	 * páginas da sua subcolecção directa com mais páginas deverão aplicar um
	 * desconto de 10% sobre os preços das suas subcolecções
	 */

	public float getPreco() {
		int  pag, somatorio=0,max=0;
		float count=0;
		//percorremos as obras e fazemos o somatório
		for (int i = 0; i < numObras; i++) {
			pag = obras[i].getNumPaginas();
			somatorio+= obras[i].getNumPaginas();
			if (obras[i].getNumPaginas() > max) { // guardamos o nº de paginas da maior coleção
				max = obras[i].getNumPaginas();
			}
			if (pag > 5000) count += (obras[i].getPreco() * 0.8f); // aplicamos desconto caso haja uma coleção com mais de 5000 paginas
			else count += obras[i].getPreco();

		}
		if(somatorio >= 4*max) count+=count*0.9f; // aplicamos desconto caso seja igual ou superior ao quádruplo do nº de
		return count;							//páginas da sua subcolecção directa com mais páginas


	}

	/**
	 * Adiciona uma obra à colecção se puder, se esta não for null e a colecção não
	 * ficar com obras com iguais no seu nível imediato. Deve utilizar o método
	 * getIndexOfLivro e getIndexOfColeccao
	 */
	public boolean addObra(Obra obra) {
		//caso não null ou não exceder o MAXOBRAS ou a obra pretendida não existe retorna false
		if(obra == null || numObras >= MAXOBRAS || getIndexOfObra(obra.getTitulo()) != -1) return false;
		// adicionamos a obra pretendido no array de obras no índice numObras
		obras[numObras] = obra;
		//incrementamos o numObras
		numObras++;
		return true;
	}

	/**
	 * Devolve o index no array de obras onde estiver a obra com o nome pretendido.
	 * Devolve -1 caso não o encontre
	 */
	private int getIndexOfObra(String titulo) {
		// percorremos as obras
		for (int i = 0; i < numObras; i++) {
			//caso um titulo seja igual ao titulo pretendido, retornamos o seu index
			if(titulo.equals(obras[i].getTitulo())) return i;
		}
		//caso contrário, se não existir esse título, retornamos -1
		return -1;
	}


	/**
	 * Remove do array a obra com o título igual ao título recebido. Devolve a obra
	 * removida ou null caso não tenha encontrado a obra. Deve-se utilizar o método
	 * getIndexOfLivro. Recorda-se que as obras ocupam sempre os menores índices, ou
	 * seja, não pode haver nulls entre elas.
	 */
	public Obra remObra(String titulo) {
		//encontramos o índice da obra pretendido chamando o método getIndexOfObra
		int idx = getIndexOfObra(titulo);
		//criamos uma instância da obra com parâmentros null
		Obra removeObra = null;

		// caso não tenha encontrado a obra, retornamos null
		if (idx == -1) return null;
			//caso contrário
		else {
			//guardamos a obra que é para remover no removeBook
			removeObra = obras[idx];
			//passamos a obra que é preteindido remover para null
			obras[idx] = null;

			//percorremos o número de obras apartir do índice da obra removida
			List<Obra> list = new LinkedList<>(Arrays.asList(obras));
			while (list.remove(null)) {
			}
			obras = list.toArray(new Obra[20]);

					numObras--;
				}


		return removeObra;
	}

	/**
	 * Remove todas as obras (livros ou colecções) dentro da obra corrente, que
	 * tenham um título igual ou título recebido. Devolve true se removeu pelo menos
	 * uma obra, ou false caso não tenha trealizado qualquer remoção. Deve utilizar
	 * os métodos remObra e remAllObra.
	 */
	public boolean remAllObra(String titulo) {
		boolean removed = false;
		int idx = getIndexOfObra(titulo); // descobrimos o idx do titulo pretendido
		if(idx == -1) return false; // caso não encontre, return false
		for (int i = 0; i < numObras; i++) { // percorremos as obras
			idx = getIndexOfObra(titulo); // descobrimos o idx do titulo outra vez
			if(idx == -1 && !removed) return false; // caso não encontre e não foi nada removido, então return false
			if(i == idx){ 							//caso encontre o livro, no indice idx
				remObra(titulo);					// chamamos o remObra
				removed = true;
			}
		}
		return true;
	}

	/**
	 * Devolve o nº de obras de uma pessoa. Cada colecção deve contabilizar-se como
	 * uma obra para os editores.
	 */
	public int getNumObrasFromPerson(String autorEditor) {
		int count = 0;

		for (int i = 0; i < numObras; i++) {											// percorremos as obras
			if(obras[i] instanceof Livro){												//Caso a obra seja um livro
				count+=(((Livro) obras[i]).contemAutor(autorEditor))?1:0;				//Incrementamos se for do autorEditor
			}
			else if(obras[i] instanceof Coleccao){										// caso a obra seja uma Coleção
				count+=((Coleccao) obras[i]).getNumObrasFromPerson(autorEditor);		// chamamos outra vez o método getNumObrasFromPerson, e somamos
			}
		}

																						// percorremos o array de editores
		for (int i = 0; i < editores.length; i++) {
			if(autorEditor.equals(editores[i])) count++;								//caso seja igual ao autor pretendido, incrementamos
		}
		return count;
	}


	/**
	 * Deve devolver um novo array, sem repetições, com os livros de que o autor
	 * recebido é autor. O array devolvido não deve conter repetições, para excluir
	 * as repetições devem utilizar o método mergeWithoutRepetitions
	 */
	public Livro[] getLivrosComoAutor(String autorNome) {
		int new_array_pos = 0;
		//instanciamos a classe Livro
		tp2.pack1ColeccoesComHeranca.Livro[] novo_livros = new tp2.pack1ColeccoesComHeranca.Livro[getNumObrasFromPerson(autorNome)];

		for (int i = 0; i < numObras; i++) {								// percorremos as obras
				if (obras[i] instanceof Livro) {							//caso a obra seja um livro
				if (((Livro) obras[i]).contemAutor(autorNome)) {			//e o livro tem como autor o autorNome
					novo_livros[new_array_pos] = (Livro) obras[i];			// guardamos na o livro no nosso array de livros
					new_array_pos++;										//incrementamos
				}
			}
			else if(obras[i] instanceof Coleccao){							// caso seja uma coleção
																			// chamamos outra vez o getLivrosComoAutor e o mergeWithoutRepetitions
																			// e guardamos no array de livros
				novo_livros=mergeWithoutRepetitions(novo_livros,((Coleccao) obras[i]).getLivrosComoAutor(autorNome));
			}
		}

		List<Livro> list = new LinkedList<>(Arrays.asList(novo_livros));
		while (list.remove(null)) {
		}
		novo_livros = list.toArray(new Livro[0]);

		return novo_livros;
	}

	/**
	 * Deve devolver um array, sem nulls, com todos os autores e editores existentes
	 * na colecção. O resultado não deve conter repetições. Deve utilizar o método
	 * mergeWithoutRepetitions
	 */
	public String[] getAutoresEditores() {

		// criamos um array de strings vazio
		String[] aut = {};

		for (int i = 0; i < numObras; i++) {															//percorremos as obras
			if (obras[i] instanceof Livro) {															//caso a obra seja um livro
				aut = mergeWithoutRepetitions(aut,(((Livro) obras[i]).getAutores()));					// chamamos o mergeWithoutRepetitions
																										//com o aut vazio e o array de autores do livro
			}
			else if(obras[i] instanceof Coleccao){														//caso seja uma coleção
				aut = mergeWithoutRepetitions(aut,((Coleccao) obras[i]).getAutoresEditores());			//chamamos o merge com o aut e chamamos o getAutoresEditores
			}
		}
		for (int i = 0; i < editores.length; i++) {														// percorremos o array de editores
			aut = mergeWithoutRepetitions(aut,editores);												// usamos o merge
		}
		return aut;
	}

	/**
	 * Método que recebendo dois arrays sem repetições devolve um novo array com
	 * todos os elementos dos arrays recebidos mas sem repetições
	 */
	private static String[] mergeWithoutRepetitions(String[] a1, String[] a2) {
		//criamos um array de strings com o tamanho dos arrays que é para juntar
		String[] c = new String[a1.length + a2.length];
		//adicionamos a c os conteudos do primeiro array
		for (int i = 0; i < a1.length; i++) {
			c[i] = a1[i];
		}
		//adicionamos a c os conteudos do segundo array
		for (int i = 0; i < a2.length; i++) {
			c[a1.length + i] = a2[i];
		}
		int len = c.length;
		//percorremos o array c e retiramos os repetidos
		for (int i = 0; i < len - 1; i++)
			for (int j = i + 1; j < len; j++)
				if (c[i].equals(c[j])) {
					for (int k = j; k < len - 1; k++)
						c[k] = c[k + 1];
					--len;
				}
		//guardamos os elementos não repetidos num novo array de strings
		String[] r = new String[len];
		for (int i = 0; i < r.length; i++)
			r[i] = c[i];


		return r;
	}

	/**
	 * Método idêntico ao método anterior mas agora com arrays de livros
	 */
	private static Livro[] mergeWithoutRepetitions(Livro[] a1, Livro[] a2) {
		//igual ao mergeWithoutRepetitions mas com arrays do tipo Livro
		tp2.pack1ColeccoesComHeranca.Livro[] c = new tp2.pack1ColeccoesComHeranca.Livro[a1.length + a2.length];
		for (int i = 0; i < a1.length; i++) {
			c[i] = a1[i];
		}
		for (int i = 0; i < a2.length; i++) {
			c[a1.length + i] = a2[i];
		}
		int len = c.length;
		for (int i = 0; i < len - 1; i++)
			for (int j = i + 1; j < len; j++)
				if(c[i] != null && c[j] != null) {
					if (c[i].equals(c[j])) {
						for (int k = j; k < len - 1; k++)
							c[k] = c[k + 1];
						--len;
					}
				}
		tp2.pack1ColeccoesComHeranca.Livro[] r = new tp2.pack1ColeccoesComHeranca.Livro[len];
		for (int i = 0; i < r.length; i++)
			r[i] = c[i];
		return r;
	}

	/**
	 * Devolve o nº de livros dentro da colecção
	 */
	public int getNumLivros() {
		int count = 0;
		for (int i = 0; i < numObras; i++) {							// percorremos as obras
			if(obras[i] instanceof Livro) count++;						//caso a obra seja livro, incrementamos
			else if(obras[i] instanceof Coleccao){
				count+= ((Coleccao) obras[i]).getNumLivros();			//caso seja coleção, chamamos outra vez o getNumLivros
			}
		}
		return count;
	}

	/**
	 * Devolve o nº de colecções dentro da colecção
	 */
	public int getNumColeccoes() {
		int count = 0;
		for (int i = 0; i < numObras; i++) {							//percorremos as obras
			if(obras[i] instanceof Coleccao) count++;					//caso seja coleção incrementa
		}
		return count;
	}

	/**
	 * Devolve a profundidada de máxima de uma colecção em termos de coleccões
	 * dentro de coleccções: uma colecção c1 com uma colecção c2 dentro, c1 deve
	 * devolver 2 e c2 deve devolver 1, independentemente do número do conteúdo de
	 * cada uma.
	 */
	public int getProfundidade() {
		int  max = 0,aux;

		for (int i = 0; i < numObras; i++) {							// percorremos as obras
			if(obras[i] instanceof Coleccao){							//caso seja uma coleção
				aux=((Coleccao) obras[i]).getProfundidade();			// chamamos outra vez o getProfundidade
				if(aux >max){											// guardamos a profundidade máxima numa variavel max
					max=aux;
				}
			}
		}
		return max+1;
	}

	/**
	 * Duas colecções são iguais se tiverem o mesmo título e a mesma lista de
	 * editores. Deve utilizar o equals da classe Obra. Para verificar verificar se
	 * os editores são os mesmos devem utilizar o método mergeWithoutRepetitions
	 */
	public boolean equals(Object c) {

		if(super.equals(c)){
			return Arrays.equals(this.editores, mergeWithoutRepetitions(this.editores, ((Coleccao) c).editores));
		}
		return false;
	}

	/**
	 * Deve devolver uma string compatível com os outputs desejados
	 */
	public String toString() {
		return "\n" + super.getTitulo() + ", " + getNumPaginas() + "p, " + getPreco() + ", editores "
				+ Arrays.toString(editores) + ", com " + getNumLivros()+ " livros, com "
				+ getNumColeccoes() + " colecções e com profundidade máxima de " + getProfundidade() + "\n" + Arrays.toString(getObras_final()) + "\n";
	}

	/**
	 * Mostra uma colecção segundo os outputs desejados. Deve utilizar o método
	 * print da classe Obra.
	 */
	public void print(String prefix) {
		System.out.println(prefix + toString());
	}


	public Obra[] getObras_final(){

		Obra[] obras_final;

		List<Obra> list = new LinkedList<>(Arrays.asList(obras));
		while (list.remove(null)) {
		}
		list.remove("[");
		list.remove("]");
		obras_final = list.toArray(new Obra[list.size()]);

		return obras_final;

	}

	/**
	 * main
	 */
	public static void main(String[] args) {
		Livro l1 = new Livro("Viagem aos Himalaias", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l2 = new Livro("Viagem aos Pirinéus", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao c1 = new Coleccao("Primavera", new String[] { "João Mendonça", "Manuel Alfazema" });

		boolean res;

		res = c1.addObra(l1);
		res = c1.addObra(l2);
		System.out.println("c1 -> " + c1);
		//c1.print("");
		System.out.println();

		// adicionar um livro com nome de outro já existente
		res = c1.addObra(l2);
		System.out.println("adição novamente de Viagem aos Pirinéus a c1 -> " + res);
		System.out.println("c1 -> " + c1);
		System.out.println();

		// Outra colecção
		Livro l21 = new Livro("Viagem aos Himalaias 2", 340, 12.3f, new String[] { "João Mendonça", "Mário Andrade" });
		Livro l22 = new Livro("Viagem aos Pirinéus 2", 270, 11.5f, new String[] { "João Mendonça", "Júlio Pomar" });

		Coleccao cx2 = new Coleccao("Outono", new String[] { "João Mendonça", "Manuel Antunes" });
		cx2.addObra(l21);
		cx2.addObra(l22);
		System.out.println("cx2 -> " + cx2);
		//cx2.print("");
		System.out.println();

		// adicioná-la a c1
		c1.addObra(cx2);
		System.out.println("c1 após adição da colecção cx2 -> " + c1);
		//c1.print("");
		System.out.println();

		// get editores autores
		String[] ae = c1.getAutoresEditores();
		System.out.println("Autores editores of c1 -> " + Arrays.toString(ae));
		System.out.println();

		// getNumObrasFromPerson
		String nome = "João Mendonça";
		int n = c1.getNumObrasFromPerson(nome);
		System.out.println("Nº de obras de " + nome + " -> " + n);
		System.out.println();

		// getLivrosComoAutor
		nome = "João Mendonça";
		Livro[] livros = c1.getLivrosComoAutor(nome);
		System.out.println("Livros de " + nome + " -> " + Arrays.toString(livros));
		System.out.println();
		System.out.println();

		// testes aos métodos getNumLivros, getNumColeccoes e getProfundidade
		c1.print("");
		//System.out.println(Arrays.toString(c1.getEventos()));
		System.out.println("Nº de livros na colecção " + c1.getTitulo() + " -> " + c1.getNumLivros());

		System.out.println("Nº de colecções dentro da colecção " + c1.getTitulo() + " -> " + c1.getNumColeccoes());

		System.out.println("Profundidade da colecção " + c1.getTitulo() + " -> " + c1.getProfundidade());
		System.out.println("Profundidade da colecção " + cx2.getTitulo() + " -> " + cx2.getProfundidade());
		System.out.println();

		// rem livro
		String nomeLivro = "Viagem aos Himalaias";
		Obra l = c1.remObra(nomeLivro);
		System.out.println("Remoção de " + nomeLivro + " -> " + l);
		c1.print("");

	}
}