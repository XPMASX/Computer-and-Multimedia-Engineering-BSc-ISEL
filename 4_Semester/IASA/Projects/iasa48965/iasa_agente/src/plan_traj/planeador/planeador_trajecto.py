from pee.melhor_prim.procura_custo_unif import ProcuraCustoUnif
from plan_traj.mod_prob.problema_plan_traj import ProblemaPlanTraj
from pee.prof.procura_profundidade import ProcuraProfundidade

class PlaneadorTrajecto:

    def planear(self, ligacoes, loc_inicial, loc_final):
        """
        instanciamos o problema
        instaciamos o  mecanismo procura
        procuramos o problema para ProcuraCustoUnif()

        Args:
            ligacoes: Lista de ligacoes possiveis
            loc_inicial: ponto de partida
            loc_final: destino

        Returns: devolve a solucao

        """

        problema = ProblemaPlanTraj(ligacoes, loc_inicial, loc_final)
        mec_proc = ProcuraCustoUnif()
        solucao = mec_proc.procurar(problema)

        return solucao
