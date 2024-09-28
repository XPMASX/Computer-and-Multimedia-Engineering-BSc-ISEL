
from j4_em_linha_motor_48965 import novo_jogo
from j4_em_linha_motor_48965 import ha_espaco
from j4_em_linha_motor_48965 import jogar
from j4_em_linha_motor_48965 import valor
from j4_em_linha_motor_48965 import terminou
from j4_em_linha_motor_48965 import quem_ganhou
from j4_em_linha_motor_48965 import get_linha_vitoria
from j4_em_linha_motor_48965 import proximo_a_jogar
from j4_em_linha_agente_48965 import jogada_agente


mensagem_inicio = '''
ISEL - 4 em linha!

Nesta versão do 4 em linha joga-se contra o computador.
O primeiro jogador a jogar joga com 1's.
O segundo jogador a jogar joga com 2's.
As posições vazias são representadas por 0's.
'''

mensagem_escolher_ordem = '''Quer ser o primeiro ou o segundo a jogar?
Insira um 1, seguido de return, para ser o primeiro
ou um 2, seguido de return, para ser o segundo.
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

jogador = input_1_ou_2()

jogo = novo_jogo()

while not terminou(jogo):

    print_jogo(jogo)
    
    if proximo_a_jogar(jogo) == jogador:

        coluna = jogada_jogador(jogo)

    else:

        coluna = jogada_agente(jogo, proximo_a_jogar(jogo))
    
    jogo = jogar(jogo, coluna)
        
print_jogo(jogo)

# fim

linha_vitoria = get_linha_vitoria(jogo)
if linha_vitoria != None:

    print_jogo_vitoria(jogo, linha_vitoria)

if quem_ganhou(jogo) == None:

    print('EMPATE')

elif quem_ganhou(jogo) == jogador:

    print('GANHOU!!! Parabéns!!!')

else:

    print('PERDEU!!! tente outra vez...')
