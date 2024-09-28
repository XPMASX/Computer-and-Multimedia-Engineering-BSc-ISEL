# Importar o módulo pygame
# se a execução deste import em Python3 ou Python2 der algum erro
# é porque o pygame não está bem instalado
import pygame, sys
from pygame.locals import *
from math import cos, sin, sqrt


# inicialização do módulo pygame
pygame.init()

# criação de uma janela
largura = 532
altura  = 410
tamanho = (largura, altura)
janela  = pygame.display.set_mode(tamanho)
pygame.display.set_caption('Car Race aluno 48965') #nome da janela
#Nesta janela o ponto (0,0) é o canto superior esquerdo
#e (532-1,410-1) = (531,409) o canto inferior direito.


# número de imagens por segundo
frame_rate = 30

# relógio para controlo do frame rate
clock = pygame.time.Clock()

# ler uma imagem em formato bmp
pista = pygame.image.load("car-track.jpg")
carro = pygame.image.load("moto.png")
carro90 = pygame.transform.rotate(carro, 90)
carro135 = pygame.transform.rotate(carro, 135)
carro180 = pygame.transform.rotate(carro, 180)
carro270 = pygame.transform.rotate(carro, 270)
carro0 = pygame.image.load("moto.png")

velocidade=0.1
v=1


    
#Inicializa o tempo
t=0.0


#########################
#Para escrever o tempo:
font_size = 25
font = pygame.font.Font(None, font_size) # fonte pré-definida
antialias = True # suavização
WHITE = (255, 255, 255)# cor (terno com os valores Red, Green, Blue entre 0 e 255)


##################################
##Exemplo ajustado à pista

def parametrizacao (t):
    if t==0:
        resultado=(202,370)
    if 0<t<=0.5:
        resultado=(202+160*t,370) 
    if 0.5<t<=3:
        resultado=(270+30*cos(-2+t),290+80*sin(1+t))
    if 3<t<=4.3:
        resultado=(290.8-75*(t-3), 229.5)
    if 4.3<t<=6.2:
        resultado=(205+5*-cos(4.3-t),229.5+83*sin(4.3-t))
    if 6.2<t<=8:
        resultado=(206+95*(t-6.2), 145)
    if 8<t<=9:
        resultado=(370+5*cos(t-8),145-80*sin(8-t))
    if 9<t<=10.4:
        resultado=(372+50*(t-9), 212)
    if 10.4<t<=12:
        resultado=(430+46*cos(t-11),215+190*-sin(t-10.4))
    if 12<t<=14.4:
        resultado=(450-100*(t-12),30)
    if 14.4<t<=15.5:
        resultado=(190-90*(t-14.5),35+85*sin(t-14.4))    
    if 15.5<t<=16.8:
        resultado=(95,130+50*(t-15.5))   
    if 16.8<t<=18.5:
        resultado=(40+60*cos(t-16.8),205+30*sin(t-16.8))    
    if 18.5<t<=21.4:
        resultado=(25,230+50*(t-18.5))       
    if 21.4<t<=22.5:
        resultado=(38+150*(t-21.5),370) 
    if t>22.5:
        resultado=(0,0)   
    return resultado

#######################

#(A) Se descomentar aqui (e comentar B) vejo onde passou/ rasto da trajetória
# Pois neste caso só junta a pista uma vez,
#no outro caso está sempre a juntar/desenhar a pista
#janela.blit(pista, (0, 0)) 

#################################
#Ciclo principal do jogo
while True:
    tempo = font.render("T="+str(t), antialias, WHITE)
    info = font.render("R = reset", antialias, WHITE)
    vinfo = font.render("Teclas 1,2,3 = V",antialias,WHITE)
    janela.blit(pista, (0, 0))  
    janela.blit(carro, parametrizacao(t))
    speed = font.render("Velocidade="+str(v), antialias, WHITE)
    janela.blit(tempo, (10, 10))
    janela.blit(vinfo, (400, 245))
    janela.blit(speed, (400, 270))
    janela.blit(info, (400, 290))
    pygame.display.update()
    clock.tick(frame_rate)
    t=t+velocidade
    
    
    if(0<t<0.5):
        carro = carro270
    if(0.5<t<=3):
        carro=carro0
    if(3<t<=4.3):
        carro = carro90
    if(4.3<t<=6.2):
        carro = carro0
    if(6.2<t<=8):
        carro=carro270
    if(8<t<=9):
        carro = carro180
    if(9<t<=10.4):
        carro = carro270
    if(10.4<t<=12):
        carro=carro0
    if(12<t<=14.4):
        carro = carro90   
    if(14.4<t<=15.5):
        carro = carro135
    if(15.5<t<=16.8):
        carro=carro180
    if(16.8<t<=18.5):
        carro = carro90 
    if(18.5<t<=21.4):
        carro = carro180
    if(21.4<t<=22.5):
        carro=carro270    
    

    
    for event in pygame.event.get():
        #Para sair...
        if event.type == QUIT:
            pygame.quit()
            sys.exit()

        #Ao clicar em qualquer local, o tempo recomeça com t=0
        # evento mouse click botão esquerdo (código = 1)
        elif event.type== pygame.KEYDOWN and event.key == pygame.K_r:
            t = 0
                       

##        #Quando queremos saber as coordenadas de um ponto: 
##        # descomentar isto e comentar o "evento mouse click"...
##        #"clicar" nesse ponto... o python print as coordenadas.
##        # evento mouse click botão esquerdo (código = 1)
        elif event.type== pygame.MOUSEBUTTONUP and event.button == 3:
          (x, y) = event.pos
          localizacao="posicao=(" + str(x) + "," + str(y) + ")"
          print(localizacao)
          
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_1:
                velocidade=0.05
                v=0.5
            if event.key == pygame.K_2:
                velocidade=0.1
                v=1
            if event.key == pygame.K_3:
                velocidade=0.15  
                v=1.5


##FAQs:
##            (1)
##            Quando parametrização (ou velocidade) não está definida
##            para algum valor de t, dá o erro:
##                "local variable "result/resultado" referenced before assignment"
##            
            




