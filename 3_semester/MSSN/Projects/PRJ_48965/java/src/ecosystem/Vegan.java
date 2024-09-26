package ecosystem;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Vegan extends Animal{

    private PApplet parent;
    private SubPlot plt;

    public Vegan(PVector pos, float mass, float radius, int color, PApplet parent, SubPlot plt) {
        super(pos, mass, radius, color, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.Vegan_COLOR[0],
                WorldConstants.Vegan_COLOR[1],
                WorldConstants.Vegan_COLOR[2]));
        energy = WorldConstants.INI_Vegan_ENERGY;
    }

    public Vegan(Vegan predator, boolean mutate, int mode, PApplet parent, SubPlot plt) {
        super(predator, mutate, mode, parent, plt);
        this.parent = parent;
        this.plt = plt;
        this.mass = this.dna.mass;
        this.setShape(parent,plt,dna.size,parent.color(
                WorldConstants.Vegan_COLOR[0],
                WorldConstants.Vegan_COLOR[1],
                WorldConstants.Vegan_COLOR[2]));
        energy = WorldConstants.INI_Vegan_ENERGY;
    }

    @Override
    public Animal reproduce(boolean mutate, int mode) {
        Animal child = null;
        if (energy > WorldConstants.Vegan_ENERGY_TO_REPRODUCE){
            energy -= WorldConstants.INI_Vegan_ENERGY;
            child = new Vegan(this, mutate, mode, parent, plt);
            if (mutate) child.mutateBehaviours();
        }
        return child;
    }

    @Override
    public void eat(Terrain terrain) {
        Patch patch = (Patch) terrain.world2Cell(pos.x, pos.y);
        if (patch.getState() == WorldConstants.PatchType.FOOD.ordinal()){
            energy += WorldConstants.ENERGY_FROM_PLANT*1.7;
            patch.setFertile();
        }
    }

}
