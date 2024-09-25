from plan.plano import Plano


class PlanoPDM(Plano):

    def __init__(self, utilidade, politica):
        self.__utilidade = utilidade
        self.__politica = politica

    def obter_accao(self, estado):
        """
        Returns: Devolvemos a accao na politica com a chave estado
        """
        return self.__politica.get(estado)

    def mostrar(self, vista):
        """
        Se existir utilidade e politica mostra-as
        """
        if self.__utilidade and self.__politica:
            vista.mostrar_valor(self.__utilidade)
            vista.mostrar_politica(self.__politica)
