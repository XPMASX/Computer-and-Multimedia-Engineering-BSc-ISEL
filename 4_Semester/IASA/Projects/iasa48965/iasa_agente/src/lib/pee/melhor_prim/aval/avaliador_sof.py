from pee.melhor_prim.aval.avaliador_heur import AvaliadorHeur


class AvaliadorSof(AvaliadorHeur):
    """
    Uma das variantes de avaliador com base em heuristica.

    É usado no método de Procura Sôfrega, e tem apenas em conta a própria heurística. Heurística esta que:
        > Não tem em consideração o custo do percurso até aqui explorado.
        > Procura minimizar o custo total;
        > Obtém soluções não ótimas, isto é, nem sempre encontra a melhor solução para o problema.

    Podemos dizer que f(n) = h(n)

    Onde n será um nó, f() a sua prioridade e h() o valor da avaliação pela heurística.
    """

    def prioridade(self, no):
        """
        Sendo f(n) = h(n) basta retornar o resultado
        do método h() da heuristica usada para inicializar o método de procura
        que utiliza este avaliador (estimativa de custo).
        Args:
            no: no cuja prioridade deve ser avaliada

        Returns: prioridade do nó

        """
        return self._heuristica.h(no.estado)
