from abc import abstractmethod, ABC


class Operador(ABC):
    """
    Interface operador representa uma acção
    """

    @abstractmethod
    def aplicar(self, estado):
        """
        Gera transformação de estado (operador.aplicar: estado → estado)

        Returns: Estado

        """

    @abstractmethod
    def custo(self, estado, estado_suc):
        """
       indica o custo de transitar do estado atual para o sucessor

       Returns: double

        """

