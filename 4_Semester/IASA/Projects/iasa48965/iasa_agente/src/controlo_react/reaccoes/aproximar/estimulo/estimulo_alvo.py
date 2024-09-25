from ecr.estimulo import Estimulo
from sae.agente.percepcao import Percepcao
from sae import Elemento
from sae import Direccao

"""
Cada Sub-comportamento vai ter um diferente estimulo 
tal como vai ter uma diferente acao. 
"""


class EstimuloAlvo(Estimulo):

    def __init__(self, direccao: Direccao, gama=0.9):
        self.__direccao = direccao
        self.__gama = gama

    def detectar(self, percepcao: Percepcao):
        """
        Funcao que deteta a posicao de um dos alvos no mapa e que
        devolve a intensidade desse estimulo de acordo com a distância
        entre a nossa posicao e a do alvo

        """
        elemento, distancia, _ = percepcao.per_dir[self.__direccao]
        """
        Uma distancia mais proxima da uma intensidade maior. Se for 0 é maxima.
        """
        return self.__gama ** distancia if elemento == Elemento.ALVO else 0
