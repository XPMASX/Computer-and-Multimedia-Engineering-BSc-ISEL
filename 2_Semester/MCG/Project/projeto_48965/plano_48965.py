from ponto_48965  import Ponto3D
from reta_48965   import Reta
from matriz_48965 import Matriz

TOLERANCIA_ZERO = 10.0**(-10)

class ErroPontosColineares(Exception):
    '''excecao lancada com pontos colineares'''

class Plano:
    
    def __init__(self, ponto1, ponto2, ponto3):
        
        self.ponto1 = ponto1
        self.ponto2 = ponto2
        self.ponto3 = ponto3
        
        v12 = ponto2 - ponto1
        v13 = ponto3 - ponto1
        
        externo = v12.externo(v13)
        
        if externo.comprimento() < TOLERANCIA_ZERO:
            raise ErroPontosColineares
        
        self.normal = externo.versor()
        
    def __repr__(self):
        
        return 'Plano(' + str(self.ponto1) + ', ' + str(self.ponto2) + ', ' \
               + str(self.ponto3) + str(self.normal) +')'
    
    
    def interceta_triangulo(self,reta):
        
        ax = self.ponto1.get_x()
        ay = self.ponto1.get_y()
        az = self.ponto1.get_z()
        
        bx = self.ponto2.get_x()
        by = self.ponto2.get_y()
        bz = self.ponto2.get_z()
        
        cx = self.ponto3.get_x()
        cy = self.ponto3.get_y()
        cz = self.ponto3.get_z() 
        
        vx = reta.get_vetor_diretor().get_x()
        vy = reta.get_vetor_diretor().get_y()
        vz = reta.get_vetor_diretor().get_z()
        
        px = reta.get_origem().get_x()
        py = reta.get_origem().get_y()
        pz = reta.get_origem().get_z()
        
        matriz_A = Matriz(3, 3)
        matriz_A.set_coluna(1, [bx-ax, by-ay, bz-az])
        matriz_A.set_coluna(2, [cx-ax, cy-ay, cz-az])
        matriz_A.set_coluna(3, [-vx, -vy, -vz])
        
        det_A = matriz_A.det()
        
        if abs(det_A) < TOLERANCIA_ZERO:
            return [False, None, None]
        
        matriz_t = matriz_A.copia()
        matriz_t.set_coluna(3, [px-ax, py-ay, pz-az])
        det_t = matriz_t.det()
        t = det_t / det_A
        if t < 0.0:
            return [False, None, None]
        
        matriz_tB = matriz_A.copia()
        matriz_tB.set_coluna(1, [px-ax, py-ay, pz-az])
        det_tB = matriz_tB.det()
        tB = det_tB / det_A
        if tB < 0.0 or tB > 1.0:
            return [False, None, None]
        
        matriz_tC = matriz_A.copia()
        matriz_tC.set_coluna(2, [px-ax, py-ay, pz-az])
        det_tC = matriz_tC.det()
        tC = det_tC / det_A
        if tC < 0.0 or tC > 1.0:
            return [False, None, None]      
        
        tA = 1.0 - tB - tC
        if tA < 0.0 or tA > 1.0:
            return [False, None, None]
        
        A = self.ponto1
        B = self.ponto2
        C = self.ponto3
        vAB = B - A
        vAC = C - A
        ponto_intercecao = A + ((vAB * tB) + ( vAC * tC))
        
        return [True, ponto_intercecao, t]
    
    def get_coordenadas(self):
            
            ax = self.ponto1.get_x()
            ay = self.ponto1.get_y()
            az = self.ponto1.get_z()
            
            bx = self.ponto2.get_x()
            by = self.ponto2.get_y()
            bz = self.ponto2.get_z()
            
            cx = self.ponto3.get_x()
            cy = self.ponto3.get_y()
            cz = self.ponto3.get_z() 
            
            return ax,ay,az,bx,by,bz,cx,cy,cz
            
            


a = Ponto3D(0.0, 0.0, 0.0)
b = Ponto3D(2.0, 0.0, 0.0)
c = Ponto3D(0.0, 2.0, 0.0)
plano1 = Plano(a, b, c)

ax,ay,az,bx,by,bz,cx,cy,cz = plano1.get_coordenadas()

a1 = bx - ax
b1 = by - ay
c1 = bz - az
a2 = cx - ax
b2 = cy - ay
c2 = cz - az
a = b1 * c2 - b2 * c1
b = a2 * c1 - a1 * c2
c = a1 * b2 - b1 * a2
print(a,b,c)
d = (-a * ax - b * ay - c * az)
print(d)

print(plano1.get_coordenadas())