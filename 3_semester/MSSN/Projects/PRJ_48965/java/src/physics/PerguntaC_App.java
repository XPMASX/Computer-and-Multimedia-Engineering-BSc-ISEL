package physics;

import processing.core.PApplet;
import processing.core.PVector;
import setup.IProcessingApp;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class PerguntaC_App implements IProcessingApp {

    //Dobro do raio de jupiter * 100
    private final float Raio = 1.4e10f;
    private final float Raio_Jupiter =Raio*0.8f;

    private final float sunMass = 1.989e30f;

    private final float distMercurySun = Raio*5;

    private final float earthMass = 1.5e29f;
    private final float distEarthSun = (float) (distMercurySun*2.5);
    private final float earthSpeed = 2.9e4f;

    private final float mercuryMass = 3.3e23f;

    private final float mercurySpeed = 4.1e4f;

    private final float moonMass = 1e22f;
    private final float distMoonSun = (float) (distMercurySun*2.74);
    private final float moonSpeed = 11.8e3f;

    private final float venusMass = 4.8675e24f;
    private final float distVenusSun = (float) (distMercurySun*1.7);
    private final float venusSpeed = 3.4e4f;

    private final float marsMass = (float) (earthMass*0.107);
    private final float distMarsSun = (float) (distMercurySun*3.5);
    private final float marsSpeed = 2.4e4f;

    private final float jupiterMass = 1.898e30f;
    private final float distJupiterSun = (float) (distMercurySun*5.5);
    private final float jupiterSpeed = 1.9e4f;

    private final float saturnMass = (float) (earthMass*95.159);
    private final float distSaturnSun = (float) (distMercurySun*7.5);
    private final float saturnSpeed = 1.6e4f;

    private final float uranusMass = (float) (earthMass*14.536);
    private final float distUranusSun = (float) (distMercurySun*9.5);
    private final float uranusSpeed = 1.4e4f;

    private final float neptuneMass = (float) (earthMass*17.147);
    private final float distNeptuneSun = (float) (distMercurySun*11.5);
    private final float neptuneSpeed = 1.27e4f;

    private final float asteroidMass = (float) (earthMass*0.107);
    private final float distAsteroidSun = (float) (distMercurySun*4.25);
    private final float asteroidSpeed = 2.2e4f;

    private List<ParticleSystem> pss,pss_j,pss_s,pss_u,pss_n,pss_a1,pss_a2,pss_a3,pss_a4;
    private List<Body> comets;
    private final float[] velParams = {PApplet.radians(360)  , PApplet.radians(360)  , 1.27e7f, 1.6e10f};
    private final float[] lifetimeParams = {2, 2.3f};
    private final float[] radiusParams = {Raio_Jupiter*0.55f*1.5f, Raio_Jupiter*1.2f};
    private final float flow = 20;
    private final float[] velParams_a = {PApplet.radians(180)  , PApplet.radians(20)  , 1.27e8f, 5e10f};
    private final float[] lifetimeParams_a = {1, 2f};
    private final float[] radiusParams_a = {Raio_Jupiter*0.55f, Raio_Jupiter*0.8f};
    private final float flow_a = 30;


    private final float[] viewport = {0.1f,0.1f,0.8f,0.8f};
    private final double[] window = {-1.1*distNeptuneSun, 1.1*distNeptuneSun,
            -1.1*distNeptuneSun, 1.1*distNeptuneSun};

    private SubPlot plt;
    private Body sun, earth, mercury, venus, mars, jupiter, saturn, uranus, neptune, moon;
    private Body asteroid1,asteroid2,asteroid3,asteroid4, comet;



    private final float speedUp = 60 * 60 * 24 * 30;

    @Override
    public void setup(PApplet p)
    {
        plt = new SubPlot(window, viewport, p.width, p.height);
        sun = new Body(new PVector(), new PVector(), sunMass, Raio*1.6f, p.color(255,128,0));
        earth = new Body(new PVector(0 ,distEarthSun), new PVector(earthSpeed,0), earthMass,
                (float) (Raio_Jupiter*0.55*1.5), p.color(0,180,120));
        moon = new Body(new PVector(0 ,distMoonSun), new PVector(moonSpeed,0), moonMass,
                (float) (Raio_Jupiter*0.3), p.color(254,252,215));
        mercury = new Body(new PVector(0 ,distMercurySun), new PVector(mercurySpeed,0), mercuryMass,
                (float) (Raio_Jupiter*0.3*1.3), p.color(151,151,159));
        venus = new Body(new PVector(0 ,distVenusSun), new PVector(venusSpeed,0), venusMass,
                (float) (Raio_Jupiter*0.5*1.5), p.color(255,198,73));
        mars = new Body(new PVector(0 ,distMarsSun), new PVector(marsSpeed,0), marsMass,
                (float) (Raio_Jupiter*0.4*1.5), p.color(193,68,14));
        jupiter = new Body(new PVector(0 ,distJupiterSun), new PVector(jupiterSpeed,0), jupiterMass,
                Raio_Jupiter*1.5f, p.color(180,92,61));
        saturn = new Body(new PVector(0 ,distSaturnSun), new PVector(saturnSpeed,0), saturnMass,
                (float) (Raio_Jupiter*0.9*1.5f), p.color(250,229,191));
        uranus = new Body(new PVector(0 ,distUranusSun), new PVector(uranusSpeed,0), uranusMass,
                (float) (Raio_Jupiter*0.8*1.5f), p.color(172,229,238));
        neptune = new Body(new PVector(0 ,distNeptuneSun), new PVector(neptuneSpeed,0), neptuneMass,
                (float) (Raio_Jupiter*0.7*1.5f), p.color(0,125,172));

        asteroid1 = new Body(new PVector(0 ,distAsteroidSun), new PVector(asteroidSpeed,0), asteroidMass,
                (float) (Raio_Jupiter*0.4*1.5), p.color(254,252,215));
        asteroid2 = new Body(new PVector(0 ,-distAsteroidSun), new PVector(-asteroidSpeed,0), asteroidMass,
                (float) (Raio_Jupiter*0.4*1.5), p.color(254,252,215));
        asteroid3 = new Body(new PVector(distAsteroidSun ,0), new PVector(0,asteroidSpeed), asteroidMass,
                (float) (Raio_Jupiter*0.4*1.5), p.color(254,252,215));
        asteroid4 = new Body(new PVector(-distAsteroidSun ,0), new PVector(0,asteroidSpeed), asteroidMass,
                (float) (Raio_Jupiter*0.4*1.5), p.color(254,252,215));

        pss = new ArrayList<ParticleSystem>();
        pss_j = new ArrayList<ParticleSystem>();
        pss_s = new ArrayList<ParticleSystem>();
        pss_u = new ArrayList<ParticleSystem>();
        pss_n = new ArrayList<ParticleSystem>();
        pss_a1 = new ArrayList<ParticleSystem>();
        pss_a2 = new ArrayList<ParticleSystem>();
        pss_a3 = new ArrayList<ParticleSystem>();
        pss_a4 = new ArrayList<ParticleSystem>();
        comets = new ArrayList<Body>();
        PSControl psc = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(255,0,0));
        PSControl psc_j = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(jupiter.color));
        PSControl psc_s = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(saturn.color));
        PSControl psc_u = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(uranus.color));
        PSControl psc_n = new PSControl(velParams, lifetimeParams, radiusParams, flow, p.color(neptune.color));
        PSControl psc_a = new PSControl(velParams_a, lifetimeParams_a, radiusParams_a, flow_a, p.color(113,118,122));
        ParticleSystem ps = new ParticleSystem(new PVector(0, 0),
                new PVector(),1f, 0.2f, psc);
        ParticleSystem ps_j = new ParticleSystem(new PVector(0, distJupiterSun),
                new PVector(jupiterSpeed,0),1, Raio_Jupiter*1.5f, psc_j);
        ParticleSystem ps_s = new ParticleSystem(new PVector(0, distSaturnSun),
                new PVector(saturnSpeed,0),1, Raio_Jupiter*1.5f, psc_s);
        ParticleSystem ps_u = new ParticleSystem(new PVector(0, distSaturnSun),
                new PVector(uranusSpeed,0),1, Raio_Jupiter*1.5f, psc_u);
        ParticleSystem ps_n = new ParticleSystem(new PVector(0, distSaturnSun),
                new PVector(neptuneSpeed,0),1, Raio_Jupiter*1.5f, psc_n);
        ParticleSystem ps_a1 = new ParticleSystem(new PVector(0, distAsteroidSun),
                new PVector(asteroidSpeed,0),1, Raio_Jupiter*1.5f, psc_a);
        ParticleSystem ps_a2 = new ParticleSystem(new PVector(0, -distAsteroidSun),
                new PVector(asteroidSpeed,0),1, Raio_Jupiter*1.5f, psc_a);
        ParticleSystem ps_a3 = new ParticleSystem(new PVector(distAsteroidSun, 0),
                new PVector(asteroidSpeed,0),1, Raio_Jupiter*1.5f, psc_a);
        ParticleSystem ps_a4 = new ParticleSystem(new PVector(-distAsteroidSun, 0),
                new PVector(asteroidSpeed,0),1, Raio_Jupiter*1.5f, psc_a);
        pss.add(ps);
        pss_j.add(ps_j);
        pss_s.add(ps_s);
        pss_u.add(ps_u);
        pss_n.add(ps_n);
        pss_a1.add(ps_a1);
        pss_a2.add(ps_a2);
        pss_a3.add(ps_a3);
        pss_a4.add(ps_a4);


    }

    @Override
    public void draw(PApplet p, float dt)
    {
        float[]pp = plt.getBoundingBox();
        p.fill(53,57,53,50);
        p.rect(pp[0], pp[1], pp[2], pp[3]);


        for (ParticleSystem ps: pss)
        {
            ps.move(dt*5);
            ps.display(p, plt);
        }

        sun.display(p, plt);


        earth.applyForce(sun.attraction(earth));

        earth.move(dt*speedUp);
        earth.display(p, plt);


        moon.applyForce(earth.attraction(moon).mult(0.45f));
        moon.applyForce(sun.attraction(moon));

        moon.move(dt*speedUp);
        moon.display(p, plt);


        mercury.applyForce(sun.attraction(mercury));

        mercury.move(dt*speedUp);
        mercury.display(p, plt);


        venus.applyForce(sun.attraction(venus));

        venus.move(dt*speedUp);
        venus.display(p, plt);


        mars.applyForce(sun.attraction(mars));

        mars.move(dt*speedUp);
        mars.display(p, plt);


        asteroid1.applyForce(sun.attraction(asteroid1));
        asteroid1.move(dt*speedUp);
        for (ParticleSystem ps_a1: pss_a1)
        {
            ps_a1.pos.set(asteroid1.pos.x,asteroid1.pos.y);
            ps_a1.move(dt*6);
            ps_a1.display(p, plt);
        }
        velParams[0] = PApplet.map(asteroid1.vel.heading(), 0, PApplet.radians(180), PApplet.radians(0), PApplet.radians(360));
        for (ParticleSystem ps : pss_a1)
        {
            PSControl psc_a = ps.getPSControl();
            psc_a.setVelPArams(velParams);
        }

        asteroid2.applyForce(sun.attraction(asteroid2));
        asteroid2.move(dt*speedUp);
        for (ParticleSystem ps: pss_a2)
        {
            ps.pos.set(asteroid2.pos.x,asteroid2.pos.y);
            ps.move(dt*6);
            ps.display(p, plt);
        }
        velParams[0] = PApplet.map(asteroid2.vel.heading(), 0, PApplet.radians(180), PApplet.radians(0), PApplet.radians(360));
        for (ParticleSystem ps : pss_a2)
        {
            PSControl psc_a = ps.getPSControl();
            psc_a.setVelPArams(velParams);
        }

        asteroid3.applyForce(sun.attraction(asteroid3));
        asteroid3.move(dt*speedUp);
        for (ParticleSystem ps: pss_a3)
        {
            ps.pos.set(asteroid3.pos.x,asteroid3.pos.y);
            ps.move(dt*6);
            ps.display(p, plt);
        }
        velParams[0] = PApplet.map(asteroid3.vel.heading(), 0, PApplet.radians(180), PApplet.radians(0), PApplet.radians(360));
        for (ParticleSystem ps : pss_a3)
        {
            PSControl psc_a = ps.getPSControl();
            psc_a.setVelPArams(velParams);
        }

        asteroid4.applyForce(sun.attraction(asteroid4));
        asteroid4.move(dt*speedUp);
        for (ParticleSystem ps: pss_a4)
        {
            ps.pos.set(asteroid4.pos.x,asteroid4.pos.y);
            ps.move(dt*6);
            ps.display(p, plt);
        }
        velParams[0] = PApplet.map(asteroid4.vel.heading(), 0, PApplet.radians(180), PApplet.radians(0), PApplet.radians(360));
        for (ParticleSystem ps : pss_a4)
        {
            PSControl psc_a = ps.getPSControl();
            psc_a.setVelPArams(velParams);
        }

        jupiter.applyForce(sun.attraction(jupiter));

        jupiter.move(dt*speedUp);
        for (ParticleSystem ps: pss_j)
        {
            ps.pos.set(jupiter.pos.x,jupiter.pos.y);
            ps.move(dt*6);
            ps.getPSControl().setColor(p.color(jupiter.color,16));
            ps.display(p, plt);
        }
        jupiter.display(p, plt);


        saturn.applyForce(sun.attraction(saturn));

        saturn.move(dt*speedUp);
        for (ParticleSystem ps: pss_s)
        {
            ps.pos.set(saturn.pos.x,saturn.pos.y);
            ps.move(dt*6);
            ps.getPSControl().setColor(p.color(saturn.color,16));
            ps.display(p, plt);
        }
        saturn.display(p, plt);


        uranus.applyForce(sun.attraction(uranus));

        uranus.move(dt*speedUp);
        for (ParticleSystem ps: pss_u)
        {
            ps.pos.set(uranus.pos.x,uranus.pos.y);
            ps.move(dt*6);
            ps.getPSControl().setColor(p.color(uranus.color,16));
            ps.display(p, plt);
        }
        uranus.display(p, plt);


        neptune.applyForce(sun.attraction(neptune));

        neptune.move(dt*speedUp);
        for (ParticleSystem ps: pss_n)
        {
            ps.pos.set(neptune.pos.x,neptune.pos.y);
            ps.move(dt*6);
            ps.getPSControl().setColor(p.color(neptune.color,16));
            ps.display(p, plt);
        }
        neptune.display(p, plt);

        for(Body comet : comets)
        {
            comet.applyForce(jupiter.attraction(comet));
            comet.applyForce(sun.attraction(comet));

        }

        for (Body comet: comets)
        {
            comet.move(dt*speedUp);
            comet.display(p, plt);
        }

    }

    @Override
    public void mousePressed(PApplet p) {
        double[] ww = plt.getWorldCoord(p.mouseX, p.mouseY);

        comet = new Body(new PVector((float) ww[0], (float) ww[1]), new PVector(moonSpeed,0), moonMass,
                (float) (Raio_Jupiter*0.3), p.color(255,138,0));
        comets.add(comet);
    }

    @Override
    public void keyPressed(PApplet p) {

    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
