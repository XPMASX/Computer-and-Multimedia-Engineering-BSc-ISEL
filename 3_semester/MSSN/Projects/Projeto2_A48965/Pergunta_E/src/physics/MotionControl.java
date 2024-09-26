package physics;

import physics.RigidBody.ControlType;
import processing.core.PApplet;
import processing.core.PVector;

public class MotionControl {
    private RigidBody rb;
    private ControlType ct;
    private PVector vector;

    public MotionControl(RigidBody rb, ControlType ct)
    {
        this.rb = rb;
        this.ct = ct;
        vector = new PVector();
    }

    public void setVector(PVector vector)
    {
        this.vector = vector;
        switch (ct) {
            case POSITION -> rb.setPos(vector);
            case VELOCITY -> rb.setVel(vector);
            case FORCE -> rb.applyForce(vector);
        }
    }

    public void display(PApplet p)
    {
        p.strokeWeight(1);
        p.line(-p.width/2,0,p.width/2,0);
        p.line(0,-p.height/2,0,p.height/2);
        p.textSize(24);
        p.fill(0);
        p.text("Control by " + ct.toString(), -140, -280);

        p.strokeWeight(3);
        p.line(0,0,vector.x,vector.y);
    }
}
