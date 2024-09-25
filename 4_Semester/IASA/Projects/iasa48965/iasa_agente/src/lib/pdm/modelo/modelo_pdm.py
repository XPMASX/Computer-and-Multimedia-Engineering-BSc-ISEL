from abc import ABC, abstractmethod


class ModeloPDM(ABC):

    """
    Contrato relacionado à representação não determinista do mundo sob a forma de PDM.
    Será usado no Processo de Decisão de Markov, disponibilizando métodos que permitirão a representação
    da cadeia de Markov, em tempo discreto.
    """

    @abstractmethod
    def S(self):
        "Retorna o conjunto de estados do mundo"

    @abstractmethod
    def A(self, s):
        "Retorna o conjunto de acções possíveis com inicio no estado s recebido"

    @abstractmethod
    def T(self, s, a, sn):
        "Probabilidade de transição entre o estado s e o estado seguinte através da acao a"

    @abstractmethod
    def R(self, s, a, sn):
        "Retorna a recompensa esperada na transição de s para sn através da acao a"

    @abstractmethod
    def estados_sucessores(self, s, a):
        "Aplica a acção aos estados e devolve os estados seguintes"

