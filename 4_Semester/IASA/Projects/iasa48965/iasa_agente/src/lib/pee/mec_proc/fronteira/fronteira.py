from abc import abstractmethod, ABC


class Fronteira(ABC):
    """
    Permite inserir e remover nós de forma ordenada
    """

    def __init__(self):
        self.iniciar()

    def iniciar(self):
        """
        Inicializa a lista de nos
        """
        self._nos = []

    @abstractmethod
    def inserir(self, no):
        """
        Insere nó na fronteira de exploração, concretização depende do tipo de procura
        """

    @property
    def vazia(self):
        """
        Indica se a fronteira está vazia
        """
        return self.dimensao == 0

    @property
    def dimensao(self):
        """
        Indica a dimensão da fronteira
        """
        return len(self._nos)

    def remover(self):
        """
        Remove nó inicial da fronteira de exploração
        """
        return self._nos.pop(0)
