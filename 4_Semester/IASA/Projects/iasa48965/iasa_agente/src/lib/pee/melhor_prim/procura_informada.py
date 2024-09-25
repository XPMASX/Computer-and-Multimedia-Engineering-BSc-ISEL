from abc import ABC

from pee.melhor_prim.procura_melhor_prim import ProcuraMelhorPrim


class ProcuraInformada(ProcuraMelhorPrim, ABC):

    """
    Mecanismo de exploração que tira partido do conehcimento do domínio do problema.

    Qualquer estratégia de procura baseia-se na ordem com que os nós são inseridos na fronteira de exploração.
    No caso da procura informada, tira-se-á partido de uma heurística (ver classe Heuristica) de forma a obter uma
    estimativa do custo do percurso entre dois nós.

    É uma classe abstrata, sendo posteriormente estendida pelas classes ProcuraAA e ProcuraSofrega.
    Vai especializar a classe ProcuraMelhorPrim, implementando o método resolver() que permite chegar à solução do problema.
    """

    def procurar(self, problema, heuristica):

        """
        Implementa o algoritmo de procura de um problema com base numa heuristica recebida pelo método.

        Args:
            problema: problema a solucionar
            heuristica: heuristica de base de solução

        Returns: solução do problema enviado

        """
        self._heuristica = heuristica
        self._avaliador.definir_heuristica(heuristica)
        return super().procurar(problema)
