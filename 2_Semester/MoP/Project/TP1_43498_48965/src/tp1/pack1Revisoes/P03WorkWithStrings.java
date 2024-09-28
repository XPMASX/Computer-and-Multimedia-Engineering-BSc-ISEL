package tp1.pack1Revisoes;

public class P03WorkWithStrings {

    /* Main, método de arranque da execução
     */
    public static void main(String[] args) {
        test_compareStrings(null, null); // result = 0
        test_compareStrings(null, ""); // result = -1
        test_compareStrings("", null); // result = 1
        test_compareStrings("a", ""); // result = 1
        test_compareStrings("", "a"); // result = -1
        test_compareStrings("a", "a"); // result = 0
        test_compareStrings("b", "a"); // result = 1
        test_compareStrings("a", "b"); // result = -1
        test_compareStrings("aa", "a"); // result = 2
        test_compareStrings("a", "aa"); // result = -2
        test_compareStrings("aa", "aa"); // result = 0
        test_compareStrings("ab", "aa"); // result = 2
        test_compareStrings("ab", "ab"); // result = 0
        test_compareStrings("abc", "abc"); // result = 0
        test_compareStrings("abc", "abd"); // result = -3
    }

    /* Este método recebe duas Strings s1 e s2 e procede à sua comparação,
     * devolvendo um valor positivo se s1 for maior que s2, negativo se ao
     * contrário e 0 se iguais. A comparação deve ser feita primeiro em termos
     * lexicográficos caracter a caracter começando pelos caracteres de menor
     * peso ou em segundo lugar em termos de número de caracteres. Se diferentes
     * deve devolver o índice +1/-1 do caractere que faz a diferença. Ex.
     * s1="Bom", s2="Dia", deve devolver -1; s1="Boa", s2="Bom", deve devolver
     * -3; s1="Bom", s2="Bo", deve devolver 3. Uma String a null é considerada
     * menor que uma string não null.
     *
     * @param s1 string a comparar
     * @param s2 string a comparar
     * @return o resultado da comparação
     */
    private static int compareStrings(String s1, String s2) {
        int aux = 0, count = 0;
        //se a 1ª string for null e a 2ª tambem devolve 0 se for so a 1ª devolve -1 se for so a 2ª devolve 1
        if(s1==null){
            if(s2==null) return 0;
            else return -1;
        }else if (s2 == null) return 1;

        /*se as duas strings tiverem o mesmo tamanho comparar os caracteres das duas strings na posição i
        se forem iguais o count não muda de valor se o s1 for maior que o s2 soma-se 1 se for menor subtrai-se 1 ao count  */
        if(s1.length() == s2.length()){
            for (int i = 0; i < s1.length(); i++) {
                int compare = Character.compare(s1.charAt(i),s2.charAt(i));
                if (compare > 0){
                    count += i+1;
                }else if (compare < 0){
                    count -= i+1;
                }

            }
            return count;
        }
        //se s1 maior que s2 devolve 1 + o tamanho de s2 se o contrário subtrai-se a -1 o tamanho de s1 e devolve-se esse resultado
        if(s1.length() > s2.length()) {
            aux = 1;
            return aux + s2.length();
        }else {
            aux = -1;
            return aux - s1.length();
        }

    }

    /**
     * Auxiliary method that call compareStrings with two strings
     */
    private static void test_compareStrings(String s1, String s2) {
        try {
            System.out.print("compareStrings (" + s1 + ", " + s2 + ") = ");
            int res = compareStrings(s1, s2);
            System.out.println(res);

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
