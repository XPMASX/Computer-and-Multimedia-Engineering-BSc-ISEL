package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Tree {
    private LSystem lsys;
    private Turtle turtle;
    private PVector position;


    private float lenght;
    private float growthRate;


    private int numberOfSeasonsToGrow;
    private float scalingFactor;
    private float intervalBetweenSeasons;
    private float now;
    private float nextSeasonTime;
    private PApplet p;


    public Tree(String axiom, Rule[] rules, PVector position, float referencelenght, float angle, int niter,
                float scalingFactor, float intervalBetweenSeasons, PApplet p) {

        lsys = new LSystem(axiom,rules);

        lenght = 0;
        growthRate = referencelenght/intervalBetweenSeasons;
        turtle = new Turtle(0, angle);

        this.position = position;
        numberOfSeasonsToGrow = niter;
        this.scalingFactor = scalingFactor;
        this.intervalBetweenSeasons = intervalBetweenSeasons;
        now = p.millis()/1000f;
        nextSeasonTime = now + intervalBetweenSeasons;
        this.p = p;
    }

    public void grow(float dt)
    {
        now +=dt;
        if (now < nextSeasonTime)
        {
            lenght += growthRate * dt;
            turtle.setLength(lenght);
        }
        else if (now > nextSeasonTime && lsys.getGeneration() < numberOfSeasonsToGrow) {
            lsys.nextGeneration();
            lenght *= scalingFactor;
            growthRate *= scalingFactor;
            turtle.setLength(lenght);
            nextSeasonTime = now + intervalBetweenSeasons;
        }
    }

    public void display(PApplet p, SubPlot plt)
    {
        p.pushMatrix();
        turtle.setPose(position, (float) Math.PI/2, p, plt);
        turtle.render(lsys, p, plt);
        p.popMatrix();
    }
}
