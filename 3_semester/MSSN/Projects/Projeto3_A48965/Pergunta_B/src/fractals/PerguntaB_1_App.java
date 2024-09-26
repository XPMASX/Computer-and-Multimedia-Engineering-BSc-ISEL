package fractals;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaB_1_App implements IProcessingApp {

    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private List<Vertice> vertices;
    private List<Vertice> pontos;

    @Override
    public void setup(PApplet p)
    {
        plt = new SubPlot(window,viewport, p.width, p.height);
        vertices = new ArrayList<Vertice>();
        pontos = new ArrayList<Vertice>();
        vertices.add(new Vertice(new PVector(0,13), p.color(255,0,0)));
        vertices.add(new Vertice(new PVector(5,5), p.color(0,255,0)));
        vertices.add(new Vertice(new PVector(-5,5), p.color(0,0,255)));
        System.out.println("Jogo do caos simples\n" +
                "Se pressionar a tecla 'r' o programa recomeça.");
    }

    @Override
    public void draw(PApplet p, float dt)
    {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1], bb[2], bb[3]);
        p.strokeWeight(1);
        p.beginShape();

        for (Vertice vertice: vertices) {
            float[] pp = plt.getPixelCoord(vertice.getCoord().x, vertice.getCoord().y);
            p.vertex(pp[0], pp[1]);
        }
        p.endShape(PConstants.CLOSE);
        //loops para os displays dos vertices e dos pontos respetivamente
        for (Vertice vertice: vertices) {
            vertice.display(p, plt);
        }
        for (Vertice ponto: pontos) {
            ponto.display(p, plt);
        }
        JogoDoCaos(p);
    }

    @Override
    public void mousePressed(PApplet p)
    {

    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == 'r') {
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
            if ((vertices.get(i).getCoord().y < pos.y && vertices.get(j).getCoord().y >= pos.y ||
                    vertices.get(j).getCoord().y < pos.y && vertices.get(i).getCoord().y >= pos.y) &&
                    (vertices.get(i).getCoord().x <= pos.x || vertices.get(j).getCoord().x <= pos.x)) {

                oddNodes^=(vertices.get(i).getCoord().x + (pos.y- vertices.get(i).getCoord().y)/
                        (vertices.get(j).getCoord().y - vertices.get(i).getCoord().y)*
                        (vertices.get(j).getCoord().x- vertices.get(i).getCoord().x)<pos.x);
            }
            j=i;
        }
        return oddNodes;
    }

    public void JogoDoCaos(PApplet p)
    {
        int rando = (int) p.random(vertices.size());
        PVector T = new PVector(vertices.get(rando).getCoord().x, vertices.get(rando).getCoord().y);
        PVector X = new PVector(p.random((float) window[0], (float) window[1]),
                p.random((float) window[2], (float) window[3]));
        //Enquanto o ponto criado não tiver dentro do poligono criamos um novo
        while (!isInsidePolygon(X))
        {
            X = new PVector(p.random((float) window[0], (float) window[1]),
                    p.random((float) window[2], (float) window[3]));
        }
        //Com o ponto criado fazemos a conta para descobrir a mediatriz
        PVector y = PVector.mult((X.sub(T)),0.5f);
        T = (T.add(y));

        pontos.add(new Vertice(T, vertices.get(rando).getColor()));

    }
}
