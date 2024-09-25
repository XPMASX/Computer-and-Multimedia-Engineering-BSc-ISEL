from pee.mec_proc.fronteira.fronteira import Fronteira


class FronteiraFIFO(Fronteira):
    """
    primeiro a entrar primeiro a sair
    largura
    """
    def inserir(self, no):
        """
        Insere nó na fronteira de exploração, concretização depende do tipo de procura
        """
        self._nos.append(no)