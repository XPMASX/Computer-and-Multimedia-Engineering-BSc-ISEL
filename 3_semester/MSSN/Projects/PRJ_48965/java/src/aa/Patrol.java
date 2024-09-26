package aa;

import processing.core.PVector;

import java.util.ArrayList;

public class Patrol extends Behaviour{
    private ArrayList<PVector> path;
    int x=0;

    public Patrol(float weight, ArrayList<PVector> path) {
        super(weight);
        this.path = path;
    }

    @Override
    public PVector getDesiredVelocity(Boid me)
    {
            if (PVector.dist(me.getPos(),path.get(x)) < 0.05) {
                x = (x+ 1) % path.size();
            }
        return PVector.sub(path.get(x), me.getPos());
    }

}
