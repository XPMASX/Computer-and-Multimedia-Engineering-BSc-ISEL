package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Predator extends Animal{

    private PApplet parent;
    private SubPlot plt;

    public Predator(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, color, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.PREDATOR_COLOR[0],
                WorldConstants.PREDATOR_COLOR[1],
                WorldConstants.PREDATOR_COLOR[2]));
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    public Predator(Predator predator, boolean mutate, int mode, PApplet parent, SubPlot plt) {
        super(predator, mutate, mode, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.PREDATOR_COLOR[0],
                WorldConstants.PREDATOR_COLOR[1],
                WorldConstants.PREDATOR_COLOR[2]));
        energy = WorldConstants.INI_PREDATOR_ENERGY;
    }

    @Override
    public Animal reproduce(boolean mutate, int mode) {
        Animal child = null;
        if (energy > WorldConstants.PREDATOR_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_PREDATOR_ENERGY;
            child = new Predator(this, mutate, mode, parent, plt);
            if (mutate) child.mutateBehaviours();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {

    }

}
