from abc import abstractmethod, ABC


class Heuristica(ABC):

    @abstractmethod
    def h(self, estado):
        ""