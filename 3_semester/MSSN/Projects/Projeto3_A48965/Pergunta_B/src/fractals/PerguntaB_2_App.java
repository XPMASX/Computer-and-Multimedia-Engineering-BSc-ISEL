package fractals;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaB_2_App implements IProcessingApp {

    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private List<Vertice> vertices;
    private List<Vertice> pontos;
    private boolean run = false;

    @Override
    public void setup(PApplet p)
    {
        plt = new SubPlot(window,viewport, p.width, p.height);
        vertices = new ArrayList<Vertice>();
        pontos = new ArrayList<Vertice>();
        System.out.println("Jogo do caos em que o utilizador, no inicio, pode escolher com um clique no lado esquerdo do rato" +
                "a posição onde quer adicionar um ponto. Quando terminar pressione a tecla 'espaço' para começar!\n" +
                "Se pressionar a tecla 'r' o programa recomeça.");
    }

    @Override
    public void draw(PApplet p, float dt)
    {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        p.strokeWeight(1);
        p.beginShape();
        if (run)
        {
            for (Vertice vertice : vertices) {
                float[] pp = plt.getPixelCoord(vertice.getCoord().x, vertice.getCoord().y);
                p.vertex(pp[0], pp[1]);
            }
            p.endShape(PConstants.CLOSE);

            for (Vertice ponto : pontos) {
                ponto.display(p, plt);
            }
            JogoDoCaos(p);
        }
        for (Vertice vertice: vertices) {
            vertice.display(p, plt);
        }
    }

    @Override
    public void mousePressed(PApplet p)
    {
        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float) w[0], (float) w[1]);
        if (!run)
            vertices.add(new Vertice(pos,p.color(p.random(255),p.random(255),p.random(255))));

    }

    @Override
    public void keyPressed(PApplet p)
    {
        if (p.key == ' ')
            run = true;

        if (p.key == 'r') {
            run = false;
            setup(p);
        }
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
    public boolean isInsidePolygon(PVector pos)
    {
        int i, j=vertices.size()-1;
        int sides = vertices.size();
        boolean oddNodes = false;
        //percorremos o numero de lados
        for (i=0; i<sides; i++) {
            if ((vertices.get(i).getCoord().y < pos.y && vertices.get(j).getCoord().y >= pos.y || vertices.get(j).getCoord().y < pos.y && vertices.get(i).getCoord().y >= pos.y)
                    && (vertices.get(i).getCoord().x <= pos.x || vertices.get(j).getCoord().x <= pos.x)) {
                oddNodes^=(vertices.get(i).getCoord().x + (pos.y- vertices.get(i).getCoord().y)/(vertices.get(j).getCoord().y - vertices.get(i).getCoord().y)*
                        (vertices.get(j).getCoord().x- vertices.get(i).getCoord().x)<pos.x);
            }
            j=i;
        }
        return oddNodes;
    }

    public void JogoDoCaos(PApplet p)
    {
        int rando = (int) p.random(vertices.size());
        PVector X = new PVector(vertices.get(rando).getCoord().x, vertices.get(rando).getCoord().y);
        PVector T = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
        //Enquanto o ponto criado não tiver dentro do poligono criamos um novo
        while (!isInsidePolygon(T))
        {
            T = new PVector(p.random((float) window[0], (float) window[1]), p.random((float) window[2], (float) window[3]));
        }
        //Com o ponto criado fazemos a conta para descobrir a mediatriz
        PVector y = PVector.mult((T.sub(X)),0.5f);
        X = (X.add(y));

        pontos.add(new Vertice(X, vertices.get(rando).getColor()));

    }
}
