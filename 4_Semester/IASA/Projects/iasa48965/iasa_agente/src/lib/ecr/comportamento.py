from abc import ABC, abstractmethod


class Comportamento(ABC):
    """Interface comportamento em que este pode ser uma reaccao ou um comportamento composto"""

    @abstractmethod
    def activar(self, percepcao):
        """Ativar um dado comportamento"""
