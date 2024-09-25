from ..resposta.resposta_mover import RespostaMover
from ecr.comportamento import Comportamento
from sae.ambiente.direccao import Direccao
import random


class Explorar(Comportamento):
    """comportamento que gera uma accao que corresponde a um movimento numa 
        direccao aleatoria
    """

    def activar(self, percepcao):
        """Escolhemos uma direccao random ao utilizar a funcao choice do random numa lista com as direccoes
        criamos uma resposta mover com essa direccao
        retornamos a activacao de essa resposta
        """
        direccao = list(Direccao)
        resposta = RespostaMover(random.choice(direccao))

        return resposta.activar(percepcao)
