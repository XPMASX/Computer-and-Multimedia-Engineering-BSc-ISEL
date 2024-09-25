from pee.melhor_prim.aval.avaliador_aa import AvaliadorAA
from pee.melhor_prim.procura_informada import ProcuraInformada


class ProcuraAA(ProcuraInformada):
    """
    Tipo de procura informada.

    Este mecanismo de procura tenta encontrar o caminho menor entre os estados iniciais e finais.
    Toma partido de uma heuristica admíssivel, isto é, uma heurística optimista que prevê sempre custos/valores inferiores ou iguais
    ao custo efetivo mínimo.

    Uma heuristica consistente é sempre uma heuristica admissivel, mas o oposto nem sempre é verdade: enquanto a segunda dá valores
    necessáriamente inferiores ao custo óptimo, uma heurística consistende garante ainda que à medida que são gerados mais nós (intermédios
    no percurso) a previsão da heuristica de dado nó n será sempre inferior à soma da heurística do sucessor e do custo de aplicação do
    operador que permite a transição entre os estados que cada um representa.

    """

    def __init__(self):
        super().__init__(AvaliadorAA())
