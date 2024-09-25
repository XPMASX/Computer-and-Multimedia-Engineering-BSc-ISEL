from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from pee.melhor_prim.procura_aa import ProcuraAA
from pee.melhor_prim.procura_sofrega import ProcuraSofrega
from sequencia.mod_prob.problema_seq import ProblemaSeq
from sequencia.mod_prob.estado_seq import EstadoSeq
from sequencia.mod_prob.heur_seq import HeurSeq
from sequencia.planeador.planeador_seq import PlaneadorSeq

"""
Solução ProcuraCustoUnif:           Solução ProcuraAA:                  Solução ProcuraSofrega: 
[3, 6, 4, 2, 5, 1] None             [3, 6, 4, 2, 5, 1] None             [3, 6, 4, 2, 5, 1] None
[3, 6, 2, 4, 5, 1] Troca(2, 3)      [3, 6, 2, 4, 5, 1] Troca(2, 3)      [3, 6, 2, 4, 5, 1] Troca(2, 3)
[3, 2, 6, 4, 5, 1] Troca(1, 2)      [3, 2, 6, 4, 5, 1] Troca(1, 2)      [3, 2, 6, 4, 5, 1] Troca(1, 2)
[3, 2, 1, 4, 5, 6] Troca(2, 5)      [3, 2, 1, 4, 5, 6] Troca(2, 5)      [6, 2, 3, 4, 5, 1] Troca(0, 2)
[1, 2, 3, 4, 5, 6] Troca(0, 2)      [1, 2, 3, 4, 5, 6] Troca(0, 2)      [1, 2, 3, 4, 5, 6] Troca(0, 5)
Dimensão:  5                        Dimensão:  5                        Dimensão:  5
Custo:  7                           Custo:  7                           Custo:  9
Complexidade temporal:  699         Complexidade temporal:  30          Complexidade temporal:  5
Complexidade espacial:  720         Complexidade espacial:  237         Complexidade espacial:  54

Solução ProcuraSofrega: 
[3, 6, 4, 2, 5, 1] None
[3, 6, 2, 4, 5, 1] Troca(2, 3)
[3, 2, 6, 4, 5, 1] Troca(1, 2)
[6, 2, 3, 4, 5, 1] Troca(0, 2)
[1, 2, 3, 4, 5, 6] Troca(0, 5)
Dimensão:  5
Custo:  9
Complexidade temporal:  5
Complexidade espacial:  54

Conclusões: 
Apesar de ambas as procuras terem a mesma dimensão e o mesmo custo a ProcuraAA é claramente melhor pois observando ambas 
as complexidades (temporal e espacial) conseguimos ver que esta tem menores valores comparando com a ProcuraCustoUnif.

"""

SEQ_INICIAL = EstadoSeq([3, 6, 4, 2, 5, 1])
SEQ_FINAL = EstadoSeq([1, 2, 3, 4, 5, 6])

problema = PlaneadorSeq(SEQ_INICIAL, SEQ_FINAL)
solucao = problema.planear()

solucao_dimensao = solucao[-1].profundidade + 1
solucao_custo = solucao[-1].custo

print("Solução: ")

if solucao:
    while no := solucao.remover():
        print(no.estado, no.operador)


print("Dimensão: ", solucao_dimensao)
print("Custo: ", solucao_custo)
print("Complexidade temporal: ", problema.complexidade_temporal)
print("Complexidade espacial: ", problema.complexidade_espacial)


