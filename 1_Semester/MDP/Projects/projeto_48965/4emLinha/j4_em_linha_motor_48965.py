from random import random
from random import choice
import pygame


def novo_jogo():

    grelha = [
        [0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0],
    ]
    fim = False
    vencedor = None
    jogador = 1
    linha_vitoria = [
        
        ]
    
    
    jogo = (grelha, fim, vencedor, jogador, linha_vitoria)
    
    return jogo

#verifica se existe um espaco livre se nao existir coluna retorna false 
def ha_espaco(jogo,coluna):
    
    grelha=jogo[0]
    if coluna==None:
        return False
    coluna-=1
    
    
    return grelha[0][coluna] == 0

def jogar(jogo,coluna):
    grelha  = jogo[0]
    fim     = jogo[1]
    vencedor = jogo[2]
    jogador  = jogo[3]
    linha_vitoria = jogo[4]
    
    peca=proximo_a_jogar(jogo)
    if ha_espaco(jogo,coluna):
        #faz coluna -1 pois as listas comecam no 0 mas o utilizador vai introduzir numeros a partir do 1 entao coluna -1
        coluna-=1
        line=linha(jogo,coluna)
        #coloca a peca na grelha
        grelha[line][coluna] = peca 
        #troca de jogador
        if(jogador==1):
            jogador=2
        else:
            jogador=1         
    jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
        
    return jogo
    
def valor(jogo,linha,coluna):
    grelha=jogo[0]
    peca=grelha[linha][coluna]
    
    return peca

def terminou(jogo):
    jogo=fim(jogo)
    jogo=vitoria(jogo)
    
    return jogo[1]

def quem_ganhou(jogo):
    jogo=vitoria(jogo)
    
    return jogo[2]

def get_linha_vitoria(jogo):
    jogo=vitoria(jogo)
    
    return jogo[4]

def proximo_a_jogar(jogo):    
       
    return jogo[3]

def linha(jogo,coluna):
    grelha=jogo[0]
    #vai de 5 a -1 com um step de -1 pois existem 6 linhas mas o python comeca a contar a partir do 0 logo tem que ser 5 e comeca pela ultima linha(5) pois nos queremos que ele indique a linha com disponibilidade mais abaixo na grelha
    for l in range (5,-1,-1):
        #quando vir que esse espaco na coluna indica esta vazia ou seja tem um 0 retorna a linha 'l'
        if grelha[l][coluna]==0:
            return l    

def fim(jogo):
    grelha  = jogo[0]
    fim     = jogo[1]
    vencedor = jogo[2]
    jogador  = jogo[3]
    linha_vitoria = jogo[4]    
    
    #retorna true se todas as colunas tiverem cheias ao vendo se os numeros que estao na primeira linha sao todos diferente de 0 indicando que estao cheias originando um empate
    if (grelha[0][0] != 0 and grelha[0][1] != 0 and grelha[0][2] != 0 and
    grelha[0][3] != 0 and grelha[0][4] != 0 and grelha[0][5] != 0 and 
    grelha[0][6] != 0 ):
        fim = True
    
    jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
    return jogo

def vitoria(jogo):
    grelha  = jogo[0]
    fim     = jogo[1]
    vencedor = jogo[2]
    jogador  = jogo[3]
    linha_vitoria = jogo[4]
    if(jogador==1):
        peca=2
    else:
        peca=1     
    #horizontal
    for c in range(7-3):
        for l in range(6):
            if grelha[l][c] == peca and grelha[l][c+1] == peca and grelha[l][c+2] == peca and grelha[l][c+3] == peca:
                vencedor=peca
                linha_vitoria = [
                    [l, c],
                    [l, c+1],
                    [l, c+2],
                    [l, c+3]
                ]                
                fim=True
                jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
                return jogo
    #vertical
    for c in range(7):
        for l in range(6-3):
            if grelha[l][c] == peca and grelha[l+1][c] == peca and grelha[l+2][c] == peca and grelha[l+3][c] == peca:
                vencedor=peca
                linha_vitoria = [
                    [l, c],
                    [l+1, c],
                    [l+2, c],
                    [l+3, c]
                ]
                fim=True
                jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
                return jogo  
     
    #diagonais positivas       
    for c in range(7-3):
        for l in range(6-3):
            if grelha[l][c] == peca and grelha[l+1][c+1] == peca and grelha[l+2][c+2] == peca and grelha[l+3][c+3] == peca:
                vencedor=peca
                linha_vitoria = [
                     [l, c],
                     [l+1, c+1],
                     [l+2, c+2],
                     [l+3, c+3]
                ]
                fim=True
                jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
                return jogo     
            
    #diagonais negativas       
    for c in range(7-3):
        for l in range(6):
            if grelha[l][c] == peca and grelha[l-1][c+1] == peca and grelha[l-2][c+2] == peca and grelha[l-3][c+3] == peca:
                
                vencedor=peca
                linha_vitoria = [
                      [l, c],
                      [l-1, c+1],
                      [l-2, c+2],
                      [l-3, c+3]
                ]
                fim=True
                jogo=(grelha,fim,vencedor,jogador,linha_vitoria)
                return jogo      

    return jogo
        
