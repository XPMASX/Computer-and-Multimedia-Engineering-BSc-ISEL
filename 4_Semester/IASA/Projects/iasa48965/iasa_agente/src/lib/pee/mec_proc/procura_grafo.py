from pee.mec_proc.mec_proc import MecanismoProcura


class ProcuraGrafo(MecanismoProcura):
    """
    Para facilitar o processamento dos nós repetidos, pode ser mantida uma única memória de nós
    explorados que inclui os nós abertos e os nós fechados.
    Esta memória deve ser indexada por estado, de modo a possibilitar um acesso eficiente na
    verificação dos nós já explorados que, no caso geral, podem ser muito numerosos.
    Para eliminação de nós correspondentes a estados repetidos, é necessário verificar se um novo
    nó sucessor corresponde a um estado que já foi anteriormente explorado, se isso acontecer,
    apenas o nó que corresponde ao percurso com menor custo deve ser mantido, o outro nó
    correspondente ao mesmo estado, mas num percurso com maior custo deve ser eliminado.
    """

    def _iniciar_memoria(self):
        """
        Inicia a memória com fronteira e memória de nós explorados guardados num dicionário

        """
        super()._iniciar_memoria()
        self._explorados = dict()

    def _memorizar(self, no):
        """
        Memoriza um nó de acordo com o tipo de procura, concretiza o método abstracto do mecanismo de procura
        """
        if self._manter(no):
            self._explorados[no.estado] = no
            self._fronteira.inserir(no)

    def _manter(self, no):
        """
        Verifica se nó deve ser mantido para exploração
        """
        return no.estado not in self._explorados

    def complexidade_espacial(self):
        return len(self._explorados)
