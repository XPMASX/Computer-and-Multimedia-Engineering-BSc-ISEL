from pee.mec_proc.fronteira.fronteira_lifo import FronteiraLIFO
from pee.mec_proc.mec_proc import MecanismoProcura


class ProcuraProfundidade(MecanismoProcura):
    """
    Na procura em profundidade, a procura decorre
    explorando primeiro os nós mais recentes
    (últimos a ser gerados), aumentando por isso a
    profundidade do ramo corrente de procura

    Critério de exploração: maior profundidade
    Variantes:
    PROCURA EM PROFUNDIDADE LIMITADA
    PROCURA EM PROFUNDIDADE ITERATIVA

    """

    def __init__(self):
        super().__init__(FronteiraLIFO())
        self.__num_max_nos_memoria = 0

    def _memorizar(self, no):
        """
        Memoriza um nó de acordo com o tipo de procura,
        concretiza o método abstracto do mecanismo de procura
        """
        if self.__num_max_nos_memoria > self._fronteira.dimensao:
            self.__num_max_nos_memoria = self._fronteira.dimensao

        self._fronteira.inserir(no)
