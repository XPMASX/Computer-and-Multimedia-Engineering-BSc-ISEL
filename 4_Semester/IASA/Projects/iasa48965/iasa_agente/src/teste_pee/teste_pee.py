from pee.larg.procura_largura import ProcuraLargura
from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from pee.prof.procura_prof_iter import ProcuraProfIter
from pee.prof.procura_prof_lim import ProcuraProfLim
from mod_prob.problema_contagem import ProblemaContagem

VALOR_INICIAL = 0
VALOR_FINAL = 15
INCREMENTOS = [1, 2, -1]

problema = ProblemaContagem(VALOR_INICIAL, VALOR_FINAL, INCREMENTOS)

#mec_proc = ProcuraProfLim()
mec_proc = ProcuraProfIter()
#mec_proc = ProcuraLargura()
#mec_proc = ProcuraCustoUnif()
solucao = mec_proc.procurar(problema)

print("Solucao: ", [no.estado.valor for no in solucao])

print("Dimensão: ", solucao[-1].profundidade + 1)
print("Custo: ", solucao[-1].custo)

"""
ProcuraProfLim:
Solucao:  [0, -1, 1, 3, 2, 4, 6, 5, 7, 9, 8, 10, 12, 11, 13, 15]
Dimensão:  16
Custo:  45

ProcuraProfIter: 
Solucao:  [0, 2, 4, 6, 8, 10, 12, 14, 16]
Dimensão:  9
Custo:  32

ProcuraLargura:
Solucao:  [0, 1, 3, 5, 7, 9, 11, 13, 15]
Dimensão:  9
Custo:  29

ProcuraCustoUnif:
Solucao:  [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Dimensão:  16
Custo:  15
"""
