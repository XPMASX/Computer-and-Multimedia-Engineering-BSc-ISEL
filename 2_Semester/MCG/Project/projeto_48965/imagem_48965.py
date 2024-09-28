from cor_rgb_48965 import CorRGB

from io import StringIO

class Imagem:
    
    def __init__(self, numero_linhas, numero_colunas):
        
        self.numero_linhas  = numero_linhas
        self.numero_colunas = numero_colunas
        self.linhas         = []
        
        for il in range(numero_linhas):
            linha = []
            for ic in range(numero_colunas):
                pixel = CorRGB(0.0, 0.0, 0.0)
                linha.append(pixel)
            self.linhas.append(linha)
            
    def __repr__(self):
        
        resultado = StringIO()
        
        resultado.write('P3\n')
        resultado.write('# imagem criada em MCG/LEIM/ISEL\n')
        resultado.write(str(self.numero_colunas) + ' ')
        resultado.write(str(self.numero_linhas) + '\n')
        resultado.write('255\n')
        for linha in self.linhas:
            for pixel in linha:
                resultado.write(str(pixel)+ ' ')
            resultado.write('\n')
        
        resultado_string = resultado.getvalue()
        
        resultado.close()
        
        return resultado_string
    
    def set_cor(self, linha, coluna, cor_rgb):
        
        self.linhas[linha -1][coluna -1] = cor_rgb
        
    def get_cor(self, linha, coluna):
        
        return self.linhas[linha -1][coluna -1]    
    
    def guardar_como_ppm(self, nome_ficheiro):
        
        ficheiro = open(nome_ficheiro, 'w')
        ficheiro.write(str(self))
        ficheiro.close()
            



#from colorsys import hsv_to_rgb
#linhas = 256
#colunas = 256
#imagem6 = Imagem(linhas, colunas)
#h = 130.0/360.0 # H (Hue)
#for l in range(linhas):
#    s = l 
#    for c in range(colunas):
#        v = c 
#        (r, g, b) = hsv_to_rgb(h, s/255.0, v/255.0)
#        pixel = CorRGB(r, g, b)
#        imagem6.set_cor(l+1, c+1, pixel)
#imagem6.guardar_como_ppm('imagem6.ppm')