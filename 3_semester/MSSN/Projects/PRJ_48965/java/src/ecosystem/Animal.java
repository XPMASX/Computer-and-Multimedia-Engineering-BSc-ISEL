package ecosystem;

import aa.Behaviour;
import aa.Boid;
import aa.DNA;
import aa.Eye;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public abstract class Animal extends Boid implements IAnimal {

    protected float energy;
    protected Animal(PVector pos, float mass, float radius, int color, PApplet p, SubPlot plt) {
        super(pos, mass, radius, color, p, plt);
        this.mass = this.dna.mass;
        this.setShape(p,plt,dna.size,p.color(
                this.color));
    }

    protected Animal(Animal a, boolean mutate, int mode, PApplet p, SubPlot plt) {
        super(a.pos, a.mass, a.radius, a.color, p, plt);
        for (Behaviour b : a.behaviours) {
            this.addBehaviour(b);
        }
        if (a.eye != null)
            eye = new Eye(this, a.eye);

        dna = new DNA(a.dna, mutate, mode);
        this.mass = this.dna.mass;
        a.setShape(p,plt,dna.size,p.color(
                a.color));
    }

    @Override
    public boolean die()
    {
        return (energy < 0);
    }

    @Override
    public void energy_consumption(float dt, Terrain terrain, int mode)
    {
        energy -= dt; //basic metabolism
        switch (mode) {
            case 1 -> energy -= mass * Math.pow(vel.mag(), 2) * dt;
            case 2 -> energy -= Math.pow(getDNA().mass, 3) * Math.pow(vel.mag(), 2) * dt;
            case 3, 4, 5 ->
                    energy -= Math.pow(getDNA().mass, 3) * Math.pow(vel.mag(), 2) * dt + (getDNA().visionSafeDistance * 0.01);
        }
        Patch patch = (Patch) terrain.world2Cell(pos.x,pos.y);
        if (patch.getState() == WorldConstants.PatchType.OBSTACLE.ordinal())
            energy -= 50*dt;
    }
}
