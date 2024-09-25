from abc import ABC, abstractmethod


class Planeador(ABC):

    @abstractmethod
    def planear(self, modelo_plan, objetivos):
        ""