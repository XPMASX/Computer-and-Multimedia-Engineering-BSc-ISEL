import random

from controlo_react.reaccoes.resposta.resposta_mover import RespostaMover
from sae import Direccao


class RespostaEvitar(RespostaMover):

    def __init__(self, dir_inicial=Direccao.ESTE):
        super().__init__(dir_inicial)

        self.__direccoes = list(Direccao)

    def activar(self, percepcao, intensidade):
        """
        Se houver contacto com o obstaculo e se existir uma direccao livre
        alteramos a dirreccao para uma direccao livre aleatoria
        Se não existir contacto com o obstaculo activamos a resposta com a
        direccao atual
        Se existir contacto com o obstaculo e se a direccao não for livre
        nada acontece
        """

        contacto_obst = percepcao.contacto_obst(self._accao.direccao)

        if contacto_obst:
            contacto_obst = not self.__alterar_direccao(percepcao)
        if not contacto_obst:
            return super().activar(percepcao, intensidade)

    def __alterar_direccao(self, percepcao):
        """
        Encontra uma direccao livre e verifica se existe uma direccao livre
        Muda a direccao da accao para ser igual a direccao livre e retornamos
        """

        direccao_livre = self.__direccao_livre(percepcao)
        if direccao_livre is not None:
            self._accao.direccao = direccao_livre
        return direccao_livre

    def __direccao_livre(self, percepcao):
        """
        Percorremos todas as direccoes e adicionamos as que estão livres a um novo array
        Retornamos uma aleatoria desse novo array
        """
        dir_livres = [direccao for direccao in self.__direccoes
                      if not percepcao.contacto_obst(direccao)]

        if dir_livres:
            return random.choice(dir_livres)
