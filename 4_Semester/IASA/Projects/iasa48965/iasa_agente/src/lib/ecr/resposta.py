class Resposta:
    """Classe com o objetivo de activar e perceber qual a accao
    """

    def __init__(self, accao):
        self._accao = accao

    def activar(self, percepcao, intensidade=0):
        """Define a prioridade da acção geralmente igual a intensidade
        

        Args:
            percepcao (Percepcao): Em caso geral não é utilizada
            intensidade (Intensidade): Prioridade da accao

        Returns:
            Accao
        """

        self._accao.prioridade = intensidade

        return self._accao
