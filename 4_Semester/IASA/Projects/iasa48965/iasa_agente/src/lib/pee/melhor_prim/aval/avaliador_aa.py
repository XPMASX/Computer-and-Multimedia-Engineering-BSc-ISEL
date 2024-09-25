from pee.melhor_prim.aval.avaliador_heur import AvaliadorHeur


class AvaliadorAA(AvaliadorHeur):
    """
     Uma das variantes de avaliador com base em heuristica.

     É usado no método de Procura A* (classeProcuraAA) tendo em conta, para além da heurística (avaçialão h(n)) também
     o custo do nó em estudo (g(n)).

    Pretende minimizar o custo de exploração.
    """

    def prioridade(self, no):
        """
        Sendo f(n) = g(n) + h(n), retorna-se a soma do custo do nó com o resultado
        do método h() da heuristica usada para inicializar o método de procura que utiliza este avaliador.

        Args:
            no: no cuja prioridade deve ser avaliada

        Returns: prioridade do nó

        """
        return no.custo + self._heuristica.h(no.estado)
