from pee.mec_proc.fronteira.fronteira_fifo import FronteiraFIFO
from pee.mec_proc.procura_grafo import ProcuraGrafo


class ProcuraLargura(ProcuraGrafo):
    """
    Na procura em largura, a procura decorre explorando
    primeiro os nós mais antigos (primeiros a ser gerados),
    levando à exploração exaustiva de cada nível de procura
    antes da exploração de nós a um nível de maior profundidade

    É útil quando é necessário encontrar a solução mais próxima do nó inicial.

    Critério de exploração: menor profundidade

    """

    def __init__(self):
        super().__init__(FronteiraFIFO())
