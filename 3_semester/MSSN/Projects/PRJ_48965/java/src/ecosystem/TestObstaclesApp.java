package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestObstaclesApp implements IProcessingApp {

    private SubPlot plt;
    private float[] viewport = {0f, 0f, 1f, 1f};
    private Terrain terrain;
    private Population population;

    private int mode;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
        terrain = new Terrain(p, plt);
        terrain.setStateColors(getColors(p));

        for (int i = 0; i < 2; i++) {
            terrain.majorityRule();
        }
        population = new Population(p, plt, terrain, mode);
    }

    @Override
    public void draw(PApplet p, float dt) {
        terrain.regenerate();
        population.update(dt, terrain, mode, p);

        terrain.display(p);
        population.display(p, plt, dt, false);

        System.out.println("numAnimals = " + population.getNumAnimals());
    }

    @Override
    public void mousePressed(PApplet p) {

    }

    @Override
    public void keyPressed(PApplet p) {

    }
    private int[] getColors(PApplet p)
    {
        int[] colors = new int[WorldConstants.NSTATES];
        for (int i = 0; i < WorldConstants.NSTATES; i++) {
            colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
                    WorldConstants.TERRAIN_COLORS[i][1],
                    WorldConstants.TERRAIN_COLORS[i][2]);
        }
        return colors;
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
