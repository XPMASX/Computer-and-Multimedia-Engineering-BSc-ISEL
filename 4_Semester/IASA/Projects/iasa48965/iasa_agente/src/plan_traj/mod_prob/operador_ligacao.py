from mod.operador import Operador
from plan_traj.mod_prob.estado_localidade import EstadoLocalidade


class OperadorLigacao(Operador):

    def __init__(self, origem, destino, custo):
        """
        Guardamos os parametros

        Args:
            origem: origem
            destino: destino
            custo: custo
        """
        self.__custo = custo
        self.__estado_origem = EstadoLocalidade(origem)
        self.__estado_destino = EstadoLocalidade(destino)

    def aplicar(self, estado):
        """
        Se o estado passado corresponder ao estado origem aplica o operador, ou seja, devolve estado destino
        Args:
            estado: estado atual

        Returns: estado destino

        """
        if estado == self.__estado_origem:
            return self.__estado_destino

    def custo(self, estado, estado_suc):
        return self.__custo

    def __repr__(self):
        """
        Representa em string

        Returns: Viagens em que cada linha é um no

        """
        return "%s -> %s" % (self.__estado_origem.localidade,
                             self.__estado_destino.localidade)
