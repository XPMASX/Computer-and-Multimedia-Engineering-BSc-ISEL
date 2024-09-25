from plan_traj.planeador.ligacao import Ligacao
from plan_traj.planeador.planeador_trajecto import PlaneadorTrajecto

LIGACOES = [Ligacao("Loc-0", "Loc-1", 5),
            Ligacao("Loc-0", "Loc-2", 25),
            Ligacao("Loc-1", "Loc-3", 12),
            Ligacao("Loc-1", "Loc-6", 5),
            Ligacao("Loc-2", "Loc-4", 30),
            Ligacao("Loc-3", "Loc-2", 10),
            Ligacao("Loc-3", "Loc-5", 5),
            Ligacao("Loc-4", "Loc-3", 2),
            Ligacao("Loc-5", "Loc-6", 8),
            Ligacao("Loc-5", "Loc-4", 10),
            Ligacao("Loc-6", "Loc-3", 15)]

planeador = PlaneadorTrajecto()

LOC_INICIAL = "Loc-0"
LOC_FINAL = "Loc-4"

solucao = planeador.planear(LIGACOES, LOC_INICIAL, LOC_FINAL)

if solucao:
    while no := solucao.remover():
        print(no.estado, ":", no.operador, " Custo : ", no.custo)
else:
    print("Não existe solução")
