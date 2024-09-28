from random import random
from random import choice
import pygame

def get_posicoes_vazias(grelha):
    
    posicoes_vazias = []
    
    for indice_linha in range(4):
        for indice_coluna in range(4):
            if grelha[indice_linha][indice_coluna] == 0:
                posicao_vazia = (indice_linha, indice_coluna)
                posicoes_vazias.append(posicao_vazia)
                
    return posicoes_vazias

def get_posicao_vazia(grelha):
    
    posicoes_vazias = get_posicoes_vazias(grelha)
    posicao_vazia= choice(posicoes_vazias)
    return posicao_vazia
    
    

def get_2ou4():
    dois_ou_quatro = None
    x = random()
    if x > 0.1:
        dois_ou_quatro = 2
    else:
        dois_ou_quatro = 4
    return dois_ou_quatro


def inserir_2ou4(grelha):
    
    dois_ou_quatro = get_2ou4()
    posicao_vazia = get_posicao_vazia(grelha)
    indice_linha = posicao_vazia[0]
    indice_coluna = posicao_vazia[1]
    grelha[indice_linha][indice_coluna] = dois_ou_quatro
    


def novo_jogo():

    grelha = [
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0],
        [0, 0, 0, 0]
    ]
    fim = False
    vitoria = False
    pontos = 0
    
    inserir_2ou4(grelha)
    inserir_2ou4(grelha)
    
    jogo = (grelha,fim,vitoria,pontos)
    
    return jogo


    
def mover_esquerda(uma_lista):
    resultado=[]
    
    for indice in range(len(uma_lista)):
        if uma_lista[indice] != 0:
            resultado.append(uma_lista[indice])
            
    while len(resultado) != len(uma_lista):
        resultado.append(0)
    
    return resultado

def somar_esquerda(uma_lista):
    resultado = []
    
    indice = 0
    pontos = 0
    
    while indice < len(uma_lista)-1:
        if uma_lista[indice] == uma_lista[indice+1]:
            soma = uma_lista[indice] + uma_lista[indice+1]
            resultado.append(soma)        
            indice = indice + 2
            pontos = pontos + soma
        else:
            resultado.append(uma_lista[indice])
            indice = indice + 1
            
    if indice == len(uma_lista)-1:
        resultado.append(uma_lista[indice])
    
    while len(resultado) != len (uma_lista):
        resultado.append (0)
        
    return (resultado,pontos)
    
def atualizar_grelha(grelha_inicial, grelha):
    
    inserir = False
    for il in range(len(grelha)):
        for ic in range(len(grelha[il])):
            if grelha_inicial[il][ic] != grelha[il][ic]:
                inserir = True
              
    if inserir == True:
        inserir_2ou4(grelha)
            
def get_vitoria(grelha):
    
    vitoria = False
    for il in range(len(grelha)):
        for ic in range(len(grelha[il])):   
            if grelha[il][ic] == 2048:
                vitoria = True
    return vitoria

def ha_iguais_adjacentes(grelha):
    
    ha = False
    for il in range(len(grelha)):
        for ic in range(len(grelha[il]) - 1): 
            if (grelha[il][ic] !=0) and (grelha[il][ic] == grelha[il][ic + 1]):
                ha = True            
            
    for il in range(len(grelha)-1):
        for ic in range(len(grelha[il])): 
            if (grelha[il][ic] !=0) and (grelha[il][ic] == grelha[il + 1][ic]):
                ha = True     
    
    return ha
            

def get_fim(grelha):
    
    fim = False
    
    posicoes_vazias = get_posicoes_vazias(grelha)
    if (len(posicoes_vazias)) == 0 and (not ha_iguais_adjacentes(grelha)):
        fim = True
    
    return fim

def trocar_linhas_com_colunas(grelha):
    linhas = len(grelha)
    colunas = len(grelha[0])

    grelha_t = []
    for j in range(colunas):
        linha = []
        for i in range(linhas):
            linha.append(grelha[i][j])
        grelha_t.append(linha)

    return grelha_t

def reverte_linhas(grelha):
    for i in range(4):
        inicio = 0
        fim = 4-1
        
        while (inicio < fim):
            grelha[i][inicio],grelha[i][fim] = grelha[i][fim],grelha[i][inicio]
            
            inicio += 1
            fim -= 1
    return grelha
    
    

def esquerda(jogo):
    grelha  = jogo[0]
    fim     = jogo[1]
    vitoria = jogo[2]
    pontos  = jogo[3]
    
    grelha_atualizada = []
    for linha in grelha:
        linha1 = mover_esquerda(linha)
        (linha2, pontos_a_somar) = somar_esquerda(linha1)
        grelha_atualizada.append(linha2)
        pontos = pontos + pontos_a_somar
        
    atualizar_grelha(grelha, grelha_atualizada)
    
    if vitoria == False:
        vitoria = get_vitoria(grelha_atualizada)
    
    fim = get_fim(grelha_atualizada)
    
    jogo_atualizado = (grelha_atualizada, fim, vitoria, pontos)
    
    
    return jogo_atualizado

def direita(jogo):
    
    (grelha, fim, vitoria, pontos) = jogo
    grelha_revertida = reverte_linhas(grelha)
    jogo_revertido = (grelha_revertida, fim, vitoria, pontos)
    jogo_revertido_atualizado = esquerda(jogo_revertido)
    (grelha, fim, vitoria, pontos) = jogo_revertido_atualizado
    grelha_revertida = reverte_linhas(grelha)
    jogo_atualizado = (grelha_revertida, fim, vitoria, pontos)
    
    return jogo_atualizado    
    
def acima(jogo):
    (grelha, fim, vitoria, pontos) = jogo
    grelha_transposta = trocar_linhas_com_colunas(grelha)
    jogo_transposto = (grelha_transposta, fim, vitoria, pontos)
    jogo_transposto_atualizado = esquerda(jogo_transposto)
    (grelha, fim, vitoria, pontos) = jogo_transposto_atualizado
    grelha_transposta = trocar_linhas_com_colunas(grelha)
    jogo_atualizado = (grelha_transposta, fim, vitoria, pontos)
    
    return jogo_atualizado    
    
def abaixo(jogo):
    
    (grelha, fim, vitoria, pontos) = jogo
    grelha_transposta = trocar_linhas_com_colunas(grelha)
    jogo_transposto = (grelha_transposta, fim, vitoria, pontos)
    jogo_transposto_atualizado = direita(jogo_transposto)
    (grelha, fim, vitoria, pontos) = jogo_transposto_atualizado
    grelha_transposta = trocar_linhas_com_colunas(grelha)
    jogo_atualizado = (grelha_transposta, fim, vitoria, pontos)
    
    return jogo_atualizado
    
def valor(jogo, linha, coluna):
    
    grelha = jogo[0]
    
    v = grelha[linha - 1][coluna - 1]
    
    return v
    
def terminou(jogo):
   
    return jogo[1]
    
def ganhou_ou_perdeu(jogo):
    
    return jogo[2]
    
def pontuacao(jogo):
    
    return jogo[3]
    
