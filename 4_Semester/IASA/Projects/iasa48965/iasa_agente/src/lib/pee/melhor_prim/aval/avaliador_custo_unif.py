from pee.mec_proc.fronteira.avaliador import Avaliador


class AvaliadorCustoUnif(Avaliador):

    def prioridade(self, no):
        """
        A prioridade vai ser o custo do no

        Returns: Devolve o custo do no

        """
        return no.custo



