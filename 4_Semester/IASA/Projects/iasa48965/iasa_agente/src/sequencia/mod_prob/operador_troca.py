from mod.operador import Operador
from sequencia.mod_prob.estado_seq import EstadoSeq


class OperadorTroca(Operador):

    def __init__(self, idx1, idx2, custo):
        self.__idx1 = idx1
        self.__idx2 = idx2
        self.__custo = custo

    def aplicar(self, estado):
        """
        1- Criamos uma sequência igual à cópia da sequência recebida
        2- Nesta nova sequência fazemos as alteracões de acordo com os indices guardados no construtor
        3- Retornamos um EstadoSeq da nova sequência

        Args:
            estado: sequencia atual

        Returns: sequencia atualizada

        """

        nova_sequencia = estado.sequencia.copy()

        nova_sequencia[self.__idx1], nova_sequencia[self.__idx2] = \
            estado.sequencia[self.__idx2], estado.sequencia[self.__idx1]

        return EstadoSeq(nova_sequencia)

    def custo(self, estado, estado_suc):
        return self.__custo

    def __repr__(self):
        """
        Representação da Troca efetuada pelos indices
        """
        return "Troca(" + str(self.__idx1) + ", " + str(self.__idx2) + ")"
