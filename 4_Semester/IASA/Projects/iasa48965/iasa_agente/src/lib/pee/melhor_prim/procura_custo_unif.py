from pee.melhor_prim.aval.avaliador_custo_unif import AvaliadorCustoUnif
from pee.melhor_prim.procura_melhor_prim import ProcuraMelhorPrim


class ProcuraCustoUnif(ProcuraMelhorPrim):

    def __init__(self):
        """
        Critério de exploração: custo da solução

        A avaliacao do custo é feita por custo ate ao no
        """
        super().__init__(AvaliadorCustoUnif())
