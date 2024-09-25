from abc import abstractmethod, ABC


class Estado(ABC):
    """Configuranção do sistema
    Representa uma situação (configuração) na resolução de um problema
    Contém uma identificação unica logo precisamos de criar o metodo __hash__ que define essa identificação
    e o método __eq__ que define uma relação de igualdade consistente com a definição de identificação
    """

    @abstractmethod
    def id_valor(self):
        """
        Define identificação única do estado em função da sua informação (valor de estado)

        Returns: int

        """

    def __hash__(self):
        """
        Define identificação única de um objecto

        Returns: Estabelece a identificação unica do objeto

        """
        return self.id_valor()

    def __eq__(self, other):
        """
        Define relação de igualdade consistente com definição de identificação

        Args:
            other: objeto com o qual quer comparar identificação

        Returns: devolve o resultado da comparação entre a identificação do proprio com o que recebe

        """
        return self.__hash__() == other.__hash__()
