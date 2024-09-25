from mod.problema.problema import Problema
from sequencia.mod_prob.estado_seq import EstadoSeq
from sequencia.mod_prob.operador_troca import OperadorTroca
from sequencia.planeador.troca import Troca


class ProblemaSeq(Problema):

    def __init__(self, seq_inicial, seq_final):
        """
        1- Criamos uma lista vazia onde vão ser guardadas todas as combinações possiveis assim como os seus custos
        (Sendo o custo o valor absoluto da diferença entre os indices da determinada combinação)
        2- Criamos um Problema com a sequência inicial e um operador onde vão ser passados cada elemento da nova lista
        3- Guardamos o estado final, ou seja, o objetivo

        Args:
            seq_inicial: partida
            seq_final: chegada
        """

        trocas = []
        for i in range(len(seq_final.sequencia)):
            for j in range(len(seq_final.sequencia)):
                if i != j:
                    custo = abs(i - j)
                    troca = Troca(i, j, custo)
                    trocas.append(troca)

        super().__init__(seq_inicial,
                         [OperadorTroca(troca.idx1, troca.idx2, troca.custo) for troca in trocas])

        self.__estado_final = seq_final

    def objetivo(self, estado):
        """
        Returna true se chegámos ao objetivo

        Args:
            estado: estado atual

        Returns: Chegámos ao destino?

        """

        return estado == self.__estado_final
