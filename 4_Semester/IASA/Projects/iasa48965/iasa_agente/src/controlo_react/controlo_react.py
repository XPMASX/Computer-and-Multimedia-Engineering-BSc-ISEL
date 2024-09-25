from sae import Controlo


class ControloReact(Controlo):

    def __init__(self, comportamento):
        self.__comportamento = comportamento

    def processar(self, percepcao):
        """Ativar o comportamento interno passando a percepcao
        """
        return self.__comportamento.activar(percepcao)
