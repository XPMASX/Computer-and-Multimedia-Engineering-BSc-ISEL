package aa;

public class DNA {
    public float maxSpeed;
    public float maxForce;
    public float visionDistance;
    public float visionSafeDistance;
    public float visionAngle;
    public float deltaTPursuit;
    public float radiusArrive;
    public float deltaTWander;
    public float radiusWander;
    public float deltaPhiWander;
    public float size;
    public float mass;


    public DNA()
    {
        //Physics
        maxSpeed = random(1f, 2f);
        maxForce = random(4f, 7f);
        //Vision
        visionDistance = random(1.7f, 2.7f);
        visionSafeDistance = 0.25f * visionDistance;
        visionAngle = (float) Math.PI * 0.3f;
        //Pursuit
        deltaTPursuit = random(0.5f, 1f);
        //Arrive
        radiusArrive = random(3, 5);
        //Wander
        deltaTWander = random(0.3f, 0.6f);
        radiusWander = random(1f, 3f);
        deltaPhiWander = (float) (Math.PI/8);
        //size
        size = random(.15f,.4f);
        mass = random(0.8f,1.2f);
    }

    public DNA(DNA dna, boolean mutate, int mode)
    {
        //Physics
        maxSpeed = dna.maxSpeed;
        maxForce = dna.maxForce;
        //Vision
        visionDistance = dna.visionDistance;
        visionSafeDistance = dna.visionSafeDistance;
        visionAngle = dna.visionAngle;
        //Pursuit
        deltaTPursuit = dna.deltaTPursuit;
        //Arrive
        radiusArrive = dna.radiusArrive;
        //Wander
        deltaTWander = dna.deltaTWander;
        radiusWander = dna.radiusWander;
        deltaPhiWander = dna.deltaPhiWander;
        size = dna.size;
        mass = dna.mass;
        if (mutate) mutate(mode);
    }

    private void mutate(int mode) {
        maxSpeed += random(-0.2f, 0.2f);
        maxSpeed = Math.max(0, maxSpeed);
        if (mode!=1) {
            size += random(-0.03f, 0.03f);
            size = Math.max(0, size);
            mass = (float) (size + 0.8);
        }
        if (mode == 3 || mode == 4 || mode == 5)
        {
            visionSafeDistance += random(-0.2f, 0.2f);
            visionSafeDistance = Math.max(0, visionSafeDistance);
        }

    }

    public static float random(float min, float max)
    {
        return (float) (min + (max - min)*Math.random());
    }
}
