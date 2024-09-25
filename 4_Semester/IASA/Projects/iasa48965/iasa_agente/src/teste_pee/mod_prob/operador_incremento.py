from abc import ABC, abstractmethod

from mod.operador import Operador
from .estado_valor import EstadoValor


class OperadorIncremento(Operador, ABC):

    def __init__(self, incremento):
        self.__incremento = incremento

    @property
    def incremento(self):
        return self.__incremento

    def aplicar(self, estado):
        """
        Somar o valor do estado com o incremento
        Args:
            estado: onde vamos buscar o valor atual

        Returns: soma dos valores

        """
        return EstadoValor(estado.valor + self.__incremento)

    def custo(self, estado, estado_suc):
        """
        O custo é o quadrado do incremento

        Args:
            estado: Não utilizado
            estado_suc: Não utilizado

        Returns: Devolve o custo

        """
        return self.__incremento ** 2
