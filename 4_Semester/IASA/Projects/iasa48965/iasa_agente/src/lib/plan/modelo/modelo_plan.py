from abc import ABC, abstractmethod


class ModeloPlan(ABC):

    @abstractmethod
    def obter_estado(self):
        ""

    @abstractmethod
    def obter_estados(self):
        ""

    @abstractmethod
    def obter_operadores(self):
        ""