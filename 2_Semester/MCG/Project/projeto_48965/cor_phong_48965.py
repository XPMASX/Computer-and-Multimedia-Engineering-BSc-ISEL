from cor_rgb_48965 import CorRGB
from ponto_48965 import Ponto3D
from vetor_48965 import Vetor3D
from luz_48965 import LuzPontual
from Imagem_48965 import Imagem

class CorPhong:
    
    def __init__(self, k_ambiente, k_difusa, k_especular, 
                 brilho):

        self.k_ambiente = k_ambiente
        self.k_difusa = k_difusa
        self.k_especular = k_especular
        self.brilho = brilho
        
    def __repr__(self):
        
        return 'CorPhong(' + str(self.k_ambiente) + ', ' \
               + str(self.k_difusa) + ', ' \
               + str(self.k_especular) + ', ' \
               + str(self.brilho) + ')'     
    
    def get_cor_rgb(self, luz, direcao_luz, normal, direcao_olho, sombra):
        
        cor_ambiente = self.k_ambiente * luz.get_intensidade_ambiente()
        
        if sombra == True:
            return cor_ambiente
        
        n_interno_l = normal.interno(direcao_luz)
        
        if n_interno_l < 0.0:
            return cor_ambiente
        
        cor_difusa = (self.k_difusa * luz.get_intensidade_difusa()) * n_interno_l
        
        r = direcao_luz * (-1.0) + normal * (2.0 * n_interno_l)
        
        cor_especular = (self.k_especular * luz.get_intensidade_especular()) \
                         * (abs(direcao_olho.interno(r)**self.brilho))
        
        cor_total = cor_ambiente + cor_difusa + cor_especular
        
        return cor_total