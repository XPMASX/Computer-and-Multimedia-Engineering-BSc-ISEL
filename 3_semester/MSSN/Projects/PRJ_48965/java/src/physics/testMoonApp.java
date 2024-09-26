package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

public class testMoonApp implements IProcessingApp {

    //Dobro do raio de jupiter * 10

    private float sunMass = 1.989e30f;



    private float earthMass = 1.5e29f;
    private float distEarthSun = 1.496e11f;
    private float earthSpeed = 3e4f;

    private float moonMass = 1e2f;
    private float distMoonSun = (float) (1.6e11f);
    private float moonSpeed = 10e3f;


    private float[] viewport = {0.2f,0.2f,0.6f,0.6f};
    private double[] window = {-1.2*distEarthSun, 1.2*distEarthSun,
            -1.2*distEarthSun, 1.2*distEarthSun};

    private SubPlot plt;
    private Body sun, earth, mercury, venus, mars, jupiter, saturn, uranus, neptune, moon;


    private float speedUp = 60 * 60 * 24 * 30;

    @Override
    public void setup(PApplet p)
    {
        plt = new SubPlot(window, viewport, p.width, p.height);
        sun = new Body(new PVector(), new PVector(), sunMass, distEarthSun/10, p.color(255,128,0));
        earth = new Body(new PVector(0 ,distEarthSun), new PVector(earthSpeed,0), earthMass,
                (float) (distEarthSun/60), p.color(0,180,120));
        moon = new Body(new PVector(0 ,distMoonSun), new PVector(moonSpeed,0), moonMass,
                distMoonSun/120, p.color(255,0,120));

    }

    @Override
    public void draw(PApplet p, float dt)
    {
        float[]pp = plt.getBoundingBox();
        p.fill(53,57,53,50);
        p.rect(pp[0], pp[1], pp[2], pp[3]);

        sun.display(p, plt);

        PVector f = sun.attraction(earth);
        earth.applyForce(f);

        earth.move(dt*speedUp);
        earth.display(p, plt);

        PVector f_moon = earth.attraction(moon);
        moon.applyForce(f_moon.mult(0.45f));
        PVector f_moon2 = sun.attraction(moon);
        moon.applyForce(f_moon2);


        moon.move(dt*speedUp);
        moon.display(p, plt);

    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
