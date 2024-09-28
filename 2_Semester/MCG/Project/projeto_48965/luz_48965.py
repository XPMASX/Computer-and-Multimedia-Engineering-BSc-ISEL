from ponto_48965 import Ponto3D
from cor_rgb_48965 import CorRGB

class LuzPontual:
    
    def __init__(self, posicao, intensidade_ambiente, intensidade_difusa, 
                 intensidade_especular):
        
        self.posicao = posicao
        self.intensidade_ambiente = intensidade_ambiente
        self.intensidade_difusa = intensidade_difusa
        self.intensidade_especular = intensidade_especular
        
    def __repr__(self):
        
        return 'LuzPontual(' + str(self.posicao) + ', ' \
               + str(self.intensidade_ambiente) + ', ' \
               + str(self.intensidade_difusa) + ', ' \
               + str(self.intensidade_especular) + ')' 
    
    def get_posicao(self):
        
        return self.posicao
    
    def get_intensidade_ambiente(self):
        
        return self.intensidade_ambiente    
    
    def get_intensidade_difusa(self):
        
        return self.intensidade_difusa     
    
    def get_intensidade_especular(self):
        
        return self.intensidade_especular    

