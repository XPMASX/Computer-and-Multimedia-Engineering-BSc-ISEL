from controlo_react.reaccoes.evitar.evitar_dir import EvitarDir
from ecr.hierarquia import Hierarquia
from ..evitar.resposta.resposta_evitar import RespostaEvitar
from sae import Direccao

"""
Esta classe é um comportamento composto do tipo Hierarquia em que chamamos o EvitarDir para todas as
direccoes possiveis sempre com a mesma RespostaEvitar criada no construtor.
Esta classe vai nos indicar qual a proxima direcao de acordo com os obstaculos
"""


class EvitarObst(Hierarquia):

    def __init__(self):
        self.__resposta = RespostaEvitar()

        super().__init__([
            EvitarDir(d, self.__resposta) for d in list(Direccao)
        ])
