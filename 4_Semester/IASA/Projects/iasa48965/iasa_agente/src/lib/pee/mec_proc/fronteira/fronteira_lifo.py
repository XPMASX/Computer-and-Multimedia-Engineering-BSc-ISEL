from pee.mec_proc.fronteira.fronteira import Fronteira


class FronteiraLIFO(Fronteira):
    """
    ultimo a entrar primeiros a sair
    profundidade
    """

    def inserir(self, no):
        """
        Insere nó na fronteira de exploração, concretização depende do tipo de procura
        """
        self._nos.insert(0, no)
