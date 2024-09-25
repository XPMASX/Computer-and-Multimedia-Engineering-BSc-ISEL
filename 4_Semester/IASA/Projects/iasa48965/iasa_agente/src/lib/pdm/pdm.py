from pdm.mec_util import MecUtil


class PDM:

    """
    Segue a Propriedade de Markov: "A previsão dos estados seguintes só depende do estado atual".
    Qualquer sistema que implemente esta propriedade de decisão tratará um Processo de Decisão de Markov.

    É necessário simular os vários caminhos/sequências de ações que se podem desencadear no futuro (ações, estados e
    recompensas, podendo as últimas ser ganhos, se  positivas, ou perdas, se negativas). Esta representação é a chamada
    Cadeia de Markov, e no contexto de implementação presente será auxilizado pelos métodos de uma classe que implemente
    a interface ModeloPDM, que terá uma relação de composição com esta classe.
    """

    def __init__(self, modelo, gama, delta_max):
        """
        Args:
            modelo: representação do mundo sob a forma de PDM
            gama: fator de desconto no visto de recompensas descontadas, varia entre [0, 1] sendo,
                    preferencialmente, um valor mais aproximado de 1;
          delta_max: critério de paragem de iteração (ver documentação utilidade());
        """
        self.__mec_util = MecUtil(modelo, gama, delta_max)
        self.__modelo = modelo

    def politica(self, U):
        """
        Retorna a estratégia da ação ao obtermos o máximo do conjunto de acordo
        com a funcao de avaliação util, ou seja, ficamos com a ação com maior utilidade

        Args:
            U: Utilidade

        Returns: Retorna a estratégia da ação
        """

        S, A = self.__modelo.S, self.__modelo.A
        pol = {}
        for s in S():
            if A(s):
                pol[s] = max(A(s), key=lambda a: self.__mec_util.util_accao(s, a, U))

        return pol

    def resolver(self):
        """
        Resolver pelo Processo de Decisão de Markov em que calculamos a utilidade e a politica.

        Returns: Devolvemos utilidade e politica do modelo guardado
        """

        U = self.__mec_util.utilidade()
        pol = self.politica(U)

        return U, pol
