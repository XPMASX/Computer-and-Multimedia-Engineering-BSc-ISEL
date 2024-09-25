from ecr.comport_comp import ComportComp


class Prioridade(ComportComp):
    """As respostas são seleccionadas
        de acordo com uma prioridade
        associada que varia ao longo da
        execução
        As acções são seleccionadas de acordo com uma prioridade associada
        que varia ao longo da execução
        
        funcao inversa quanto maior a proximidade mais prioridade
    """

    def seleccionar_accao(self, accoes):
        """utilizamos uma função anonima para retornar a accao com maior prioridade

        Returns:
            accao: retorna a accao da lista de accoes com maior prioridade
        """
        return max(accoes, key=lambda a: a.prioridade)
