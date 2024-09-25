from abc import abstractmethod, ABC, abstractproperty

from pee.mec_proc.no import No
from pee.mec_proc.solucao import Solucao


class MecanismoProcura(ABC):
    """
    Permite procurar uma solução para um problema quer este seja uma procura em profundidade
    ou em largura
    Utiliza um fronteira de exploração para memorizar e gerir nós explorados
    """

    def __init__(self, fronteira):
        self._fronteira = fronteira
        self.__num_total_nos_proc = 0
        self.__num_max_nos_memoria = 0

    def _iniciar_memoria(self):
        """
        iniciar a fronteira
        """
        self._fronteira.iniciar()

    @abstractmethod
    def _memorizar(self, no):
        """
        Memoriza um nó de acordo com o tipo de procura
        """

    def procurar(self, problema):
        """
        1- inicializamos a memoria
        2- criamos um no inicial
        3- inserimos a fronteira
        4- enquanto a fronteira não for vazia remover no da fronteira
        5- se o objetivo do problema for este no retornamos a solucao
        6- se nao percorremos os nos sucessores e memorizamos-os
        """

        self._iniciar_memoria()
        no_inicial = No(problema.estado_inicial)
        self._fronteira.inserir(no_inicial)
        self.__num_total_nos_proc += 1

        while not self._fronteira.vazia:

            no = self._fronteira.remover()

            self.__num_max_nos_memoria += 1

            if problema.objetivo(no.estado):
                return Solucao(no)

            else:
                for no_suc in self._expandir(problema, no):
                    self._memorizar(no_suc)

    def _expandir(self, problema, no):
        """
        expandir o no
        1- para cada operador no problema aplicamos o estado do no recebido e guardamos numa variavel
        2- se esse estado exisitr libertamos o nó com esse estado
        """

        for operador in problema.operadores:
            estado_suc = operador.aplicar(no.estado)
            if estado_suc:
                yield No(estado_suc, operador, no)

    @property
    def num_total_nos_proc(self):
        """

        Returns: Numero total de nos porcessados

        """
        return self.__num_total_nos_proc

    @property
    def complexidade_temporal(self):
        """

        Returns: Numero maximo de nos em memória

        """
        return self.__num_max_nos_memoria

    @abstractmethod
    def complexidade_espacial(self):
        ""
