from lib.sae import Controlo
from lib.sae import Simulador

class ControloTeste(Controlo):
    def processar(self, percepcao):
        print("processar")
        
Controlo = ControloTeste()
Simulador(1, Controlo).executar()