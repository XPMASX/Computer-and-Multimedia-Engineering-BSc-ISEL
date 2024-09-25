from ecr.comport_comp import ComportComp


class Hierarquia(ComportComp):
    """Os comportamentos estão
        organizados numa hierarquia fixa
        de subsunção (supressão e substituição)
    """

    def seleccionar_accao(self, accoes):
        return accoes[0]
