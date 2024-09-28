
from ponto_48965 import Ponto3D

TOLERANCIA_ZERO = 10.0**(-10)

class ErroPontosCoincidentes(Exception):
    ''' classe do erro da criacao de retas com pontos coincidentes.'''

class Reta:
    
    def __init__(self, origem, destino):
        
        self.origem = origem
        self.destino = destino
        vetor = destino - origem
        
        
        #if vetor.comprimento() == 0.0: #proibido
        if vetor.comprimento() < TOLERANCIA_ZERO:
            raise ErroPontosCoincidentes
        
        self.vetor_diretor = vetor.versor()
        
    def __repr__(self):
        
        return 'Reta(' + str(self.origem) + ', ' + str(self.destino) + ', ' \
               + str(self.vetor_diretor) + ')'
    
    def get_origem(self):
        
        return self.origem
    
    def get_destino(self):
        
        return self.destino
    
    def get_vetor_diretor(self):
        
        return self.vetor_diretor