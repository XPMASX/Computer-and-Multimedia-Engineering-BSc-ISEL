from pdm.modelo.modelo_pdm import ModeloPDM
from plan.modelo.modelo_plan import ModeloPlan


class ModeloPDMPlan(ModeloPlan, ModeloPDM):

    def __init__(self, modelo_plan, objectivos, rmax=1000):
        super().__init__()
        self.__modelo_plan = modelo_plan
        self.__objectivos = objectivos
        self.__rmax = rmax
        self.__transicoes = {}

        for s in self.obter_estados():
            for a in self.obter_operadores():
                # Modelo determinista
                # aplicamos o operador a ao estado s e guardamos num dicionario
                sn = a.aplicar(s)
                if sn:
                    self.__transicoes[(s, a)] = sn

    def obter_estado(self):
        return self.__modelo_plan.obter_estado()

    def obter_estados(self):
        return self.__modelo_plan.obter_estados()

    def obter_operadores(self):
        return self.__modelo_plan.obter_operadores()

    def S(self):
        """
        Returns: Devolve os estados
        """
        return self.obter_estados()

    def A(self, s):
        """
        Returns: Devolve os operadores
        """
        return self.obter_operadores()

    def T(self, s, a, sn):
        """
        Devolve a probabilida da transição de um estado para outro
        através de um operador a.
        Neste contexto a aplicação de um operador tem apenas 0% ou 100% de probabilidade de sucesso.
        Verificamos se existe um estado sucessor e se este existir retornamos 1 se não 0

        Args:
            s: estado
            a: operador ação
            sn: estado seguinte

        Returns: Devolve a probabilidade de sucesso da transiçãp

        """
        if self.__transicoes.get((s, a)) == sn:
            return 1
        return 0

    def R(self, s, a, sn):
        """
        Calcula e devolve a recompensa ao efetuar um passo entre os estados s e sn
        Existe sempre um custo negativo ao realizarmos um passo mas adicionamos a rmax
        se este chegar a algum objetivo

        Args:
            s: estado atual
            a: accao
            sn: estado seguinte

        Returns: Devolve a recompensa

        """
        r = -a.custo(s, sn)
        if sn in self.__objectivos:
            r += self.__rmax

        return r

    def estados_sucessores(self, s, a):
        """
        Se exisitir transicao para o estado s accao a adicionamos estado sucessor a uma lista

        Returns: Devolve uma lista com os estados sucessores
        """
        """
        Solução também funciona e foi entrega da semana 11
        
        estados_suc = []

        if self.__transicoes.get((s, a)):
            estados_suc.append(self.__transicoes.get((s, a)))
        """

        sn = self.__transicoes.get((s,a))
        return [sn] if sn else []
