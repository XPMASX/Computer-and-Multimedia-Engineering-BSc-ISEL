from ecr.estimulo import Estimulo


class EstimuloObst(Estimulo):
    def __init__(self, direccao, intensidade=1.0):
        self.__direccao = direccao
        self.__intensidade = intensidade

    def detectar(self, percepcao):
        """
        Se houver contacto no movimento com a direccao que recebe devolve 1 de intensidade
        Se não devolve 0
        Isto indica-nos em que direccao nos podemos mexer sem atingir uma parede

        """
        if percepcao.contacto_obst(self.__direccao):
            return self.__intensidade
        else:
            return 0
