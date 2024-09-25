from abc import abstractmethod
from ecr.comportamento import Comportamento


class ComportComp(Comportamento):
    """Comportamento composto por mais do que um comportamento
    """

    def __init__(self, comportamentos):
        """Construtor recebe um alista de comportamentos

        Args:
            comportamentos (Comportamento[]): Lista de Comportamentos
        """
        self.__comportamentos = comportamentos

    def activar(self, percepcao):
        
        accoes = []

        for comportamento in self.__comportamentos:

            accao = comportamento.activar(percepcao)

            if accao is not None:
                accoes.append(accao)

        if len(accoes) > 0:
            return self.seleccionar_accao(accoes)

        """Ativa um dado comportamento
        ativar todos os comportamentos e guardar na lista de accoes
        """

    @abstractmethod
    def seleccionar_accao(self, accoes):

        """Seleciona uma accao de uma lista de accoes recebida
            os primeiros elem da lista tem mais prioridade
        """
