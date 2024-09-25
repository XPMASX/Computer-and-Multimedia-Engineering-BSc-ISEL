from ecr.comportamento import Comportamento


class Reaccao(Comportamento):

    def __init__(self, estimulo, resposta):
        """Construtor da classe Reaccao

        Args:
            estimulo (Estimulo): atributo privado da classe
            resposta (Resposta): atributo privado da classe
        """
        self.__estimulo = estimulo
        self.__resposta = resposta

    def activar(self, percepcao):
        """Obtemos a intensidade do estimulo atraves da percepcao recebida
        se este for maior que 0 devolvemos a accao 

        Args:
            percepcao (Percepcao): _description_

        Returns:
            Accao: accao de acordo com a intensidade e percepcao
        """
        intensidade = self.__estimulo.detectar(percepcao)

        if intensidade > 0:
            return self.__resposta.activar(percepcao, intensidade)

