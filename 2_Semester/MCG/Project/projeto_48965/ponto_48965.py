from vetor_48965 import Vetor3D

class Ponto3D:
    
    def __init__(self, x, y, z):
        
        self.x = x
        self.y = y
        self.z = z
        
    def get_x(self):
        
        return self.x
    
    def get_y(self):
        
        return self.y    
    
    def get_z(self):
        
        return self.z 
    
    def __repr__(self):
        
        return 'Ponto3D(' + str(self.x) + ', ' + str(self.y) + ', ' \
               + str(self.z) + ')'    
    
    def adiciona_vetor(self, um_vetor):
        
        novo_x = self.get_x() + um_vetor.get_x()
        novo_y = self.get_y() + um_vetor.get_y()
        novo_z = self.get_z() + um_vetor.get_z()
        
        resultado = Ponto3D(novo_x, novo_y, novo_z)
        
        return resultado
    
    def __add__(self, um_vetor):
        
        return self.adiciona_vetor(um_vetor)
    
    def subtrai_ponto(self, ponto_inicial):
        
        novo_x = self.get_x() - ponto_inicial.get_x()
        novo_y = self.get_y() - ponto_inicial.get_y()
        novo_z = self.get_z() - ponto_inicial.get_z()
        
        resultado = Vetor3D(novo_x, novo_y, novo_z)
        
        return resultado  
    
    def __sub__(self, ponto_inicial):
        
        return self.subtrai_ponto(ponto_inicial)
    

