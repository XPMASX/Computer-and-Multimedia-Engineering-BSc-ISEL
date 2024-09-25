import math

from controlo_delib.modelo.operador_mover import OperadorMover
from mod.agente.estado_agente import EstadoAgente
from plan.modelo.modelo_plan import ModeloPlan
from sae import Direccao


class ModeloMundo(ModeloPlan):
    """
    Representa o mundo por onde o agente navegará, e que será atualizado ao longo do tempo de execução.
    Permite fazer a simulação do futuro através da aplicação dos operadores do domínio do mundo, reconhecendo os possíveis desfechos.
    Terá um dicionário que, para cada posição do mundo, dita o elemento que nele reside, bem como listas com os operadores e estados
    no domínio do problema (com instâncias de EstadoAgente e OperadorMover) e uma instância de EstadoAgente que ditará o estado que representa
    a posição atual.
    """

    def __init__(self):
        self.__estado = None
        self.__estados = []
        self.__elementos = {}
        self.__operadores = [OperadorMover(self, direccao) for direccao in Direccao]
        self.__alterado = False

    def obter_estado(self):
        """

        Returns: estado do agente no modelo do mundo.

        """
        return self.__estado

    def obter_estados(self):
        """

        Returns: conjunto de estados no domínio do mundo.

        """
        return self.__estados

    def obter_operadores(self):
        """

        Returns: conjunto de operadores no domínio do mundo.

        """
        return self.__operadores

    def obter_elemento(self, estado):
        """
        Retorna o elemento do dicionário de elementos (posição:elemento) na chave de posição do método enviado para a função.
        Ou seja, retorna o elemento que reside na posição do estado enviado.

        Args:
            estado: estado a verificar

        Returns: elemento na posição que o estado representa

        """
        return self.__elementos.get(estado.posicao)

    def distancia(self, estado):
        """

        Args:
            estado: estado a verificar

        Returns: Devolve a distancia entre o estado recebido e o estado atual

        """
        return math.dist(estado.posicao, self.__estado.posicao)

    def actualizar(self, percepcao):
        """
        1- Atualiza o estado atual (estado do agente no mundo) com base na posição percecionada;
        2- Verifica se os elementos percecionados são iguais aos guardados
        3- Se não, atualiza os elementos e os estados criando um EstadoAgente
        com cada posição percecionada. Atualiza a flag alterado para true
        4- Caso não seja necessário alterar nada, garante-se que o atributo alterado retorna ao valor de False

        Args:
            percepcao: perceção do mundo por onde o agente navega

        """
        self.__estado = EstadoAgente(percepcao.posicao)

        if percepcao.elementos != self.__elementos:
            self.__elementos = percepcao.elementos

            self.__estados = [EstadoAgente(posicao) for posicao in percepcao.posicoes]
            self.__alterado = True

        else:
            self.__alterado = False

    def mostrar(self, vista):
        """
        Método que permite a visualização dos alvos e obstáculos bem como da posição atual do agente.
        """
        vista.mostrar_alvos_obst(self.__elementos)
        vista.marcar_posicao(self.__estado.posicao)

    @property
    def alterado(self):
        return self.__alterado
