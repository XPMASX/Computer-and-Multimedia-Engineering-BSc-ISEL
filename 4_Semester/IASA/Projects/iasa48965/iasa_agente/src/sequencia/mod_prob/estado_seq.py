from mod.estado import Estado


class EstadoSeq(Estado):

    def __init__(self, sequencia):
        self.__sequencia = sequencia
        self.__id_valor = hash(tuple(self.__sequencia))

    def id_valor(self):
        return self.__id_valor

    @property
    def sequencia(self):
        return self.__sequencia

    def __repr__(self):
        if self.sequencia:
            return str(self.sequencia)
        return ""
