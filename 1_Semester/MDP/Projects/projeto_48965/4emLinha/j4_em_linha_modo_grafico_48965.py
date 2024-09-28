from j4_em_linha_motor_48965 import novo_jogo
from j4_em_linha_motor_48965 import ha_espaco
from j4_em_linha_motor_48965 import jogar
from j4_em_linha_motor_48965 import valor
from j4_em_linha_motor_48965 import terminou
from j4_em_linha_motor_48965 import quem_ganhou
from j4_em_linha_motor_48965 import get_linha_vitoria
from j4_em_linha_motor_48965 import proximo_a_jogar
from j4_em_linha_agente_48965 import jogada_agente
import pygame
import sys
import math

mensagem_inicio = '''
ISEL - 4 em linha!

Nesta versão do 4 em linha joga-se contra o computador.
O primeiro jogador a jogar joga com 1's.
O segundo jogador a jogar joga com 2's.
As posições vazias são representadas por 0's.
'''

mensagem_escolher_ordem = '''Quer jogar contra um jogador ou contra o computador?
Insira um 1, seguido de return, para jogar contra um jogador
ou um 2, seguido de return, para jogar contra o computador.
'''

mensagem_erro_1_ou_2 = '''Não inseriu nem um 1 nem um 2, seguido de return.
Tente outra vez.
'''

mensagem_escolher_coluna = '''Em que coluna quer jogar?
Insira o número da coluna, seguido de return.
'''

mensagem_erro_coluna = '''Não é possível jogar nessa coluna.
Tente outra vez.
'''
#cores em valor rgb
VERMELHO = (255,0,0)
AMARELO = (255,255,0)
AZUL = (0,0,255)
PRETO = (0,0,0)

pygame.init()

#font do texto
font = pygame.font.SysFont("monospace", 60)

#dimensao da grelha em pixel
quadrado = 100
#raio das pecas 
raio = int(quadrado/2 - 5) 

#largura tamanho do quadrado vezes o numero de colunas(7)
largura = 7 *quadrado

#altura tamanho do quadrado vezes o numero de linhas(7) + 1 para a linha onde a peca vai sobrevoar
altura = (6+1) * quadrado

#tamanho da janela
tamanho = (largura, altura)

ecra = pygame.display.set_mode(tamanho)
pygame.display.set_caption("4emLinha - PEDRO SILVA A48965")

def desenha(jogo):
    grelha=jogo[0]
    for c in range(7):
        for l in range(6):
            #desenha um retangulo azul deixando uma barra preta em cima para a peca sobrevoar
            pygame.draw.rect(ecra, AZUL, (c*quadrado, l*quadrado+quadrado,quadrado,quadrado))
            #desenha os circulos pretos onde as pecas vao ser colocadas
            pygame.draw.circle(ecra,PRETO, (int(c*quadrado+quadrado/2), int(l*quadrado+quadrado+quadrado/2)),raio)
    
    for c in range(7):
        
        for l in range(6):
            #se a posicao na grelha for 1 quer dizer que foi o primeiro jogador a jogar ou seja vai ser colocado uma peca vermelha
            if grelha[l][c] == 1:
                    pygame.draw.circle(ecra, VERMELHO, (int(c*quadrado+quadrado/2), quadrado+int(l*quadrado+quadrado/2)), raio)
            elif grelha[l][c] == 2: 
                    pygame.draw.circle(ecra, AMARELO, (int(c*quadrado+quadrado/2), quadrado+int(l*quadrado+quadrado/2)), raio)
    pygame.display.update()

def print_jogo(jogo):

    for l in range(6):
        linha_str = ''
        for c in range(7):
            linha_str = linha_str + str(valor(jogo, l, c)) + ' '
        print(linha_str)

    print()

def print_jogo_vitoria(jogo, linha_vitoria):

    for l in range(6):
        linha_str = ''
        for c in range(7):
            if [l, c] in linha_vitoria:
                valor_string = '*'
            else:
                valor_string = str(valor(jogo, l, c))
            linha_str = linha_str + valor_string + ' '
        print(linha_str)

    print()

def input_1_ou_2():

    escolha_string = input(mensagem_escolher_ordem)

    while escolha_string != '1' and escolha_string != '2':
        
        print(mensagem_erro_1_ou_2)
        escolha_string = input(mensagem_escolher_ordem)

    escolha_int = int(escolha_string)
        
    return escolha_int

def jogada_jogador(jogo):

    escolha_string = input(mensagem_escolher_coluna)

    while (not (escolha_string in ['1', '2', '3', '4', '5', '6', '7'])) \
          or (not ha_espaco(jogo, int(escolha_string))):

        print_jogo(jogo)
        print(mensagem_erro_coluna)
        escolha_string = input(mensagem_escolher_coluna)

    escolha_int = int(escolha_string)

    return escolha_int
    
# início

print(mensagem_inicio)

#usar a funcao de input que existe no modo texto para o jogador decidir se joga contra o pc ou outro jogador
segundo_jogador=input_1_ou_2()

jogador = 1

jogo = novo_jogo()
desenha(jogo)
pygame.display.update()

while not terminou(jogo):
    
    
    #enquanto a janela nao for fechada o jogo corre
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        
        #a peca segue o cursor    
        if event.type == pygame.MOUSEMOTION:
            pygame.draw.rect(ecra, PRETO, (0,0, largura, quadrado))
            #posx e a posicao onde existe um clique do mouse
            posx = event.pos[0]
            if proximo_a_jogar(jogo) == jogador :
                pygame.draw.circle(ecra, VERMELHO, (posx, int(quadrado/2)), raio)
            else: 
                pygame.draw.circle(ecra, AMARELO, (posx, int(quadrado/2)), raio)
        
        pygame.display.update()     
        
        #se o mouse for premido uma jogada acontece ou se a opcao de computador foi selecionada e e a vez do computador jogar 
        if event.type == pygame.MOUSEBUTTONDOWN or (jogo[3]==2 and segundo_jogador==2):
            pygame.draw.rect(ecra, PRETO, (0,0, largura, quadrado))
            

            if proximo_a_jogar(jogo) == jogador:
                posx = event.pos[0]
                #math.ceil para arrendondar para cima
                coluna = int(math.ceil(posx/quadrado))
                

            else:
                if segundo_jogador == 1:
                    posx = event.pos[0]
                    coluna = int(math.ceil(posx/quadrado))
                    
                
                else:
                    coluna = jogada_agente(jogo, proximo_a_jogar(jogo))
                          
    
            jogo = jogar(jogo, coluna)
        
            desenha(jogo)

            print_jogo(jogo)

# fim
linha_vitoria = get_linha_vitoria(jogo)
if linha_vitoria != None:

    print_jogo_vitoria(jogo, linha_vitoria)

if quem_ganhou(jogo) == None:

    print('EMPATE')
    texto = font.render("EMPATE", 1, VERMELHO)
    ecra.blit(texto, (40,10))    

elif quem_ganhou(jogo) == jogador:

    print('GANHOU!!! Parabéns!!!')
    texto = font.render("JOGADOR 1 VENCE!!!", 1, VERMELHO)
    ecra.blit(texto, (40,10))    

else:

    print('PERDEU!!! tente outra vez...')
    if segundo_jogador==1:
        texto = font.render("JOGADOR 2 VENCE!!!", 1, AMARELO)
        ecra.blit(texto, (40,10)) 
    else:
        texto = font.render("COMPUTADOR VENCE!!!", 1, AMARELO)
        ecra.blit(texto, (40,10))        
        

desenha(jogo)
#faz com que a janela do jogo espere 3 segundos antes de fechar
pygame.time.wait(3000)
