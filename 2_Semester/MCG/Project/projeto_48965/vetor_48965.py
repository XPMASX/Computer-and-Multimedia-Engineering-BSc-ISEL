
class Vetor3D:
    
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
        
        return 'Vetor3D(' + str(self.x) + ', ' + str(self.y) + ', ' \
               + str(self.z) + ')'
    
    def adiciona(self, outro_vetor):
        
        resultado_x = self.get_x() + outro_vetor.get_x()
        resultado_y = self.get_y() + outro_vetor.get_y()
        resultado_z = self.get_z() + outro_vetor.get_z()
        
        resultado = Vetor3D(resultado_x, resultado_y, resultado_z)
        
        return resultado
    
    def __add__(self, outro_vetor):
        
        return self.adiciona(outro_vetor)
    
    def multiplica_escalar(self, escalar):
        
        novo_x = self.get_x() * escalar
        novo_y = self.get_y() * escalar
        novo_z = self.get_z() * escalar
        
        resultado = Vetor3D(novo_x, novo_y, novo_z)
        
        return resultado
    
    def __mul__(self, escalar):
        
        return self.multiplica_escalar(escalar)
    
    def comprimento(self):
        
        from math import sqrt
        
        resultado = self.get_x()**2.0 + self.get_y()**2.0 + self.get_z()**2.0 
        resultado = sqrt(resultado)
        
        return resultado
        
    def versor(self):
        
        fator = 1.0 / self.comprimento()
        
        return self * fator
    
    def interno(self, outro_vetor):
        
        return self.get_x() * outro_vetor.get_x() \
               + self.get_y() * outro_vetor.get_y() \
               + self.get_z() * outro_vetor.get_z() \
    
    def externo(self, outro_vetor):
        
        x1 = self.get_x()
        y1 = self.get_y()
        z1 = self.get_z()
        
        x2 = outro_vetor.get_x()
        y2 = outro_vetor.get_y()
        z2 = outro_vetor.get_z()
        
        x = y1 * z2 - z1 * y2
        y = -(x1 * z2 - z1 * x2)
        z = x1 * y2 - y1 * x2
        
        resultado = Vetor3D(x, y, z)
        
        return resultado



