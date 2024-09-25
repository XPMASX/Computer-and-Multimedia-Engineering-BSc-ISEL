from abc import ABC, abstractmethod


class Estimulo(ABC):
    """ Interface estimulo permite obter um estimulo através de uma percepção no ambiente
    
    Args:
        ABC (_type_): abstracao

    """

    @abstractmethod
    def detectar(self, percepcao):
        """Detetar um estimulo numa percepção

        Args:
            percepcao (Percepcao): 
        """
