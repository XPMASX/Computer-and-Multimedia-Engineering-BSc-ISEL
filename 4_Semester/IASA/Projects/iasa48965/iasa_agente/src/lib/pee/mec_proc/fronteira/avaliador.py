from abc import ABC, abstractmethod


class Avaliador(ABC):
    """
    Define o contrato funcional (interface) de avaliação da prioridade de um nó para
    ordenação da fronteira por prioridade, é concretizado de acordo o tipo de procura
    """

    @abstractmethod
    def prioridade(self, no):
        """
        Método abstrato
        """

