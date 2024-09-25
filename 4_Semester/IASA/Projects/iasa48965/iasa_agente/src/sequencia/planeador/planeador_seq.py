from pee.melhor_prim.procura_aa import ProcuraAA
from pee.melhor_prim.procura_sofrega import ProcuraSofrega
from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from sequencia.mod_prob.heur_seq import HeurSeq
from sequencia.mod_prob.problema_seq import ProblemaSeq


class PlaneadorSeq:

    def __init__(self, seq_inicial, seq_final):

        self.__seq_inicial = seq_inicial
        self.__seq_final = seq_final
        self.__problema = ProblemaSeq(seq_inicial, seq_final)
        #self.__mec_proc = ProcuraCustoUnif()
        self.__mec_proc = ProcuraAA()
        self.__mec_proc = ProcuraSofrega()

    def planear(self):

        """
        Depois de instanciado o problema e o mecanismo de procura procuramos o problema e retornamos a solucao

        Usamos ProcuraCustoUnif ou ProcuraAA

        Returns: devolve a solucao

        """

        heuristica = HeurSeq(self.__seq_final)
        #solucao = self.__mec_proc.procurar(self.__problema)
        solucao = self.__mec_proc.procurar(self.__problema, heuristica)

        return solucao

    @property
    def complexidade_temporal(self):
        return self.__mec_proc.complexidade_temporal

    @property
    def complexidade_espacial(self):
        return self.__mec_proc.complexidade_espacial()
