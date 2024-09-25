from mod.estado import Estado
from mod.operador import Operador


class EstadoPosicao(Estado):

    def __init__(self, x, y):
        self.__x = x
        self.__y = y

    def id_valor(self):
        id_gerado = hash((self.__x, self.__y))
        print(id_gerado)
        return id_gerado


print(EstadoPosicao(0, 0) == EstadoPosicao(0, 0))


class EstadoContagem(Estado):

    def __init__(self, valor):
        self.__valor = valor

    @property
    def valor(self):
        return self.__valor

    def id_valor(self):
        return self.__valor


class OperadorIncremento(Operador):

    def custo(self, estado, estado_suc):
        return 1

    def aplicar(self, estado):
        return EstadoContagem(estado.valor + 1)


class OperadorDecremento(Operador):

    def custo(self, estado, estado_suc):
        return 1

    def aplicar(self, estado):
        return EstadoContagem(estado.valor - 1)


estado_suc_inc = OperadorIncremento().aplicar(EstadoContagem(1))
print(estado_suc_inc.valor)
estado_suc_dec = OperadorDecremento().aplicar(EstadoContagem(1))
print(estado_suc_dec.valor)
