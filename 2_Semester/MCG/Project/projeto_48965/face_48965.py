from plano_48965 import Plano, ErroPontosColineares
from ponto_48965 import Ponto3D
from cor_phong_48965 import CorPhong
from cor_rgb_48965 import CorRGB

class FaceTriangular(Plano):
    
    def __init__(self, ponto1, ponto2, ponto3, cor_phong):
        
        super().__init__(ponto1, ponto2, ponto3)
        
        
        self.cor_phong = cor_phong
        
        
    def __repr__(self):
        
        return 'FaceTriangular(' + super().__repr__() + ', ' \
               + str(self.cor_phong) + ')'
    
    def get_cor_phong(self):
        
        return self.cor_phong
    

    def get_ponto1(self):
        
        return self.ponto1
    
    def get_ponto2(self):
        
        return self.ponto2
    
    def get_ponto3(self):
        
        return self.ponto3  