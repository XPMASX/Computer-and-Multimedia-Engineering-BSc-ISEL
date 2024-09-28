from matriz_48965 import Matriz
from ponto_48965 import Ponto3D
from vetor_48965 import Vetor3D


class Camara:

    def __init__(self,
                  posicao,
                  olhar_para,
                  vertical,
                  distancia_olho_plano_projecao,
                  largura_retangulo_projecao,
                  altura_retangulo_projecao,
                  resolucao_horizontal,
                  resolucao_vertical):

        self.posicao                        = posicao
        self.olhar_para                     = olhar_para
        self.vertical                       = vertical
        self.distancia_olho_plano_projecao  = distancia_olho_plano_projecao
        self.largura_retangulo_projecao     = largura_retangulo_projecao
        self.altura_retangulo_projecao      = altura_retangulo_projecao
        self.resolucao_horizontal           = resolucao_horizontal
        self.resolucao_vertical             = resolucao_vertical

        eixo_z      = (olhar_para - posicao).versor()
        self.eixo_z = eixo_z

        eixo_y      = (vertical + eixo_z * (-1.0 * vertical.interno(eixo_z))).versor()
        self.eixo_y = eixo_y

        eixo_x      = eixo_z.externo(eixo_y)
        self.eixo_x = eixo_x

        incremento_horizontal = largura_retangulo_projecao / resolucao_horizontal
        self.incremento_horizontal = incremento_horizontal

        incremento_vertical = altura_retangulo_projecao / resolucao_vertical
        self.incremento_vertical = incremento_vertical

        canto_superior_esquerdo_x = -largura_retangulo_projecao / 2.0 + incremento_horizontal / 2.0
        self.canto_superior_esquerdo_x = canto_superior_esquerdo_x

        canto_superior_esquerdo_y = altura_retangulo_projecao / 2.0 - incremento_vertical / 2.0
        self.canto_superior_esquerdo_y  = canto_superior_esquerdo_y
        
        canto_superior_esquerdo_z = distancia_olho_plano_projecao
        self.canto_superior_esquerdo_z = canto_superior_esquerdo_z

        matriz = Matriz(4, 4)
        ex = [eixo_x.get_x(), eixo_x.get_y(), eixo_x.get_z(), 0.0]
        ey = [eixo_y.get_x(), eixo_y.get_y(), eixo_y.get_z(), 0.0]
        ez = [eixo_z.get_x(), eixo_z.get_y(), eixo_z.get_z(), 0.0]
        p  = [posicao.get_x(), posicao.get_y(), posicao.get_z(), 1.0]

        matriz.set_coluna(1, ex)
        matriz.set_coluna(2, ey)
        matriz.set_coluna(3, ez)
        matriz.set_coluna(4, p)

        self.matriz = matriz

    def __repr__(self):

        return 'Camara('  \
               + str(self.posicao) + ',\n' \
               + str(self.olhar_para) + ',\n' \
               + str(self.vertical) + ',\n' \
               + str(self.distancia_olho_plano_projecao) + ',\n' \
               + str(self.largura_retangulo_projecao) + ',\n' \
               + str(self.altura_retangulo_projecao) + ',\n' \
               + str(self.resolucao_horizontal) + ',\n' \
               + str(self.resolucao_vertical) + ',\n' \
               + str(self.eixo_x) + ',\n' \
               + str(self.eixo_y) + ',\n' \
               + str(self.eixo_z) + ',\n' \
               + str(self.incremento_horizontal) + ',\n' \
               + str(self.incremento_vertical) + ',\n' \
               + str(self.canto_superior_esquerdo_x) + ',\n' \
               + str(self.canto_superior_esquerdo_y) + ',\n' \
               + str(self.canto_superior_esquerdo_z) + ',\n' \
               + str(self.matriz) + ')' 


    def get_posicao(self):

        return self.posicao

    def get_resolucao_vertical(self):

        return self.resolucao_vertical

    def get_resolucao_horizontal(self):

        return self.resolucao_horizontal

    def get_pixel_local(self, linha, coluna):

        pixel_x = self.canto_superior_esquerdo_x + (coluna - 1) \
                  * self.incremento_horizontal

        pixel_y = self.canto_superior_esquerdo_y - (linha - 1) \
                  * self.incremento_horizontal


        pixel_z = self.canto_superior_esquerdo_z

        return Ponto3D(pixel_x, pixel_y, pixel_z)



    def local_para_global(self, ponto):

        mlocal = Matriz(4, 1)
        llocal = [ponto.get_x(), ponto.get_y(), ponto.get_z(), 1.0]
        mlocal.set_coluna(1, llocal)

        mglobal = self.matriz * mlocal
        
        global_w = mglobal.get_entrada(4, 1)
        global_x = mglobal.get_entrada(1, 1) / global_w
        global_y = mglobal.get_entrada(2, 1) / global_w
        global_z = mglobal.get_entrada(3, 1) / global_w

        return Ponto3D(global_x, global_y, global_z)

    def get_pixel_global(self, linha, coluna):

        pixel_local = self.get_pixel_local(linha, coluna)
        pixel_global = self.local_para_global(pixel_local)

        return pixel_global
    
# teste ao construtor
posicao = Ponto3D(0.0, 0.0, 3.0)
olhar_para = Ponto3D(0.0, 0.0, 0.0)
vertical = Vetor3D(0.0, 1.0, 0.0)
distancia_olho_plano_projecao = 2.0
largura_retangulo_projecao = 4.0
altura_retangulo_projecao = 2.0
resolucao_horizontal = 8
resolucao_vertical = 4
camara = Camara(posicao, olhar_para, vertical, distancia_olho_plano_projecao,
                largura_retangulo_projecao, altura_retangulo_projecao,
                resolucao_horizontal, resolucao_vertical)

print(camara)