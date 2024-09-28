class CorRGB:
    
    def __init__(self, red, green, blue):
        
##        if red <0.0:
##            self.r = 0.0
##        elif red > 1.0:
##            self.r = 1.0
##        else:
##            self.r = red
        
        self.r = min(max(red, 0.0), 1.0)
        self.g = min(max(green, 0.0), 1.0)
        self.b = min(max(blue, 0.0), 1.0)
        
    def __repr__(self):
        
        return str(int(self.r * 255.0)) + ' ' \
               + str(int(self.g * 255.0)) + ' ' \
               + str(int(self.b * 255.0))
    
    
    def soma(self, outra_cor):
        
        novo_r = self.r + outra_cor.r
        novo_g = self.g + outra_cor.g
        novo_b = self.b + outra_cor.b
        
        nova_cor = CorRGB(novo_r, novo_g, novo_b)
        
        return nova_cor
    
    def __add__(self, outra_cor):
        
        return self.soma(outra_cor)
    
    def multiplica(self, outra_cor):
        
        novo_r = self.r * outra_cor.r
        novo_g = self.g * outra_cor.g
        novo_b = self.b * outra_cor.b
        
        nova_cor = CorRGB(novo_r, novo_g, novo_b)
        
        return nova_cor   
    
    def multiplica_escalar(self, escalar):
        
        novo_r = self.r * escalar
        novo_g = self.g * escalar
        novo_b = self.b * escalar
        
        nova_cor = CorRGB(novo_r, novo_g, novo_b)
        
        return nova_cor
    
    def __mul__(self, valor):
        
        if isinstance(valor, float):
            return self.multiplica_escalar(valor)
        else:
            return self.multiplica(valor)
