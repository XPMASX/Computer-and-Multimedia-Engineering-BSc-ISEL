package aa;

import physics.Body;
import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class BoidApp implements IProcessingApp {

    private Boid b;
    private double[] window = {-10, 10, -10,10};
    private float[] viewport = {0, 0, 1, 1};
    private SubPlot plt;
    private Body target;
    private List<Body> allTrackingBodies;
    private int index = 0;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        b = new Boid(new PVector(), 1, 0.5f,
                p.color(0), p, plt);
        b.addBehaviour(new Seek(1f));
        b.addBehaviour(new Flee(1f));
        b.addBehaviour(new Wander(1f));

        target = new Body(new PVector(), new PVector(), 1f, 0.2f,
                p.color(255,0,0));
        allTrackingBodies = new ArrayList<Body>();
        allTrackingBodies.add(target);
        Eye eye = new Eye(b, allTrackingBodies);
        b.setEye(eye);
    }

    @Override
    public void draw(PApplet p, float dt) {
        p.background(255);

        b.applyBehaviour(index, dt);

        b.display(p, plt);
    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);
        target.setPos(new PVector((float) ww[0], (float) ww[1]));

    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == '1') {
            index = 0;
            System.out.println("Seek");
        }
        if (p.key == '2'){
            index = 1;
            System.out.println("Flee");
        }
        if (p.key == '3'){
            index = 2;
            System.out.println("Wander");
        }

    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
