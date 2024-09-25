class MecUtil:

    def __init__(self, modelo, gama, delta_max):
        self.__modelo = modelo
        self.__gama = gama
        self.__delta_max = delta_max

    def utilidade(self):
        """
        Calcula a utilidade de todos os estados
        1- Iniciamos a utilidade, um dicionario estado:utilidade
        2- Iteramos o dicionario obtendo o maximo da utilidade da ação
        3-Repetimos até obtermos a fórmula óptima, ou seja, quando já não há mais
        alterações face aos resultados de utilidade

        Returns: Devolve utilidade baseada no modelo
        """

        # Variáveis explicativas que simulam as formulas
        S, A = self.__modelo.S, self.__modelo.A
        U = {s: 0.0 for s in S()}

        while True:
            Uant = U.copy()
            delta = 0

            for s in S():
                U[s] = max([self.util_accao(s, a, Uant) for a in A(s)], default=0)
                delta = max(delta, abs(U[s] - Uant[s]))

            if delta < self.__delta_max:
                break

        return U

    def util_accao(self, s, a, U):
        """
        Calculamos o somatorio percorrendo os estados seguintes

        Returns: Devolve o resultado do somatório
        """

        T, R, suc = self.__modelo.T, self.__modelo.R, self.__modelo.estados_sucessores
        return sum(T(s, a, sn) * (R(s, a, sn) + self.__gama * U[sn]) for sn in suc(s, a))
