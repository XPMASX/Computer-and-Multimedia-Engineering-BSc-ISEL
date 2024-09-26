package ecosystem;

import aa.*;
import physics.Body;
import physics.PSControl;
import physics.ParticleSystem;
import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private final List<Animal> allAnimals;
    private final List<Body> allTrackingBodies;

    //particles
    private final List<ParticleSystem> explosion;
    private final float[] velParams = {PApplet.radians(180), PApplet.radians(360), 1, 3};
    private final float[] lifetimeParams = {0.5f, 0.6f};
    private final float[] radiusParams = {0.1f, 0.2f};

    public Population(PApplet parent, SubPlot plt, Terrain terrain, int mode) {
        double[] window = plt.getWindow();
        allAnimals = new ArrayList<Animal>();

        allTrackingBodies = new ArrayList<Body>();

        explosion = new ArrayList<ParticleSystem>();

        List<Body> food = terrain.getFood();

        for (int i = 0; i < WorldConstants.INI_PREY_POPULATION; i++) {
            PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                    parent.random((float) window[2], (float) window[3]));
            int color = parent.color(
                    WorldConstants.PREY_COLOR[0],
                    WorldConstants.PREY_COLOR[1],
                    WorldConstants.PREY_COLOR[2]);
            Animal a = new Prey(pos, WorldConstants.PREY_MASS, WorldConstants.PREY_SIZE, color, parent, plt);
            a.addBehaviour(new Wander(1));
            if (mode == 2){
                Eye eye = new Eye(a, allTrackingBodies);
                a.setEye(eye);
            }
            if (mode == 3 || mode == 4){
                a.addBehaviour(new Seek(0));
                Eye eye = new Eye(a, food);
                a.setEye(eye);
            }
            allTrackingBodies.add(a);
            allAnimals.add(a);

        }

        if (mode == 4 || mode == 5) {
            for (int i = 0; i < WorldConstants.INI_PREDATOR_POPULATION; i++) {
                PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                        parent.random((float) window[2], (float) window[3]));
                int color = parent.color(
                        WorldConstants.PREDATOR_COLOR[0],
                        WorldConstants.PREDATOR_COLOR[1],
                        WorldConstants.PREDATOR_COLOR[2]);
                Animal a = new Predator(pos, WorldConstants.PREDATOR_MASS, WorldConstants.PREDATOR_SIZE, color, parent, plt);
                a.addBehaviour(new Wander(1));
                a.addBehaviour(new Seek(0));
                Eye eye = new Eye(a, allTrackingBodies);
                a.setEye(eye);
                a.dna.visionSafeDistance=0.9f;
                allAnimals.add(a);
            }
        }
        if (mode == 5) {
            for (int i = 0; i < WorldConstants.INI_Vegan_POPULATION; i++) {
                PVector pos = new PVector(parent.random((float) window[0], (float) window[1]),
                        parent.random((float) window[2], (float) window[3]));
                int color = parent.color(
                        WorldConstants.Vegan_COLOR[0],
                        WorldConstants.Vegan_COLOR[1],
                        WorldConstants.Vegan_COLOR[2]);
                Animal a = new Vegan(pos, WorldConstants.Vegan_MASS, WorldConstants.Vegan_SIZE, color, parent, plt);
                a.addBehaviour(new Wander(1));
                a.addBehaviour(new Seek(0));
                Eye eye = new Eye(a, food);
                a.setEye(eye);
                a.dna.size*=0.8f;
                allTrackingBodies.add(a);
                allAnimals.add(a);
            }
        }
    }

    public void update(float dt, Terrain terrain, int mode, PApplet p) {
        move(dt);
        eat(terrain);
        energy_consumption(terrain, dt, mode);
        boolean mutate = true;
        reproduce(mutate, mode);
        if (mode == 3 || mode == 4 || mode == 5)
            track(terrain);
        if (mode!=1)
            kill(p);
        die();
    }

    public void track(Terrain terrain)
    {
        List<Body> food = terrain.getFood();
        List<Body> targets = new ArrayList<Body>();
        targets.addAll(food);
        targets.addAll(allTrackingBodies);

        for (Animal a : allAnimals) {

            Eye eye;
            if (a instanceof Prey) eye = new Eye(a, targets);
            else if (a instanceof Predator) eye = new Eye(a, allTrackingBodies);
            else eye = new Eye(a, food);

            a.setEye(eye);
            a.eye.look();
            List<Body> inSight = a.eye.getNearSight();
            if (inSight.size()!=0) {
                for (Body b : inSight) {
                    for (Animal c : allAnimals) {
                        if ((b.getVel().mag()==0 && !(a instanceof Predator))
                                || (c.getPos() == b.getPos() &&  a.getDNA().size  > c.getDNA().size * 1.2 )){
                                a.setEye(new Eye(a, inSight));
                                a.mutateSeek(50);
                                break;
                        }
                    }
                }
            }
            else a.mutateSeek(0);
        }
    }

    private void move(float dt) {
        for (Animal a : allAnimals) {
            a.applyBehaviours(dt);

        }
    }

    private void eat(Terrain terrain) {
        for (Animal a : allAnimals) {
            a.eat(terrain);
        }
    }

    private void kill(PApplet p)
    {
        for (Animal a : allAnimals) {
            a.eye.look();
            List<Body> inSight = a.eye.getNearSight();
            for (Body b : inSight) {
                if (a.eye.nearSight(b.getPos())){
                    for (Animal c : allAnimals) {
                        float flow = 100;
                        if (a instanceof Predator) {
                            if (c.getPos() == b.getPos() & c != a){
                                int color = p.color(p.color(255, 0, 0));
                                PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
                                ParticleSystem ps = new ParticleSystem(new PVector(c.getPos().x, c.getPos().y),
                                        new PVector(), 1f, 1f, psc);
                                explosion.add(ps);
                                a.energy += WorldConstants.ENERGY_FROM_KILL;
                                c.energy = -100;
                                break;
                            }
                        }
                        else {
                            if (c.getPos() == b.getPos() &&  a.getDNA().size> c.getDNA().size * 1.2 ) {
                                int color = p.color(p.color(20, 40, 255));
                                PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, color);
                                ParticleSystem ps = new ParticleSystem(new PVector(a.getPos().x, a.getPos().y),
                                        new PVector(), 1f, 1f, psc);
                                explosion.add(ps);
                                a.energy += WorldConstants.ENERGY_FROM_KILL;
                                c.energy = -100;
                                break;
                            }

                        }
                    }
                }
            }
        }
    }

    private void energy_consumption(Terrain terrain, float dt, int mode) {
        for (Animal a : allAnimals) {
            a.energy_consumption(dt, terrain, mode);
        }
    }

    private void die() {
        for (int i = 0; i < allAnimals.size(); i++) {
            Animal a = allAnimals.get(i);
            if (a.die())
                allAnimals.remove(a);
        }
    }

    private void reproduce(boolean mutate, int mode) {
        for (int i = allAnimals.size() - 1; i >= 0; i--) {
            Animal a = allAnimals.get(i);
            Animal child = a.reproduce(mutate, mode);
            if (child != null) {
                allAnimals.add(child);
                if (!(child instanceof Predator))
                    allTrackingBodies.add(child);
            }
        }
    }

    public void display(PApplet p, SubPlot plt, float dt, boolean debug) {
        for (Animal a : allAnimals) {
            a.display(p, plt);
            if (a.equals(allAnimals.get(0))) {
                p.color(255,0,0);
                p.circle(a.getPos().x, a.getPos().y, 0.2f);
            }
            if (debug)
                a.eye.display(p,plt);
        }
        //explosao
        for (int i = 0; i< explosion.size(); i++)
        {
            explosion.get(i).move(dt);

            explosion.get(i).display(p, plt);
            if (explosion.get(i).timer>0.8f) {
                explosion.remove(explosion.get(i));
            }
        }
    }

    public int getNumAnimals() {
        return allAnimals.size();
    }

    public int getNumPredators() {
        int count = 0;
        for (Animal a : allAnimals) {
            if (a instanceof Predator)
                count++;
        }
        return count;
    }

    public int getNumOmni() {
        int count = 0;
        for (Animal a : allAnimals) {
            if (a instanceof Prey)
                count++;
        }
        return count;
    }

    public int getNumVegan() {
        int count = 0;
        for (Animal a : allAnimals) {
            if (a instanceof Vegan)
                count++;
        }
        return count;
    }

    public float getMeanMaxSpeed() {
        float sum = 0;
        for (Animal a : allAnimals)
            sum += a.getDNA().maxSpeed;

        return sum/allAnimals.size();
    }

    public float getMeanSize() {
        float sum = 0;
        for (Animal a : allAnimals)
            sum += a.getDNA().size;

        return sum/allAnimals.size();
    }

    public float getMeanSense() {
        float sum = 0;
        for (Animal a : allAnimals)
            sum += a.getDNA().visionSafeDistance;

        return sum/allAnimals.size();
    }

    public float getStdMaxSpeed() {
        float mean = getMeanMaxSpeed();
        float sum = 0;
        for (Animal a : allAnimals)
            sum += Math.pow(a.getDNA().maxSpeed - mean, 2);
        return (float) Math.sqrt(sum/allAnimals.size());
    }

    public float[] getMeanWeights() {
        float[] sums = new float[2];
        for (Animal a: allAnimals) {
            sums[0] += a.getBehaviours().get(0).getWeight();
            //sums[1] += a.getBehaviours().get(1).getWeight();
        }
        sums[0] /= allAnimals.size();
        //sums[1] /= allAnimals.size();
        return sums;
    }


}
