package aa;

import physics.Body;
import physics.PSControl;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import simple_subplot.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaE_App implements IProcessingApp {

    private Boid leader;
    private Boid predator;
    private Flock flock;
    private float[] sacWeights = {1f,1f,1f};
    private double[] window = {-10, 10, -10,10};
    private float[] viewport = {0, 0, 1, 1};
    private Body target_seeker;
    private int ix = 0;
    private final Seek seek = new Seek(2.5f);
    private List<ParticleSystem> explosion;
    private float[] velParams = {PApplet.radians(180), PApplet.radians(360), 1, 3};
    private float[] lifetimeParams = {0.5f, 0.8f};
    private float[] radiusParams = {0.1f, 0.3f};
    private float flow = 100;

    private long start;
    private long end;


    private SubPlot plt;
    private boolean debug = false;

    @Override
    public void setup(PApplet p) {

        System.out.println("JOGO: RUN!!! \n" +
                "O objetivo do jogo é manter vivo durante o maior tempo possível " +
                "pelo menos um dos teus seguidores enquanto te desvias das zonas mortais" +
                " e do predador\n" +
                "Assim que um dos teus seguidores é tocado por algum destes perigos ele explode.\n" +
                "Tens 50 seguidores eles só te seguirão se tiveres no seu raio de visão, se esse for o caso eles mudam de cor para amarelo\n" +
                "Boa sorte!!! \n" );

        plt = new SubPlot(window, viewport, p.width, p.height);

        List<Body> allTrackingBodies = new ArrayList<Body>();

        explosion = new ArrayList<ParticleSystem>();

        predator = new Boid(new PVector(p.random((float) window[0], (float) window[1]),p.random((float) window[2], (float) window[3])),
                0.5f,0.4f, p.color(255,0,0), p, plt);
        predator.addBehaviour(new Pursuit(1f));


        flock = new Flock(50, .1f, 0.3f, p.color(0, 100, 200),
               sacWeights,p, plt);
        leader = new Boid(new PVector(),1f,.3f,p.color(0, 200, 0),p,plt);

        target_seeker = new Body(new PVector(), new PVector(), 1f, .1f, p.color(0,200,0));

        leader.dna.maxSpeed= leader.dna.maxSpeed*1.2f;


        leader.addBehaviour(new Seek(1f));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target_seeker);
        leader.setEye(new Eye(leader, allTrackingBodies));

        for (int i = 0; i < flock.getNboids(); i++) {
            flock.getBoid(i).dna.visionAngle = (float) Math.PI;
            flock.getBoid(i).eye.target = leader;
        }

         start = System.currentTimeMillis();

    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(200);
        float[] bb = plt.getBoundingBox();
        p.fill(255, 64);
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        target_seeker.setPos(new PVector((float) w[0], (float) w[1]));


        ArrayList<Body> allTrackingBodies = new ArrayList<Body>();

        //Percorre os boids no flock
        for (int i = 0; i < flock.getNboids(); i++) {
            flock.getBoid(i).eye.look();
            allTrackingBodies.add(flock.getBoid(i));
            predator.setEye(new Eye(predator, allTrackingBodies));

            //se o boid do flock vir o lider persegue-o e muda de cor
            if (flock.getBoid(i).eye.farSight(leader.getPos()) ) {
                if (flock.getBoid(i).count!=1) {
                    flock.getBoid(i).addBehaviour(seek);
                    flock.getBoid(i).count++;
                }
                flock.getBoid(i).setShape(p,plt,0.3f,p.color(220,220,0));
            }
            //se não remove o comportamento de perseguição
            else {
                flock.getBoid(i).removeBehaviour(seek);
                flock.getBoid(i).count=0;
                flock.getBoid(i).setShape(p,plt,0.3f,p.color(0,100,200));
            }
            //se o boid do flock tocar num ponto vermelho ou for apanhado pelo predador explode e removemos do flock
            if (flock.getBoid(i).eye.nearSight(new PVector(-6, 6)) ||
                    flock.getBoid(i).eye.nearSight(new PVector(-6, -6)) ||
                    flock.getBoid(i).eye.nearSight(new PVector(6, -6)) ||
                    flock.getBoid(i).eye.nearSight(new PVector(6, 6)) ||
                    flock.getBoid(i).eye.nearSight(predator.getPos())) {
                int color = p.color(p.color(255,91,20));
                PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
                ParticleSystem ps = new ParticleSystem(new PVector(flock.getBoid(i).getPos().x,flock.getBoid(i).getPos().y),
                        new PVector(),1f, 1f, psc);
                explosion.add(ps);
                flock.removeBoid(i);
                System.out.println(flock.getNboids() + " Boids Restantes");
            }
        }

        //pontos de morte
        float[] pp = plt.getPixelCoord(-6, 6);
        float[] r = plt.getDimPixel(0.3, 0.3);
        p.noStroke();
        p.fill(p.color(255,0,0));
        p.circle(pp[0], pp[1],r[0]);
        pp = plt.getPixelCoord(-6, -6);
        p.circle(pp[0], pp[1],r[0]);
        pp = plt.getPixelCoord(6, 6);
        p.circle(pp[0], pp[1],r[0]);
        pp = plt.getPixelCoord(6, -6);
        p.circle(pp[0], pp[1],r[0]);


        //explosao
        for (int i = 0; i< explosion.size(); i++)
        {
            explosion.get(i).move(dt);

            explosion.get(i).display(p, plt);
            if (explosion.get(i).timer>0.8f) {
                explosion.remove(explosion.get(i));
            }
        }

        //final do jogo
        if (flock.getNboids()==0)
        {
            long end = System.currentTimeMillis();
            long elapsedTime = end - start;
            long minutos = (elapsedTime/1000)/60;
            long segundos = (elapsedTime/1000)%60;
            System.out.printf("Sobreviveu %d minutos e %d segundos",minutos, segundos);
            System.exit(0);
        }

        flock.applyBehaviour(dt);
        predator.applyBehaviours(dt);


        flock.display(p, plt);
        predator.display(p,plt);
        leader.applyBehaviours(dt);
        if (debug)
            leader.getEye().display(p, plt);
        leader.display(p,plt);
        target_seeker.display(p,plt);


    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

        if (p.key == 'd')
            debug=!debug;

    }

}
