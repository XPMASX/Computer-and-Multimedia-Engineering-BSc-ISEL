from abc import ABC

from pee.mec_proc.fronteira.fronteira_prioridade import FronteiraPrioridade
from pee.mec_proc.procura_grafo import ProcuraGrafo

"""
    Tipo de mecanismo de procura em grafo.

    Com base numa função f(n), sendo n um nó, obtem a avaliação de cada nó n gerado.
    O construtor receve um Avaliador que indica o critério com o qual o "melhor" vai ser avaliado.
    Usa uma fronteira do tipo prioridade, que será organizada por ordem crescente de f(n).
    Quanto menor o valor de f(n) melhor.

    Classe abstrata extendida por ProcuraCustoUnif, que definirá f(n) como uma avaliação por custo - que é
    a forma mais generalizada pesquisa.
"""


class ProcuraMelhorPrim(ProcuraGrafo, ABC):

    def __init__(self, avaliador):
        super().__init__(FronteiraPrioridade(avaliador))
        self._avaliador = avaliador

    def _manter(self, no):
        """
        Implementar a condição manter
        mantem se não existir nos explorados ou se o no tiver custo
        inferior ao no dos explorados
        """
        return super()._manter(no) or no < self._explorados[no.estado]
