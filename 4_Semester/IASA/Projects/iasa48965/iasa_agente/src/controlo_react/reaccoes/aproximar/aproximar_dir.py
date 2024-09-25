from ecr.reaccao import Reaccao
from ..resposta.resposta_mover import RespostaMover
from ..aproximar.estimulo.estimulo_alvo import EstimuloAlvo
from sae import Direccao

"""reaccao que responde a um estimulo numa direccao especifica
Recebemos uma das direccoes possiveis e com isso usamos o construtor da
função pai para passar um estimulo e uma resposta a partir do EstimuloAlvo e da RespostaMover respetivamente
Desta forma conseguimos obter a reação pretendida (que é um comportamento)"""


class AproximarDir(Reaccao):

    def __init__(self, direccao: Direccao):
        super().__init__(EstimuloAlvo(direccao), RespostaMover(direccao))
