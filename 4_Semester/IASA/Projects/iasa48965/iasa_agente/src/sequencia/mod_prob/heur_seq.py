from pee.melhor_prim.aval.heuristica import Heuristica


class HeurSeq(Heuristica):
    def __init__(self, estado_final):
        self.__estado_final = estado_final

    def h(self, estado):
        """
        A Heuristica vai ser o número de posições erradas comparando com a sequrncia final

        Args:
            estado: sequencia atual

        Returns: Devolve numero de posiçoes erradas

        """
        num = 0
        for i in range(len(self.__estado_final.sequencia)):
            if self.__estado_final.sequencia[i] != estado.sequencia[i]:
                num += 1
        return num
