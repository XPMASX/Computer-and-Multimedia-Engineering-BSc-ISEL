from mod.estado import Estado


class EstadoValor(Estado):

    def __init__(self, valor_inicial):
        self.__valor = valor_inicial
        self.__id_valor = hash(self.__valor)

    @property
    def valor(self):
        return self.__valor

    def id_valor(self):
        return self.__id_valor

    def __hash__(self):
        return hash(self.valor)

    def __eq__(self, other):
        return self.__hash__() == other.__hash__()
