import random
import pygame
from j4_em_linha_motor_48965 import ha_espaco


#importar a funcao ha_espaco e utilizar random.choice para escolher uma coluna sendo a escolha so efetuada quando ha espaco
def jogada_agente(jogo,jogador):
    opcoes=[1,2,3,4,5,6,7]
    coluna=random.choice(opcoes)

    if ha_espaco(jogo,coluna):
        return coluna
    
    