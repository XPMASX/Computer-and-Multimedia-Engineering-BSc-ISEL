from lib.ecr.hierarquia import Hierarquia
from controlo_react.reaccoes.aproximar.aproximar_alvo import AproximarAlvo
from controlo_react.reaccoes.evitar.evitar_obst import EvitarObst
from controlo_react.reaccoes.explorar.explorar import Explorar

"""Classe que cria um comportamento composto do tipo Hierarquia Os comportamentos têm a segunite importância: 
AproximarAlvo, EvitarObst, Explorar Esta ordem deve-se ao objetivo ser apanhar todos os alvos sem atingir nenhuma 
parede explorando quando nenhuma dos dois casos se verifica"""


class Recolher(Hierarquia):
    def __init__(self):
        super().__init__([
            AproximarAlvo(),
            EvitarObst(),
            Explorar()]
        )
