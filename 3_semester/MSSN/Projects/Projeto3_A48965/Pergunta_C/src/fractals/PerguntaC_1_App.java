package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaC_1_App implements IProcessingApp {

    private double[] window = {-15, 15, 0, 15};
    private float[] viewport = {0f, 0f, 1f, 1f};
    private SubPlot plt;
    private List<Tree> forest;
    private int choice = 1;


    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window,viewport, p.width, p.height);
        forest = new ArrayList<Tree>();
        System.out.println("Podemos escolher com os números 1 a 4 qual Turtle queremos exibir\n" +
                "Com a tecla 'r' limpamos o ecra");
    }

    @Override
    public void draw(PApplet p, float dt) {
        float[] bb = plt.getBoundingBox();
        p.rect(bb[0], bb[1], bb[2], bb[3]);

        for (Tree tree : forest) {
            tree.grow(dt);
            tree.display(p, plt);
        }

    }

    @Override
    public void mousePressed(PApplet p) {
        double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
        PVector pos = new PVector((float) w[0], (float) w[1]);
        Tree tree = null;

        //utilizando os números de 1 a 4 podemos escolher que Turtle usar
        switch (choice){
            case 1:
                Rule[] rules = new Rule[1];
                rules[0] = new Rule('F',"F+F-F-F+F" );
                tree = new Tree("FF+FF+FF+FF", rules, pos, .4f, PApplet.radians(90f),
                        4, 0.4f, 1f, p);
                break;
            case 2:
                rules = new Rule[1];
                rules[0] = new Rule('F',"F-F++F-F");
                tree = new Tree("F++F++F", rules, pos, .4f, PApplet.radians(60f),
                        5, 0.4f, 1f, p);
                break;
            case 3:
                rules = new Rule[2];
                rules[0] = new Rule('X',"X[-FFF][+FFF]FX");
                rules[1] = new Rule('Y',"YFX[+Y][-Y]");
                tree = new Tree("Y", rules, pos, .4f, PApplet.radians(25.7f),
                        5, 0.6f, 1f, p);
                break;
            case 4:
                rules = new Rule[1];
                rules[0] = new Rule('F',"F[+FF][-FF]F[-F][+F]F");
                tree = new Tree("F", rules, pos, .4f, PApplet.radians(35f),
                        4, 0.5f, 1f, p);
                break;
        }

        forest.add(tree);
    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == '1' )
            choice=1;
        if (p.key == '2' )
            choice=2;
        if (p.key == '3' )
            choice=3;
        if (p.key == '4' )
            choice=4;
        System.out.println("Escolheu a turtle " + choice);
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
}
