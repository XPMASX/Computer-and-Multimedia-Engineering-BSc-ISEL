import numpy as np


def soma_produto_listas(lista1, lista2):
    
    resultado = 0.0
    
    for i in range(len(lista1)):
        
        produto = lista1[i] * lista2[i]
        resultado = resultado + produto
        
    return resultado

class Matriz:
    
    def __init__(self, numero_linhas, numero_colunas):
        
        self.numero_linhas = numero_linhas
        self.numero_colunas = numero_colunas
        
        self.linhas = []
        
        for m in range(numero_linhas):
            linha = []
            for n in range(numero_colunas):
                linha.append(0.0)
            self.linhas.append(linha)
            
    
    def __repr__(self):
        
        resultado = 'Matriz(' + str(self.numero_linhas) + ', ' + \
                        str(self.numero_colunas) + ')\n'
        
        for m in range(self.numero_linhas):
            for n in range(self.numero_colunas):
                resultado = resultado + str(self.linhas[m][n]) + ' '
            resultado = resultado +'\n'
            
        return resultado
    
    def set_entrada(self, linha, coluna, valor):
        
        self.linhas[linha -1][coluna - 1] = valor
        
    def get_entrada(self, linha, coluna):
        
        return self.linhas[linha -1][coluna - 1]   
    
    def adiciona(self, outra_matriz):
        
        resultado = Matriz(self.numero_linhas, self.numero_colunas)
        
        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                soma = self.get_entrada(l + 1, c +1) \
                       + outra_matriz.get_entrada(l + 1, c +1)
                resultado.set_entrada(l +1, c + 1, soma)
        
        return resultado
    
    def __add__(self, outra_matriz):
        
    
        return self.adiciona(outra_matriz) 
    
    def get_linha(self, linha):
        
        resultado = []
        
        for c in range(self.numero_colunas):
            entrada = self.get_entrada(linha, c + 1)
            resultado.append(entrada)
            
        return resultado
    
    def get_coluna(self, coluna):
        
        resultado = []
        
        for l in range(self.numero_linhas):
            entrada = self.get_entrada(l + 1, coluna)
            resultado.append(entrada)
            
        return resultado    
    
    def multiplica(self, outra_matriz):
        
        resultado = Matriz(self.numero_linhas, outra_matriz.numero_colunas)
        
        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                linha = self.get_linha(l + 1)
                coluna = outra_matriz.get_coluna(c + 1)
                entrada = soma_produto_listas(linha, coluna)
                resultado.set_entrada(l + 1, c + 1, entrada)
                
        return resultado
    
    def multiplica_escalar(self, escalar):

        resultado = Matriz(self.numero_linhas, self.numero_colunas)

        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                entrada = self.get_entrada(l + 1, c +1) * escalar   
                resultado.set_entrada(l +1, c + 1, entrada)

        return resultado    
    
    def __mul__(self, valor):
        
        if isinstance(valor, Matriz):
            return self.multiplica(valor)
        else:
            return self.multiplica_escalar(valor)
        
    def det_2x2(self):
        
        a = self.linhas[0][0]
        b = self.linhas[0][1]
        c = self.linhas[1][0]
        d = self.linhas[1][1]
        
        return a*d - b*c
    
    def det_3x3(self):
        
        a = self.get_entrada(1, 1)
        b = self.get_entrada(1, 2)
        c = self.get_entrada(1, 3)
        d = self.get_entrada(2, 1)
        e = self.get_entrada(2, 2)
        f = self.get_entrada(2, 3)
        g = self.get_entrada(3, 1)
        h = self.get_entrada(3, 2)
        i = self.get_entrada(3, 3)       
        
        return a*e*i + b*f*g + c*d*h -g*e*c - h*f*a - i*d*b    
    
    def sub_matriz(self, linha_a_remover, coluna_a_remover):
        
        resultado = Matriz(self.numero_linhas - 1, self.numero_colunas -1)
        
        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                ll = l
                cc = c
                if l >= linha_a_remover - 1:
                    ll = l + 1
                if c >= coluna_a_remover - 1:
                    cc = c + 1
                entrada = self.get_entrada(ll + 1, cc + 1)
                resultado.set_entrada(l + 1, c + 1,entrada)
                
        return resultado
    
    def det(self):
        
        if self.numero_linhas == 1:
            return self.get_entrada(1,1)
        elif self.numero_linhas == 2:
            return self.det_2x2()
        elif self.numero_linhas == 3:
            return self.det_3x3()
        else:
            soma = 0.0
            for j in range(self.numero_linhas):
                soma = soma + (-1.0)**(1+j+1)*self.get_entrada(1, j+1) \
                       * self.sub_matriz(1,j+1).det()  
        return soma
    
    def transposta(self):
        
        resultado = Matriz(self.numero_colunas, self.numero_linhas)
        
        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                entrada = self.get_entrada(c + 1, l + 1)
                resultado.set_entrada(l + 1, c + 1, entrada)
        
        return resultado
    
    def copia(self):

        resultado = Matriz(self.numero_linhas, self.numero_colunas)

        for l in range(resultado.numero_linhas):
            for c in range(resultado.numero_colunas):
                entrada = self.get_entrada(l + 1, c + 1)
                resultado.set_entrada(l + 1, c + 1, entrada)

        return resultado    
    
    def set_linha(self, linha, uma_lista):
        
        resultado = []
        
        for c in range(self.numero_colunas):
            self.set_entrada(linha, c + 1, uma_lista[c])
            
        return self
    
    def set_coluna(self, coluna, uma_lista):
        
        resultado = []
        
        for l in range(self.numero_linhas):
            self.set_entrada(l + 1, coluna, uma_lista[l])
            
        return self   
    
        
          



