from abc import abstractmethod, ABC


class Problema(ABC):
    """
    Composto pelo estado inicial, operadores e objetivos
    Os operadores representam acções que produzem a mudança (transformação) de estado.
    Operam sobre as representações internas de estado, produzindo transições
    de estado que correspondem à geração de novos estados.
    elementos {read only} – Restrição que indica acesso a atributo apenas para leitura (propriedade de leitura)
    Para indicar isso criamos "getters" utilizando o atributo property do python
    """

    def __init__(self, estado_inicial, operadores):
        """
        Construtor da classe Problema

        Args:
            estado_inicial: estado atual
            operadores: lista de acções
        """
        self.__estado_inicial = estado_inicial
        self.__operadores = operadores

    @property
    def operadores(self):
        return self.__operadores

    @property
    def estado_inicial(self):
        return self.__estado_inicial

    @abstractmethod
    def objetivo(self, estado):
        "return bool"


