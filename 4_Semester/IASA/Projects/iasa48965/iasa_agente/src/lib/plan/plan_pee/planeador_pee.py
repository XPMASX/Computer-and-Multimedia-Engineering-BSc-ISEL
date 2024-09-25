from pee.melhor_prim.procura_aa import ProcuraAA
from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from pee.melhor_prim.procura_sofrega import ProcuraSofrega
from plan.plan_pee.mod_prob.heur_dist import HeurDist
from plan.plan_pee.mod_prob.problema_plan import ProblemaPlan
from plan.plan_pee.plano_pee import PlanoPEE
from plan.planeador import Planeador

"""
Classe PlaneadorPEE
    Planeador com base na Procura em Espaço de Estados.
    Implementa a interface Planeador.
    
    Com a informação do domínio do mundo cria um trajeto que permite ao agente movimentar-se desde o estado
    inicial até ao estado final do problema.
"""


class PlaneadorPEE(Planeador):

    def __init__(self, tipoProcura):
        """
        Inicializa o mecanismo de procura a ser utilizado (ProcuraAA)
        Também inicializa a solução, que pode ou não existir ao longo da execução - começando a None.
        """

        if tipoProcura.value == 1:
            self.__mec_pee = ProcuraCustoUnif()
        if tipoProcura.value == 2:
            self.__mec_pee = ProcuraSofrega()
        if tipoProcura.value == 3:
            self.__mec_pee = ProcuraAA()
        self.__tipo_procura = tipoProcura.value
        self.__solucao = None

    def planear(self, modelo_plan, objetivos):
        """
        Ativa o método procurar do mecanismo de procura em uso, guardando a solução obtida.
        Inicializa assim o problema a resolver, enviando o modelo de planeamento e o primeiro objetivo a cumprir.
        Sendo a heuristica em uso baseada na distância, inicializa-se uma HeurDist.
        Args:
            modelo_plan: modelo de planeamento
            objetivos: pbjetivos a cumprir pelo agente

        Returns: Devolve o plano

        """
        problema = ProblemaPlan(modelo_plan, objetivos[0])
        heuristica = HeurDist(objetivos[0])
        if self.__tipo_procura != 1:
            self.__solucao = self.__mec_pee.procurar(problema, heuristica)
        else:
            self.__solucao = self.__mec_pee.procurar(problema)
        return PlanoPEE(self.__solucao)

    @property
    def complexidade_temporal(self):
        return self.__mec_pee.complexidade_temporal

    @property
    def complexidade_espacial(self):
        return self.__mec_pee.complexidade_espacial()
