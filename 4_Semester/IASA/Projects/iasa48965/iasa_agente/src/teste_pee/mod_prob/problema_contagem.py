from mod.problema.problema import Problema
from .estado_valor import EstadoValor
from .operador_incremento import OperadorIncremento


class ProblemaContagem(Problema):

    def __init__(self, vi, vf, incrementos):
        super().__init__(EstadoValor(vi), [OperadorIncremento(inc) for inc in incrementos])
        self.__valor_final = vf

    def objetivo(self, estado):

        return estado.valor >= self.__valor_final
