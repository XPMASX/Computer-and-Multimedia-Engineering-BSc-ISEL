import math

from pee.melhor_prim.aval.heuristica import Heuristica


class HeurDist(Heuristica):

    def __init__(self, estado_final):
        self.__estado_final = estado_final

    def h(self, estado):
        return math.dist(self.__estado_final.posicao, estado.posicao)
