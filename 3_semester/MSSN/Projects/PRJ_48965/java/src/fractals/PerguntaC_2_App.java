package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.Random;

public class PerguntaC_2_App implements IProcessingApp {

    private LSystem lsys;
    private final double[] window = {-15, 15, 0, 15};
    private final float[] viewport = {0.1f, 0.1f, 0.8f, 0.8f};
    private final PVector startingPos = new PVector();
    private SubPlot plt;
    private Turtle turtle;
    private int counter = 0;
    private float scale = 0.5f;
    private int randos[];

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window,viewport, p.width, p.height);
        Rule[] rules = new Rule[2];
        rules[0] = new Rule('F',"G[+F]-F");
        rules[1] = new Rule('G',"GG");

        lsys = new LSystem("F", rules);
        turtle = new Turtle(5, PApplet.radians(35f));

    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1], bb[2], bb[3]);
        turtle.setPose(startingPos, PApplet.radians(90), p, plt);
        turtle.render2(lsys, counter,randos, p, plt);

    }

    @Override
    public void mousePressed(PApplet p) {
        counter++;
        System.out.println(lsys.getGeneration());
        lsys.nextGeneration();
        turtle.scaling(scale+0.1f);
        randos = get_Rand();
    }

    private int[] get_Rand()
    {
        int[] randos = new int[lsys.getSequence().length()];
        for (int i = 0; i < lsys.getSequence().length(); i++)
        {
            Random r = new Random();
            int randomInt = r.nextInt(100) + 1;
            randos[i] = randomInt;
        }
        return randos;
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
