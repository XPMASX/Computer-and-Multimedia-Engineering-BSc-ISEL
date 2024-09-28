package tp2.pack1ColeccoesComHeranca;

public abstract class Obra implements IObra {

	protected int length;
	private String titulo;

	/**
	 * Constructor
	 */
	public Obra(String titulo) {
		if (titulo == null || titulo.length() == 0)
			throw new IllegalArgumentException("O titulo tem de ter pelo menos um caracter");
		this.titulo = titulo;
	}

	/**
	 * Devolve o título da obra
	 */
	@Override
	public String getTitulo() {
		return this.titulo;
	}

	/**
	 * Devolve o número de páginas da obra
	 */
	@Override
	public abstract int getNumPaginas();

	/**
	 * Devolve o preço da obra
	 */
	@Override
	public abstract float getPreco();

	/**
	 * Deve devolver true se o array conter apenas nomes válidos. Cada nome deve ser
	 * validado pelo método validarNome
	 */

	public static boolean validarNomes(String[] nomes) {
		//percoreemos o array de strings
		for (int i = 0; i < nomes.length; i++) {
			//chamamos o método validarNome. caso falso, retornamos false
			if(!validarNome(nomes[i])) return false;
		}
		return true;
	}

	/**
	 * Um nome válido se não for null e conter pelo menos uma letra
	 * (Character.isLetter) e só conter letras e espaços (Character.isWhitespace)
	 */
	public static boolean validarNome(String nome) {
		if (nome != null) {
			// percorremos a string nome
			for (int i = 0; i < nome.length(); i++) {
				//quando não é letra, ou não letra e não espaço, retorna false
				if (!Character.isLetter(nome.charAt(i)) || !(Character.isLetter(nome.charAt(i)) && !Character.isWhitespace(nome.charAt(i)))) return false;
			}
		}
		return true;
	}

	/**
	 * Recebe um nome já previamente validado, ou seja só com letras ou espaços.
	 * Deve devolver o mesmo nome mas sem espaços (utilizar trim e
	 * Character.isWhitespace) no início nem no fim e só com um espaço ' ' entre
	 * cada nome. Deve utilizar um StringBuilder para ir contendo o nome já
	 * corrigido
	 */
	public static String removeExtraSpaces(String nome) {
		StringBuilder nome_final = new StringBuilder();
		// retiramos os espaços á esquerda da string e a direita
		String novo_nome = nome.trim();
		// percorremos o novo nome sem os espaços
		for (int i = 0; i < novo_nome.length(); i++) {
			// se é um espaço e for igual ao caracter anterior, não faz append
			if(Character.isWhitespace(novo_nome.charAt(i)) && novo_nome.charAt(i) == novo_nome.charAt(i-1)) continue;
			nome_final.append(novo_nome);
		}
		return nome_final.toString();
	}

	/**
	 * Método que verifica se há elementos repetidos. O array recebido não contém
	 * nulls.
	 */
	public static boolean haRepeticoes(String[] elems) {
		//percorremos o array de strings
		for (int i = 0; i < elems.length; i++) {
			//percorremos o array de strings a começar do i para a frente
			for (int j =i+1; j < elems.length; j++) {
				//caso houver repetidos, returnamos false (chamamos o removeExtraSpaces para os arrays de string)
				if(removeExtraSpaces(elems[i]).equals(removeExtraSpaces(elems[j]))) return false;
			}
		}
		return true;
	}

	/**
	 * Devolve uma string com a informação da obra (ver outputs desejados e método
	 * toString de Livro)
	 */
	@Override
	public String toString() {
		return getTitulo() + " " + getNumPaginas() + "p " + getPreco() + " ";
	}

	/**
	 * Deve mostrar na consola a informação da obra (toString) precedida do prefixo
	 * recebido
	 */
	@Override
	public void print(String prefix) {
		System.out.println(prefix + toString());
	}

	/**
	 * O Object recebido é igual, se não for null, se for uma obra e se tiver o
	 * mesmo título que o título da obra corrente
	 */
	@Override
	public boolean equals(Object l) {
		if(l==null || !(l instanceof Obra)) return false;
		return (this.titulo.equals(((Obra) l).titulo));
	}

}

