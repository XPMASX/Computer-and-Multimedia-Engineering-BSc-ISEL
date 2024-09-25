from sae import Controlo
from sae import Simulador
from ecr.hierarquia import Hierarquia
from ecr.prioridade import Prioridade
from controlo_react.controlo_react import ControloReact
from controlo_react.reaccoes.recolher.recolher import Recolher
from controlo_react.reaccoes.explorar.explorar import Explorar


class ControloTeste(Controlo):

    def processar(self, percepcao):
        """1 - Criamos uma lista com a reaccao explorar
        2 - Criamos um comportamento composto ao instanciar uma Hierarquia ou Prioridade com essa lista
        3 - Ativamos esse comportamento passando lhe a percepcao
        """
        #recolher = Recolher()
        #comportamentos = list([recolher])
        #comportamento = Hierarquia(comportamentos)
        ## print(comportamento.activar(percepcao))
        #return comportamento.activar(percepcao)


recolher = Recolher()
comportamentos = list([recolher])
comportamento = Hierarquia(comportamentos)
controlo = ControloReact(comportamento)
Simulador(1, controlo).executar()

"tem que utilizar um comp_composto"
