from pee.mec_proc.fronteira.fronteira import Fronteira
from heapq import heappush, heappop


class FronteiraPrioridade(Fronteira):

    def __init__(self, avaliador):
        super().__init__()
        self._avaliador = avaliador

    def inserir(self, no):
        """
        heapq - transforma lista numa fila
        Args:
            no:

        Returns:

        """
        prioridade = self._avaliador.prioridade(no)
        # insere tuplos em que o primeiro parametro é a prioridade
        heappush(self._nos, (prioridade, no))

    def remover(self):
        # heappop retorna um no
        (_, no) = heappop(self._nos)
        return no

    @property
    def avaliador(self):
        return self._avaliador
