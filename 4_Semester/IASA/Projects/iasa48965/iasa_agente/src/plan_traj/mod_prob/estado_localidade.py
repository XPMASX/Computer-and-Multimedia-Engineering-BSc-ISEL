from mod.estado import Estado


class EstadoLocalidade(Estado):

    def __init__(self, localidade):
        self.__localidade = localidade
        self.__id_valor = hash(self.__localidade)

    def id_valor(self):
        return self.__id_valor

    @property
    def localidade(self):
        return self.__localidade

    def __repr__(self):

        return self.__localidade

