from ecr.prioridade import Prioridade
from controlo_react.reaccoes.aproximar.aproximar_dir import AproximarDir
from sae.ambiente.direccao import Direccao

"""
Esta classe é um comportamento composto do tipo prioridade em que o seu conjunto de comportamentos é
uma lista com uma reaccao que responde a um estimulo para cada direccao possivel

"""


class AproximarAlvo(Prioridade):

    def __init__(self):

        super().__init__([AproximarDir(Direccao.NORTE), AproximarDir(Direccao.SUL),
                          AproximarDir(Direccao.ESTE), AproximarDir(Direccao.OESTE)])
