package fractals;

import processing.core.PApplet;
import processing.core.PVector;
import tools.SubPlot;

public class Turtle {

    private float len;
    private float angle;

    public Turtle(float len, float angle) {
        this.len = len;
        this.angle = angle;
    }

    public void setPose(PVector position, float orientation,PApplet p, SubPlot plt )
    {
        float[] pp = plt.getPixelCoord(position.x, position.y);
        p.translate(pp[0], pp[1]);
        p.rotate(-orientation);
    }

    public void scaling(float s)
    {
        len *= s;
    }

    public float getLength() {
        return len;
    }

    public void setLength(float length) {
        len = length;
    }

    public void render(LSystem lsys , PApplet p, SubPlot plt)
    {
        p.stroke(0);
        float[] lenPixel = plt.getVectorCoord(len, len);
        for (int i = 0; i < lsys.getSequence().length(); i++) {
            char c = lsys.getSequence().charAt(i);
            if (c == 'F' || c == 'G')
            {
                p.line(0, 0, lenPixel[0], 0);
                p.translate(lenPixel[0], 0);
            }
            else if (c == 'f')
                p.translate(lenPixel[0],0);
            else if (c == '+')
                p.rotate(angle);
            else if (c == '-')
                p.rotate(-angle);
            else if (c == '[')
                p.pushMatrix();
            else if (c == ']')
                p.popMatrix();
        }
    }
    public void render2(LSystem lsys , int scale, int[] randos, PApplet p, SubPlot plt)
    {
        p.stroke(0);
        float[] lenPixel = plt.getVectorCoord(len, len);
        for (int i = 0; i < lsys.getSequence().length(); i++) {
            System.out.println(lsys.getSequence().length());
            char c = lsys.getSequence().charAt(i);
            if (c == 'F' && scale > 2 )
            {
                p.pushStyle();
                p.noStroke();
                if (randos[i] < 25)
                {
                    p.fill(p.color(255, 0, 0));
                    p.circle(0, 0, 10);
                }
                else if (randos[i] > 25 && randos[i] < 50  )
                {
                    p.fill(p.color(0, 255, 0));
                    p.circle(0, 0, 7);
                    p.circle(5, 0, 7);
                    p.circle(0, 5, 7);
                    p.circle(5, 5, 7);
                }
                else {
                    p.line(0, 0, lenPixel[0], 0);
                    p.translate(lenPixel[0], 0);
                }

                p.popStyle();
                p.translate(lenPixel[0], 0);
            }
            else if (c == 'G')
            {
                p.stroke(p.color(114,92,66));
                p.line(0, 0, lenPixel[0], 0);
                p.translate(lenPixel[0], 0);
            }
            else if (c == 'f')
                p.translate(lenPixel[0],0);
            else if (c == '+')
                p.rotate(angle);
            else if (c == '-')
                p.rotate(-angle);
            else if (c == '[')
                p.pushMatrix();
            else if (c == ']')
                p.popMatrix();
        }
    }
}
