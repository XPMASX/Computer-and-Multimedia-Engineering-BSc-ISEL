from cor_rgb_48965 import CorRGB
from cor_phong_48965 import CorPhong
from ponto_48965 import Ponto3D
from face_48965 import FaceTriangular
from luz_48965 import LuzPontual
from vetor_48965 import Vetor3D
from camara_48965 import Camara
from Imagem_48965 import Imagem
from reta_48965 import Reta
from transformacao_48965 import Transformacao

class RayTracer:
    
    def __init__(self, lista_faces, lista_luzes, camara, cor_fundo):
        
        self.lista_faces = lista_faces
        self.lista_luzes = lista_luzes
        self.camara = camara 
        self.cor_fundo = cor_fundo


    def __repr__(self):
        
        resultado = 'RayTracer('
        
        for face in self.lista_faces:
            resultado = resultado + str(face) + '\n'
        for luz in self.lista_luzes:
            resultado = resultado + str(luz) + '\n'
        resultado = resultado + str(self.camara) + '\n'
        resultado = resultado + str(self.cor_fundo) + ')'
        
        return resultado
    
    def get_face_mais_proxima(self, lista_faces):
        
        indice = 0
        t = lista_faces[indice][2]
        indice_t_min = indice
        t_min = t
        
        for indice in range(1, len(lista_faces)):
            t = lista_faces[indice][2]
            if t < t_min:
                indice_t_min = indice
                t_min = t
        
        
        face = lista_faces[indice_t_min][0]
        ponto = lista_faces[indice_t_min][1]
        
        return [face, ponto]
    
    def get_face_visivel(self, raio):
        
        auxiliar = []
        
        for face in self.lista_faces:
            
            [interceta, ponto_intercecao, t] = face.interceta_triangulo(raio)
            if interceta == True:
                auxiliar.append([face, ponto_intercecao, t])
                
        if len(auxiliar) == 0:
            return [False, None, None]
        
        [face, ponto] = self.get_face_mais_proxima(auxiliar)
        
        return [True, face, ponto]
    
    def get_sombra (self, ponto, luz, face_ponto):
        
        origem = ponto 
        destino = luz.get_posicao()
        raio_sombra = Reta(origem, destino)
        
        distancia = (destino - origem).comprimento()
        
        for face in self.lista_faces:
            
            if face is not face_ponto:
            
                [interceta , ponto_intercecao, t] = face.interceta_triangulo(raio_sombra)
            
                if interceta == True:
                    
                    if t < distancia:
                        return True
       
        return False
        
        
    
    def get_cor_face(self, face, ponto):
        
        cor_total = CorRGB(0.0, 0.0, 0.0)
        
        cor_face = face.get_cor_phong()
        normal = face.normal
        direcao_olho = (self.camara.get_posicao() - ponto).versor()
        
        for luz in self.lista_luzes:
            
            direcao_luz = (luz.get_posicao() - ponto).versor()
            sombra = self.get_sombra(ponto, luz, face)
            
            cor = cor_face.get_cor_rgb(luz, direcao_luz, normal, direcao_olho, sombra)
            
            cor_total = cor_total + cor
            
        return cor_total
    
    
    def get_cor_vista_por_raio(self,raio):
        
        aux = self.get_face_visivel(raio)
        
        ha_face = aux[0]
        face = aux[1]
        ponto = aux[2]
        
        if ha_face == True:
            return self.get_cor_face(face, ponto)
        else:
            return self.cor_fundo
        
        return CorRGB(1.0, 1.0, 1.0)
    
    
    
    
    def renderiza (self):
        
        numero_linhas = self.camara.get_resolucao_vertical()
        numero_colunas = self.camara.get_resolucao_horizontal()
        resultado = Imagem(numero_linhas, numero_colunas)
        
        origem = self.camara.get_posicao()
        for linha in range(numero_linhas):
            print('linha ' + str(linha + 1) + ' de ' + str(numero_linhas))
            for coluna in range(numero_colunas):
                destino = self.camara.get_pixel_global(linha+1, coluna+1)
                raio_visao = Reta(origem, destino)
                cor_pixel = self.get_cor_vista_por_raio(raio_visao)
                resultado.set_cor(linha+1, coluna+1, cor_pixel)
        
        return resultado


transformacao = Transformacao()

# teste ao construtor
vermelho = CorRGB(1.0, 0.0, 0.0)
branco = CorRGB(1.0, 1.0, 1.0)
preto = CorRGB(0.0, 0.0, 0.0)
cinzento = CorRGB(0.25, 0.25, 0.25)
brilho = 100.0
cor_letras = CorPhong(vermelho*0.25, vermelho*0.75, vermelho*0.75, brilho)
cor_chao = CorPhong(cinzento, cinzento, cinzento, brilho)
# letra H - triângulo 1
# prefixos h v
# prefixo h: da letra H; prefixo v: de vértice
h1_v1 = Ponto3D(-2.5, 0.0, 0.0)
h1_v2 = Ponto3D(-2.5, 3.0, 0.0)
h1_v3 = Ponto3D(-3.5, 1.5, 0.0)
h1 = FaceTriangular(h1_v1, h1_v2, h1_v3, cor_letras)

