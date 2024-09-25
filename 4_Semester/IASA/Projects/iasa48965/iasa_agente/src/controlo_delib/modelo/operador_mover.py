import math

from mod.agente.estado_agente import EstadoAgente
from mod.operador import Operador
from sae.agente.accao import Accao


class OperadorMover(Operador):
    """
    Herdando de Operador, representa um movimento que o agente pode executar para ir de dada posição para outra (ambas
    representadas por estados). Ou seja, será inicializado com um certo ângulo de movimento. A aplicação de um vetor com esse
    ângulo e dada distância na posição que dado estado representa, resultará na posição do estado sucessor.
    """

    def __init__(self, modelo_mundo, direccao):
        self.__modelo_mundo = modelo_mundo
        self.__angulo = direccao.value
        self.__accao = Accao(direccao)

    def translacao(self, posicao, distancia, angulo):
        """
        Calcula a translacao da posicao atual para uma nova de acordo
        com os parametros recebidos
        Args:
            posicao: posicao atual
            distancia: distancia
            angulo: angulo

        Returns: Devolve a nova posicao apos os calculos

        """
        x, y = posicao
        dx = round(distancia * math.cos(angulo))
        dy = -round(distancia * math.sin(angulo))
        nova_posicao = (x + dx, y + dy)

        return nova_posicao

    def aplicar(self, estado):
        """
        Aplica o operador que a classe representa no estado recebido, retornando o estado sucessor se este
        existir.
        Utilizamos o metodo anterior para obter a nova posicao

        Args:
            estado: estado o qual vai ser operado

        Returns:

        """
        nova_posicao = self.translacao(estado.posicao, self.__accao.passo, self.__angulo)
        novo_estado = EstadoAgente(nova_posicao)

        if novo_estado in self.__modelo_mundo.obter_estados():
            return novo_estado

    def custo(self, estado, estado_suc):
        """
        Retorna o custo de aplicação do operador para obter dado estado sucessor de um outro estado, ambos enviados para a função.
        Este custo será proporcional à distância entre as 2 posições que os estados representam, não sendo nunca inferior a 1.

        Args:
            estado: estado a aplicar operador
            estado_suc: estado que dessa aplicação resultará

        Returns: custo da aplicação do operador

        """
        return max(1, math.dist(estado.posicao, estado_suc.posicao))

    @property
    def accao(self):
        return self.__accao

    @property
    def ang(self):
        return self.__angulo
