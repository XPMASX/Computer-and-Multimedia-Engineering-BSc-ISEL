from pee.prof.procura_profundidade import ProcuraProfundidade


class ProcuraProfLim(ProcuraProfundidade):
    """

    tem um limite de profundidade fixo, ou seja, a busca não vai além de um determinado nível de profundidade. É útil
    quando é necessário controlar o consumo de recursos, como memória, e evitar buscas muito profundas.

    """

    def __init__(self, prof_max=100):
        super().__init__()
        self.__prof_max = prof_max

    def _expandir(self, problema, no):
        """
        Expande nó se a sua profundidade for inferior à
        profundidade máxima da procura

        Returns: Devolve o yield da classe pai
        """

        if no.profundidade < self.__prof_max:
            yield from super()._expandir(problema, no)

    def _memorizar(self, no):

        if self._ciclo(no) is False:
            return super()._memorizar(no)

    def _ciclo(self, no):
        """
        Verifica se nó corresponde a um ciclo no ramo
        respetivo, para evitar a expansão de nós referentes a
        estados já explorados (não evita ciclos em relação a
        outros ramos
        """

        no_ramo = no.antecessor
        while no_ramo:
            if no.estado == no_ramo.estado:
                return True
            no_ramo = no_ramo.antecessor
        return False

    @property
    def prof_max(self):
        """
        getter
        Returns: profundidade max
        """
        return self.__prof_max

    @prof_max.setter
    def prof_max(self, valor):
        """
        setter
        Args:
            valor: Novo valor da profundidade max
        """
        self.__prof_max = valor
