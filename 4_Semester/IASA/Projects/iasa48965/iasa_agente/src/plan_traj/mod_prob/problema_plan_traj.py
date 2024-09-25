from mod.problema.problema import Problema
from plan_traj.mod_prob.estado_localidade import EstadoLocalidade
from plan_traj.mod_prob.operador_ligacao import OperadorLigacao


class ProblemaPlanTraj(Problema):

    def __init__(self, ligacoes, loc_inicial, loc_final):
        super().__init__(EstadoLocalidade(loc_inicial),
                         [OperadorLigacao(ligacao.origem, ligacao.destino, ligacao.custo) for ligacao in ligacoes])
        self.__estado_final = EstadoLocalidade(loc_final)

    def objetivo(self, estado):
        """
        Retorna se o estado recebido é o estado final do problema

        Args:
            estado: estado a analisar

        Returns: Retorna se o estado recebido é o estado final do problema

        """
        return estado == self.__estado_final
