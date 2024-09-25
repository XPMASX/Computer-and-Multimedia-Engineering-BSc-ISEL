from plan.plano import Plano


class PlanoPEE(Plano):

    def __init__(self, solucao):
        self.__solucao = solucao

    def obter_accao(self, estado):
        """
        Devolve o operador a aplicar face ao estado enviado - isto se a resolução ainda estiver
        sincronizada com o mundo (não tiverem havido alterações que possam tornar o plano inválido).
        Assim, só realiza o return se o estado do primeiro passo removido corresponder ao estado atual.

        Args:
            estado: estado atual

        Returns: operador/ação a realizar

        """
        if self.__solucao.dimensao > 1:

            if estado == self.__solucao[0].estado:

                operador = self.__solucao[1].operador
                self.__solucao.remover()
                return operador

    def mostrar(self, vista):
        vista.mostrar_solucao(self.__solucao)
