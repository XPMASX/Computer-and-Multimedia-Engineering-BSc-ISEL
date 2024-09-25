from sae import Accao
from ecr.resposta import Resposta


class RespostaMover(Resposta):

    def __init__(self, direccao):
        """geramos uma data class em que o unico parametro obrigatorio é a direccao
        ao injetar a super classe
        """
        super().__init__(Accao(direccao))
