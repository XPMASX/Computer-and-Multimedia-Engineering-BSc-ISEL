from pee.prof.procura_prof_lim import ProcuraProfLim


class ProcuraProfIter(ProcuraProfLim):

    """

    Combina a busca em profundidade com a busca em largura, ao procurar em profundidade com limites crescentes
    de profundidade até encontrar a solução. É útil quando queremos aproveitar as vantagens da busca em
    profundidade, mas também garantir que todas as profundidades sejam exploradas.

    """

    def procurar(self, problema, inc_prof=1, limite_prof=100):
        """
        Procuramos no problema enquanto tivermos abaixo do limite de profundidade
        Para um limite de profundidade crescente
        Realizar procura com o limite atual
        Se existe solução, retornar solução


        Args:
            problema: vamos procurar
            inc_prof: incremento
            limite_prof: limite da procura

        Returns: devolve a solução

        """

        for profundidade in range(0, limite_prof, inc_prof):
            self.prof_max = profundidade
            result = super().procurar(problema)

            if result is not None:
                return result
