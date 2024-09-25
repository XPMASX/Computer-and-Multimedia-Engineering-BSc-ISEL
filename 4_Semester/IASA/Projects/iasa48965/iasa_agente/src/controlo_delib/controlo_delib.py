from .mec_delib import MecDelib
from .modelo.modelo_mundo import ModeloMundo
from sae.agente.controlo import Controlo


class ControloDelib(Controlo):

    def __init__(self, planeador):
        self.__modelo_mundo = ModeloMundo()
        self.__mec_delib = MecDelib(self.__modelo_mundo)
        self.__planeador = planeador
        self.__objetivos = None
        self.__plano = None

    def processar(self, percepcao):
        """
        1- Assimila a percepcao
        2- Se for para reconsiderar deliberamos e planeamos
        3- devolver executar
        Args:
            percepcao: perceção que o agente tem do meio que o rodeia

        Returns: ação a tomar face a essa perceção

        """
        self.__assimilar(percepcao)
        if self.__reconsiderar():
            self.__deliberar()
            self.__planear()

        self.__mostrar()
        return self.__executar()

    def __assimilar(self, percepcao):
        """
        Atualiza o modelo mundo com a percepcao recebida

        Args:
            percepcao: perceção que o agente tem do meio que o rodeia

        """
        self.__modelo_mundo.actualizar(percepcao)

    def __reconsiderar(self):
        """
        Verifica se a representação do mundo foi alterada

        Returns: True se houve alteração logo é necessário reconsiderar

        """
        if self.__modelo_mundo.alterado or not self.__plano:
            return True

    def __deliberar(self):
        """
        Chama o deliberar() do mecanismo de deliberação
        """
        self.__objetivos = self.__mec_delib.deliberar()

    def __planear(self):
        """
        Tem como objetivo obter o plano através do planeador passando lhe
        o modelo do mundo e os objetivos
        Isto só acontece se existirem objetivos
        Se não colocamos o plano a None

        """
        if self.__objetivos:
            self.__plano = self.__planeador.planear(self.__modelo_mundo,
                                                    self.__objetivos)
        else:
            self.__plano = None

    def __executar(self):
        """
        Obtém-se, chamando o método obter_accao() do plano, o operador que deverá ser aplicado ao estado atual do agente
        no modelo do mundo.
        Se este não for nulo e existirem objetivos retornamos a ação a este ligada.

        Returns: ação que o agente deverá executar

        """
        if self.__plano:
            operador = self.__plano.obter_accao(self.__modelo_mundo.obter_estado())
            if operador:
                return operador.accao

    def __mostrar(self):
        """
        Permite mostrar visualmente o que está a acontecer no mundo.
        Começa por limpar a vista, de forma a que esta possa ser atualizada, de seguida representa
        os obtáculos, alvos e posição do agente (chamando o método mostrar() do ModeloMundo), chamando ainda
        mostrar() do plano.

        """
        if self.__plano:
            self.vista.limpar()
            self.__modelo_mundo.mostrar(self.vista)
            self.__plano.mostrar(self.vista)
