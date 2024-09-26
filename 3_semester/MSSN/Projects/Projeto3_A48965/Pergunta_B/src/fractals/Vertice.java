package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Vertice {

    private PVector coord;
    private int color;

    public Vertice(PVector coord, int color) {
        this.coord = coord;
        this.color = color;
    }

    public PVector getCoord() {
        return coord;
    }

    public void setCoord(PVector coord) {
        this.coord = coord;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void display(PApplet p, SubPlot plt)
    {
        p.pushStyle();
        float[] pp = plt.getPixelCoord(coord.x, coord.y);
        float[] r = plt.getVectorCoord(3, 3);
        p.noStroke();
        p.fill(color);
        p.circle(pp[0], pp[1], 6);
        p.popStyle();
    }
}
