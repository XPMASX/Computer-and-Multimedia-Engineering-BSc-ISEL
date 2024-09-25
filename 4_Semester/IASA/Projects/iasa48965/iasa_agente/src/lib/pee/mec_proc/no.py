class No:

    def __init__(self, estado, operador=None, antecessor=None):
        self.__estado = estado
        self.__operador = operador
        self.__antecessor = antecessor
        self.__profundidade = 0
        self.__custo = 0
        if antecessor:
            self.__profundidade = antecessor.profundidade + 1
            self.__custo = antecessor.custo + operador.custo(antecessor.estado, estado)

    @property
    def estado(self):
        """
        Retorna o estado do no
        """
        return self.__estado

    @property
    def operador(self):
        """
        Retorna o operadaor
        """
        return self.__operador

    @property
    def antecessor(self):
        """
        Retorna o antecessor do no
        """
        return self.__antecessor

    @property
    def profundidade(self):
        """
        Retorna a profundidade do no
        """
        return self.__profundidade

    @property
    def custo(self):
        """
        Retorna o custo de alterar de estado
        """
        return self.__custo

    def __lt__(self, no):
        """
        Define relação “menor” (“less than”) de comparação entre nós
        """
        return self.custo < no.custo
