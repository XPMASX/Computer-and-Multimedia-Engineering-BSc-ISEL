class Solucao:
    """
    Representa um percurso correspondente a uma solução de um problema
    """
    def __init__(self, no_final):
        """
        A solução é uma sequencia de nos que representa o percurso
        Args:
            no_final:
        """
        self.__percurso = []
        no = no_final
        while no:
            self.__percurso.insert(0, no)
            no = no.antecessor

    @property
    def dimensao(self):
        """
        Retorna a dimensão da lista
        """
        return len(self.__percurso)

    def remover(self):
        """
        Permite remover o primeiro nó do percurso
        """
        if self.__percurso:
            return self.__percurso.pop(0)

    def __iter__(self):
        """
        Permite iterar sobre a lista de nos que constitui a nossa solucao

        Returns: retorna um iterador da solucao
        """
        return iter(self.__percurso)

    def __getitem__(self, index):
        """
        Retorna um nó da solução por índice
        """

        return self.__percurso[index]
