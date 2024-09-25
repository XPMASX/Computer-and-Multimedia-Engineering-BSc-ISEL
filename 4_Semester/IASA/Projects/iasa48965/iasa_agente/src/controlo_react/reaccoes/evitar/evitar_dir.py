from controlo_react.reaccoes.evitar.resposta.resposta_evitar import RespostaEvitar
from ecr.reaccao import Reaccao
from ..evitar.estimulo.estimulo_obst import EstimuloObst

"""reaccao que responde a um estimulo numa direccao e resposta especifica 
Recebemos uma das direccoes possiveis e com isso usamos o construtor da
função pai para passar um estimulo e uma resposta a partir do EstimuloObst e da RespostaEvitar recebida
Desta forma conseguimos obter a reação pretendida (que é um comportamento)"""


class EvitarDir(Reaccao):

    def __init__(self, direccao, resposta: RespostaEvitar):
        super().__init__(EstimuloObst(direccao), resposta)
