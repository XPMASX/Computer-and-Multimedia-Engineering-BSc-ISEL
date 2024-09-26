package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Prey extends Animal{

    private PApplet parent;
    private SubPlot plt;

    public Prey(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, color, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.PREY_COLOR[0],
                WorldConstants.PREY_COLOR[1],
                WorldConstants.PREY_COLOR[2]));
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    public Prey(Prey prey, boolean mutate,int mode, PApplet parent, SubPlot plt) {
        super(prey, mutate, mode, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.PREY_COLOR[0],
                WorldConstants.PREY_COLOR[1],
                WorldConstants.PREY_COLOR[2]));
        energy = WorldConstants.INI_PREY_ENERGY;
    }

    @Override
    public Animal reproduce(boolean mutate, int mode) {
        Animal child = null;
        if (energy > WorldConstants.PREY_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_PREY_ENERGY;
            child = new Prey(this, mutate, mode, parent, plt);
            if (mutate) child.mutateBehaviours();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
        if (patch.getState() == WorldConstants.PatchType.FOOD.ordinal()){
            energy += WorldConstants.ENERGY_FROM_PLANT;
            patch.setFertile();
        }
    }


}
