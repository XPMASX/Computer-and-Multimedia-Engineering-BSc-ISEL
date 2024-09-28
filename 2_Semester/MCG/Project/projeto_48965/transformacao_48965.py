from matriz_48965 import Matriz
from vetor_48965 import Vetor3D
from face_48965 import FaceTriangular
from ponto_48965 import Ponto3D
from cor_rgb_48965 import CorRGB
from cor_phong_48965 import CorPhong
from plano_48965 import Plano
import math

class Transformacao:
    
    def __init__(self):
        
        matriz = Matriz(4,4)
        matriz.set_entrada(1,1,1.0)
        matriz.set_entrada(2,2,1.0)
        matriz.set_entrada(3,3,1.0)
        matriz.set_entrada(4,4,1.0)
        
        self.matriz = matriz 
        
    def __repr__(self):
        
        return str(self.matriz)
    
    def transforma_face_triangular(self, uma_face_triangular):
             
        ponto1 = Matriz(4,1)
        ponto1.set_entrada(1,1,uma_face_triangular.get_ponto1().get_x())
        ponto1.set_entrada(2,1,uma_face_triangular.get_ponto1().get_y())
        ponto1.set_entrada(3,1,uma_face_triangular.get_ponto1().get_z())
        ponto1.set_entrada(4,1,1)
        
        ponto2 = Matriz(4,1)
        ponto2.set_entrada(1,1,uma_face_triangular.get_ponto2().get_x())
        ponto2.set_entrada(2,1,uma_face_triangular.get_ponto2().get_y())
        ponto2.set_entrada(3,1,uma_face_triangular.get_ponto2().get_z())
        ponto2.set_entrada(4,1,1)
        
        ponto3 = Matriz(4,1)
        ponto3.set_entrada(1,1,uma_face_triangular.get_ponto3().get_x())
        ponto3.set_entrada(2,1,uma_face_triangular.get_ponto3().get_y())
        ponto3.set_entrada(3,1,uma_face_triangular.get_ponto3().get_z())
        ponto3.set_entrada(4,1,1)        
       
        #if (ponto1.get_entrada(1, 1) < 0):
        #    self.translacao(Vetor3D(-1,0,0))
        #else:
        #   self.translacao(Vetor3D(1,0,0))
        
        #self.escalamento(0.8,0.8,1)
        
        #self.shearing(0.3,0.3,0.3,0.3,0.3,0.3)
        
        #self.rotacao_z(math.pi/6)
        
        #self.rotacao_y(math.pi/6)
        
        #self.rotacao_x(math.pi/6)
        
        ponto1 = self.matriz * ponto1
        ponto2 = self.matriz * ponto2
        ponto3 = self.matriz * ponto3
        
        v1 = Ponto3D(ponto1.get_entrada(1,1), ponto1.get_entrada(2,1), ponto1.get_entrada(3,1))
        v2 = Ponto3D(ponto2.get_entrada(1,1), ponto2.get_entrada(2,1), ponto2.get_entrada(3,1))
        v3 = Ponto3D(ponto3.get_entrada(1,1), ponto3.get_entrada(2,1), ponto3.get_entrada(3,1))
        
        nova_face_triangular = FaceTriangular(v1, v2, v3, uma_face_triangular.get_cor_phong())
        
        return nova_face_triangular
        
        
    def translacao(self, um_vetor):
        
        self.matriz.set_entrada(1,4,um_vetor.get_x())
        self.matriz.set_entrada(2,4,um_vetor.get_y())
        self.matriz.set_entrada(3,4,um_vetor.get_z())
        
        return
    
    def escalamento(self, sx, sy, sz):
        
        self.matriz.set_entrada(1,1,sx)
        self.matriz.set_entrada(2,2,sy)
        self.matriz.set_entrada(3,3,sz)
        
        return
    
    def shearing (self, sxy, sxz, syx, syz, szx, szy):
        
        self.matriz.set_entrada(1,2,sxy)
        self.matriz.set_entrada(1,3,sxz)
        self.matriz.set_entrada(2,1,syx)
        self.matriz.set_entrada(2,3,syz)  
        self.matriz.set_entrada(3,1,szx)
        self.matriz.set_entrada(3,2,szy)
                   
        return
    
    def rotacao_z (self, angulo):
        
        self.matriz.set_entrada(1,1,math.cos(angulo))
        self.matriz.set_entrada(1,2,math.sin(-angulo))
        self.matriz.set_entrada(2,1,math.sin(angulo))
        self.matriz.set_entrada(2,2,math.cos(angulo))       
        
        return
    
    def rotacao_y (self, angulo):
        
        self.matriz.set_entrada(1,1,math.cos(angulo))
        self.matriz.set_entrada(1,3,math.sin(-angulo))
        self.matriz.set_entrada(3,1,math.sin(angulo))
        self.matriz.set_entrada(3,3,math.cos(angulo))       
        
        return  
    
    def rotacao_x (self, angulo):
        
        self.matriz.set_entrada(2,2,math.cos(angulo))
        self.matriz.set_entrada(2,3,math.sin(-angulo))
        self.matriz.set_entrada(3,2,math.sin(angulo))
        self.matriz.set_entrada(3,3,math.cos(angulo))       
        
        return  
