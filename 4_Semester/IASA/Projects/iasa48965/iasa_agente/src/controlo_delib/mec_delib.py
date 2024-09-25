import math

from sae import Elemento


class MecDelib:

    def __init__(self, modelo_mundo):
        self.__modelo_mundo = modelo_mundo

    def deliberar(self):
        """
        Deliberar corresponde a atualizar o conjunto de objetivos
        organizando por distância.

        Returns: Devolve os objetivos

        """
        objetivos = [estado for estado in self.__modelo_mundo.obter_estados()
                     if self.__modelo_mundo.obter_elemento(estado) == Elemento.ALVO]

        if objetivos:
            objetivos.sort(key=self.__modelo_mundo.distancia)
            return objetivos
