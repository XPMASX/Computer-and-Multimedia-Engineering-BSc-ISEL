from controlo_delib.controlo_delib import ControloDelib
from plan.plan_pee.planeador_pee import PlaneadorPEE
from plan.plan_pee.tipo_procura import TipoProcura
from sae import Simulador

"""
TipoProcura.CUSTO_UNIFORME
Complexidade temporal:  1419
Complexidade espacial:  611

TipoProcura.SOFREGA
Complexidade temporal:  1186
Complexidade espacial:  562

TipoProcura.AA
Complexidade temporal:  885
Complexidade espacial:  438

Conclusões: 
Os resultados são os esperados com a procura menos eficiente a ser a Procura Custo Uniforme 
e a mais eficiente a Procura A*, sendo a Procura Sofrega um intermédio.

A procura custo uniforme é um algoritmo de busca em que o custo total do 
caminho percorrido até o momento é levado em consideração, ou seja, isto faz com que o algoritmo
precise de visitar muitos nós antes de encontrar a solução ótima, o que não é bom tendo em conta o tamanho desta simulação

A procura Sofrega é uma estratégia que explora o espaço de busca 
indo o mais longe possível em cada ramificação antes de retroceder.
Esta não é a melhor procura para esta simulação devido ao facto de esta funcionar como um labirinto,ou seja,
existem vários longos caminhos que não vão dar em nada o que faz com que grande quantidade de nós sejam explorados desnecessariamente.

A procura A* combina a procura custo uniforme e uma heuristica que estima o custo do caminho restante até a solução.
Logo devido à heuristica não é necessário explorar todos os caminhos ao contrario da procura custo uniforme.
"""

tipoProcura = TipoProcura.AA
problema = PlaneadorPEE(tipoProcura)

controlo = ControloDelib(problema)
Simulador(4, controlo).executar()
print(tipoProcura)
print("Complexidade temporal: ", problema.complexidade_temporal)
print("Complexidade espacial: ", problema.complexidade_espacial)