# letra L - triângulo 2
h2_v1 = Ponto3D(-4.5, 0.0, 0.0)
h2_v2 = Ponto3D(-3.5, 1.5, 0.0)
h2_v3 = Ponto3D(-4.5, 3.0, 0.0)
h2 = FaceTriangular(h2_v1, h2_v2, h2_v3, cor_letras)

# letra O - triângulo 1
o1_v1 = Ponto3D(-1.25, 0.0, 0.0)
o1_v2 = Ponto3D(-0.25, 1.5, 0.0)
o1_v3 = Ponto3D(-2.25, 1.5, 0.0)
o1 = FaceTriangular(o1_v1, o1_v2, o1_v3, cor_letras)

# letra L - triângulo 2
o2_v1 = Ponto3D(-0.25, 1.5, 0.0)
o2_v2 = Ponto3D(-1.25, 3.0, 0.0)
o2_v3 = Ponto3D(-2.25, 1.5, 0.0)
o2 = FaceTriangular(o2_v1, o2_v2, o2_v3, cor_letras)

# letra M - triângulo 1
m1_v1 = Ponto3D(0.0, 0.0, 0.0)
m1_v2 = Ponto3D(0.75, 0.0, 0.0)
m1_v3 = Ponto3D(0.0, 3.0, 0.0)
m1 = FaceTriangular(m1_v1, m1_v2, m1_v3, cor_letras)
# letra M - triângulo 2
m2_v1 = Ponto3D(1.0, 1.5, 0.0)
m2_v2 = Ponto3D(2.0, 3.0, 0.0)
m2_v3 = Ponto3D(0.0, 3.0, 0.0)
m2 = FaceTriangular(m2_v1, m2_v2, m2_v3, cor_letras)
# letra M - triângulo 3
m3_v1 = Ponto3D(1.25, 0.0, 0.0)
m3_v2 = Ponto3D(2.0, 0.0, 0.0)
m3_v3 = Ponto3D(2.0, 3.0, 0.0)
m3 = FaceTriangular(m3_v1, m3_v2, m3_v3, cor_letras)

# letra E - triângulo 1
e1_v1 = Ponto3D(2.25, 2.0, 0.0)
e1_v2 = Ponto3D(4.25, 3.0, 0.0)
e1_v3 = Ponto3D(2.25, 3.0, 0.0)
e1 = FaceTriangular(e1_v1, e1_v2, e1_v3, cor_letras)
# letra E - triângulo 2
e2_v1 = Ponto3D(2.25, 1.0, 0.0)
e2_v2 = Ponto3D(4.25, 1.5, 0.0)
e2_v3 = Ponto3D(2.25, 2.0, 0.0)
e2 = FaceTriangular(e2_v1, e2_v2, e2_v3, cor_letras)
# letra E - triângulo 3
e3_v1 = Ponto3D(2.25, 0.0, 0.0)
e3_v2 = Ponto3D(4.25, 0.0, 0.0)
e3_v3 = Ponto3D(2.25, 1.0, 0.0)
e3 = FaceTriangular(e3_v1, e3_v2, e3_v3, cor_letras)

# parede de fundo
longe = 10.0**4
fundo_v1 = Ponto3D(longe, longe, -2.0)
fundo_v2 = Ponto3D(-longe, longe, -2.0)
fundo_v3 = Ponto3D(0.0, -longe, -2.0)
fundo = FaceTriangular(fundo_v1, fundo_v2, fundo_v3, cor_chao)

# chão
chao_v1 = Ponto3D(longe, -2.0, -longe)
chao_v2 = Ponto3D(-longe, -2.0, -longe)
chao_v3 = Ponto3D(0.0, -2.0, longe)
chao = FaceTriangular(chao_v1, chao_v2, chao_v3, cor_chao)

#teste translacao deslocar H uma unidade para a esquerda e desclocar E uma unidade para a direita
#h1 = transformacao.transforma_face_triangular(h1)
#h2 = transformacao.transforma_face_triangular(h2)
#e1 = transformacao.transforma_face_triangular(e1)
#e2 = transformacao.transforma_face_triangular(e2)
#e3 = transformacao.transforma_face_triangular(e3)

#teste escalamento encolher 20% as letras O e M
#o1 = transformacao.transforma_face_triangular(o1)
#o2 = transformacao.transforma_face_triangular(o2)
#m1 = transformacao.transforma_face_triangular(m1)
#m2 = transformacao.transforma_face_triangular(m2)
#m3 = transformacao.transforma_face_triangular(m3)

