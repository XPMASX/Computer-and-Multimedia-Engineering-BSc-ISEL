package physics;

import processing.core.PVector;


public class PSControl {

    private float averageAngle;
    private float dispersionAngle;
    private float minVelocity;
    private float maxVelocity;
    private float minLifetime;
    private float maxLifetime;
    private float minRadius;
    private float maxRadius;
    private float flow;
    private int color;

    public PSControl(float[] velControl, float[] lifetime, float[] radius, float flow, int color)
    {
        setVelPArams(velControl);
        setLifetimePArams(lifetime);
        setRadiusPArams(radius);
        setFlow(flow);
        setColor(color);
    }

    public float getAverageAngle() {
        return averageAngle;
    }

    public float getFlow() {
        return flow;
    }

    public int getColor() {
        return color;
    }

    public void setFlow(float flow) {
        this.flow = flow;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setLifetimePArams(float[] lifetime)
    {
        minLifetime = lifetime[0];
        maxLifetime = lifetime[1];
    }

    public void setRadiusPArams(float[] radius)
    {
        minRadius = radius[0];
        maxRadius = radius[1];
    }

    public void setVelPArams(float[] velControl)
    {
        averageAngle = velControl[0];
        dispersionAngle = velControl[1];
        minVelocity = velControl[2];
        maxVelocity = velControl[3];
    }

    public float getRandRadius()
    {
        return getRand(minRadius, maxRadius);
    }

    public float getRandLifetime()
    {
        return getRand(minLifetime, maxLifetime);
    }

    public PVector getRandVel()
    {
      float angle  = getRand(averageAngle - dispersionAngle/2, averageAngle + dispersionAngle);
      PVector v = PVector.fromAngle(angle);
      return v.mult(getRand(minVelocity,maxVelocity));
    }

    public static float getRand(float min, float max)
    {
        return min + (float) (Math.random() * (max - min));
    }
}
