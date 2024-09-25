from pee.melhor_prim.aval.avaliador_sof import AvaliadorSof
from pee.melhor_prim.procura_informada import ProcuraInformada


class ProcuraSofrega(ProcuraInformada):
    """
    Tipo de procura informada.

    Nesta pesquisa, o algoritmo seleciona sempre o caminho que aparenta ser o melhor no momento. Construindo a sua solução passo a
    passo. É uma pesquisa completa (encontra sempre solução se existir) mas pode não produzir a solução óptima, isto por não ter
    sempre em conta os constrangimentos que lhe podem ser impostos pelo caminho.

    É um algoritmo heuristico, ou seja, vai utilizar (neste caso, apenas!) uma função heuristica para solucionar o problema.
    Como tal, nesta classe herdeira de ProcuraInformada, será inicializado um Avaliador do tipo AvaliadorSof.
    """

    def __init__(self):
        super().__init__(AvaliadorSof())