#teste shearing com shear de 0.3
#h1 = transformacao.transforma_face_triangular(h1)
#h2 = transformacao.transforma_face_triangular(h2)
#o1 = transformacao.transforma_face_triangular(o1)
#o2 = transformacao.transforma_face_triangular(o2)
#m1 = transformacao.transforma_face_triangular(m1)
#m2 = transformacao.transforma_face_triangular(m2)
#m3 = transformacao.transforma_face_triangular(m3)
#e1 = transformacao.transforma_face_triangular(e1)
#e2 = transformacao.transforma_face_triangular(e2)
#e3 = transformacao.transforma_face_triangular(e3)

#teste rotacao_z rodar a imagem 30º do eixo positivo dos xx para o dos yy
#h1 = transformacao.transforma_face_triangular(h1)
#h2 = transformacao.transforma_face_triangular(h2)
#o1 = transformacao.transforma_face_triangular(o1)
#o2 = transformacao.transforma_face_triangular(o2)
#m1 = transformacao.transforma_face_triangular(m1)
#m2 = transformacao.transforma_face_triangular(m2)
#m3 = transformacao.transforma_face_triangular(m3)
#e1 = transformacao.transforma_face_triangular(e1)
#e2 = transformacao.transforma_face_triangular(e2)
#e3 = transformacao.transforma_face_triangular(e3)

#teste rotacao_y rodar a imagem 30º do eixo positivo dos xx para o dos zz
#h1 = transformacao.transforma_face_triangular(h1)
#h2 = transformacao.transforma_face_triangular(h2)
#o1 = transformacao.transforma_face_triangular(o1)
#o2 = transformacao.transforma_face_triangular(o2)
#m1 = transformacao.transforma_face_triangular(m1)
#m2 = transformacao.transforma_face_triangular(m2)
#m3 = transformacao.transforma_face_triangular(m3)
#e1 = transformacao.transforma_face_triangular(e1)
#e2 = transformacao.transforma_face_triangular(e2)
#e3 = transformacao.transforma_face_triangular(e3)

#teste rotacao_x rodar a imagem 30º do eixo positivo dos yy para o dos zz
#h1 = transformacao.transforma_face_triangular(h1)
#h2 = transformacao.transforma_face_triangular(h2)
#o1 = transformacao.transforma_face_triangular(o1)
#o2 = transformacao.transforma_face_triangular(o2)
#m1 = transformacao.transforma_face_triangular(m1)
#m2 = transformacao.transforma_face_triangular(m2)
#m3 = transformacao.transforma_face_triangular(m3)
#e1 = transformacao.transforma_face_triangular(e1)
#e2 = transformacao.transforma_face_triangular(e2)
#e3 = transformacao.transforma_face_triangular(e3)


lista_faces = [h1, h2, o1, o2, m1, m2, m3, e1, e2, e3, fundo, chao]
# lista de luzes
luz1_posicao = Ponto3D(-3.0, 3.0, 1.5)
luz2_posicao = Ponto3D( 3.0, 7.0, -1.9)
luz1 = LuzPontual(luz1_posicao, branco, branco, branco)
luz2 = LuzPontual(luz2_posicao, branco, branco, branco)
lista_luzes = [luz1, luz2]
# a câmara
camara_posicao = Ponto3D(0.0, 0.0, 5.0)
camara_olhar_para = Ponto3D(0.0, 0.0, 0.0)
camara_vertical = Vetor3D(0.0, 1.0, 0.0)
camara_distancia_olho_plano_projecao = 3.0
camara_largura_retangulo_projecao = 8.0
camara_altura_retangulo_projecao = 6.0
camara_resolucao_horizontal = 320
camara_resolucao_vertical = 240
camara_resolucao_horizontal = 100
camara_resolucao_vertical = 80
camara = Camara(camara_posicao,
                camara_olhar_para,
                camara_vertical,
                camara_distancia_olho_plano_projecao,
                camara_largura_retangulo_projecao,
                camara_altura_retangulo_projecao,
                camara_resolucao_horizontal,
                camara_resolucao_vertical)
cor_fundo = preto
ray_tracer = RayTracer(lista_faces, lista_luzes, camara, cor_fundo)

print(ray_tracer)
imagem = ray_tracer.renderiza()

#original
#imagem.guardar_como_ppm("projeto_imagem.ppm")

#teste translacao  
#imagem.guardar_como_ppm("projeto_imagem1.ppm")

#teste escalamento  
#imagem.guardar_como_ppm("projeto_imagem2.ppm")

#teste shearing 
#imagem.guardar_como_ppm("projeto_imagem3.ppm")

#teste rotacao_z  
#imagem.guardar_como_ppm("projeto_imagem4.ppm")

#teste rotacao_y 
#imagem.guardar_como_ppm("projeto_imagem5.ppm")

#teste rotacao_x  
#imagem.guardar_como_ppm("projeto_imagem6.ppm")
