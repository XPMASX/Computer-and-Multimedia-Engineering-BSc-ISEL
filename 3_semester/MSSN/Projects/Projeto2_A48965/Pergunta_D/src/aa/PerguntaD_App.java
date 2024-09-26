package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import simple_subplot.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaD_App implements IProcessingApp {

    private Boid wander, seeker, pursuiter, patrol, arrive;
    private double[] window = {-10, 10, -10,10};
    private float[] view1 = {0.02f, 0.51f, 0.47f, 0.47f};
    private float[] view2 = {0.02f, 0.02f, 0.47f, 0.47f};
    private float[] view3 = {0.51f, 0.51f, 0.47f, 0.47f};
    private float[] view4 = {0.51f, 0.02f, 0.47f, 0.47f};
    private Body target_seeker, target_arrive, p1, p2, p3;
    private int ix = 0;
    private float speed = 0.5f;

    private SubPlot plt1, plt2, plt3, plt4;

    @Override
    public void setup(PApplet p) {

        System.out.println("Na primeira janela(Branca) temos o Comportamento" +
                " Patrol onde o Boid percorre ciclicamente os pontos de cor " +
                "vermelha, amarela e verde respetivamente; \n" +
                "Na segunda janela(verde) temos o Boid com acelerador e travão" +
                " em que o Boid persegue um alvo controlado pela posição do rato" +
                " sendo possível mudar velocidade do Boid com as teclas 'w'" +
                " para aumentar e 's' para diminuir; \n" +
                "Na terceira janela(amarela) temos o Comportamento Wander" +
                " em que o Boid verde vagueia pelo mundo sem objetivo e o " +
                "Boid vermelho persegue-o; \n" +
                "Na quarta janela(roxa) temos o Comportamento Arrive " +
                "em que o Boid persegue o alvo controlado pela posição do rato" +
                " mas diminui gradualmente a sua velocidade à medida que se" +
                "aproxima do alvo. \n");

        plt1 = new SubPlot(window, view1, p.width, p.height);
        plt2 = new SubPlot(window, view2, p.width, p.height);
        plt3 = new SubPlot(window, view3, p.width, p.height);
        plt4 = new SubPlot(window, view4, p.width, p.height);

        List<Body> allTrackingBodies = new ArrayList<Body>();


        //Patrol
        ArrayList<PVector> path = new ArrayList<PVector>();
        path.add(new PVector(-5,5));
        path.add(new PVector(0,-8));
        path.add(new PVector(8,-7));

        p1 = new Body(new PVector(-5,5), new PVector(), 1f, .3f, p.color(255,0,0));
        p2 = new Body(new PVector(0,-8), new PVector(), 1f, .3f, p.color(255,255,0));
        p3 = new Body(new PVector(8,-7), new PVector(), 1f, .3f, p.color(0,255,0));

        patrol = new Boid(new PVector(p.random((float) window[0], (float) window[1]),p.random((float) window[2], (float) window[3])),
                0.3f,0.4f, p.color(75,0,130), p,plt1);
        patrol.addBehaviour(new Patrol(1f, path));


        //Arrive
        arrive = new Boid(new PVector(p.random((float) window[0], (float) window[1]),p.random((float) window[2], (float) window[3])),
                0.3f,0.4f, p.color(75,0,130), p, plt4);
        arrive.addBehaviour(new Arrive(1f));
        target_arrive = new Body(new PVector(), new PVector(), 1f, .3f, p.color(0));
        allTrackingBodies.add(target_arrive);
        arrive.setEye(new Eye(arrive, allTrackingBodies));


        //Wander
        wander = new Boid(new PVector(p.random((float) window[0], (float) window[1]),
                p.random((float) window[2], (float) window[3])), 0.05f, 0.5f,
                p.color(0,255,0), p, plt2);
        wander.addBehaviour(new Wander(1f));

        pursuiter = new Boid(new PVector(p.random((float) window[0], (float) window[1]),p.random((float) window[2], (float) window[3])),
                0.5f,0.5f, p.color(255,0,0), p, plt2);
        pursuiter.addBehaviour(new Pursuit(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(wander);
        pursuiter.setEye(new Eye(pursuiter, allTrackingBodies));

        //Seeker
        target_seeker = new Body(new PVector(), new PVector(), 1f, .3f, p.color(0));
        seeker = new Boid(new PVector(p.random((float) window[0], (float) window[1]),p.random((float) window[2], (float) window[3])),
                speed,0.5f, p.color(0,0,255), p, plt3);
        seeker.addBehaviour(new Seek(1f));
        seeker.addBehaviour(new Brake(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target_seeker);
        seeker.setEye(new Eye(seeker, allTrackingBodies));
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);
        float[] bb = plt1.getBoundingBox();
        p.fill(255, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt2.getBoundingBox();
        p.fill(190,170,45, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        bb = plt3.getBoundingBox();
        p.fill(120,170,150, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);


        bb = plt4.getBoundingBox();
        p.fill(30,30,200, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);


        wander.applyBehaviours(dt);
        pursuiter.applyBehaviours(dt);
        seeker.applyBehaviour(ix, dt);
        patrol.applyBehaviours(dt);
        arrive.applyBehaviours(dt);

        wander.display(p, plt2);
        pursuiter.display(p, plt2);
        seeker.display(p, plt3);
        p1.display(p, plt1);
        p2.display(p, plt1);
        p3.display(p, plt1);
        patrol.display(p, plt1);

        target_seeker.display(p, plt3);
        arrive.display(p, plt4);
        target_arrive.display(p, plt4);

    }

    @Override
    public void mousePressed(PApplet p) {
        if (plt3.isInside(p.mouseX, p.mouseY))
        {
            double[] w = plt3.getWorldCoord(p.mouseX, p.mouseY);
            target_seeker.setPos(new PVector((float) w[0], (float) w[1]));
        }
        if (plt4.isInside(p.mouseX, p.mouseY))
        {
            double[] w = plt4.getWorldCoord(p.mouseX, p.mouseY);
            target_arrive.setPos(new PVector((float) w[0], (float) w[1]));
        }

    }

    @Override
    public void keyPressed(PApplet p) {

        if (p.key == 'w')
        {
            seeker.dna.maxSpeed+=0.5;
            System.out.println("Velocidade = " + String.format("%.2f", seeker.dna.maxSpeed));
        }

        if (p.key == 's')
        {
            seeker.dna.maxSpeed-=0.5;
            System.out.println("Velocidade = " + String.format("%.2f", seeker.dna.maxSpeed));
        }

        if (p.key == ' ')
        {
            ix = (ix + 1) % 2;
        }

    }

}
