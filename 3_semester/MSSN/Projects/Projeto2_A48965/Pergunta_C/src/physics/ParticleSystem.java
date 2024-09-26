package physics;

import processing.core.PApplet;
import processing.core.PVector;
import simple_subplot.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class ParticleSystem extends Body{

    private List<Particle> particles;
    private PSControl psc;
    public float timer;

    public ParticleSystem(PVector pos, PVector vel, float mass,
                          float radius, PSControl psc)
    {
        super(pos, vel, mass, radius, 0);
        this.psc = psc;
        this.particles = new ArrayList<Particle>();
        timer=0;
    }

    public PSControl getPSControl()
    {
       return psc;
    }

    @Override
    public void move(float dt)
    {
        super.move(dt);
        timer += dt;
        addParticles(dt);
        for (int i = particles.size()-1; i >=0 ; i--)
        {
            Particle p = particles.get(i);
            p.move(dt);
            if (p.isDead())
                particles.remove(i);
        }
    }

    private void addParticles(float dt)
    {
        float particlesPerFrame = psc.getFlow() * dt;
        int n = (int) particlesPerFrame;
        float f = particlesPerFrame - n;
        for (int i = 0; i < n; i++) {
            addOneParticle();
            if (Math.random() < f)
                addOneParticle();
        }
    }

    private void addOneParticle()
    {
        Particle particle = new Particle(pos, psc.getRandVel(), psc.getRandRadius(), psc.getColor(), psc.getRandLifetime());
        particles.add(particle);
    }

    @Override
    public void display(PApplet p, SubPlot plt)
    {
        for (Particle particle: particles)
        {
            particle.display(p,plt);
        }
    }
}
