package fractals;

import processing.core.PApplet;
import tools.Complex;
import tools.SubPlot;

public class JuliaSet {
    private int niter;
    private int x0, y0;
    private int dimx, dimy;
    private float angle;

    public JuliaSet(int niter, SubPlot plt, float angle) {
        this.niter = niter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];
        this.angle = angle;

    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    //display normal
    public void display(PApplet p, SubPlot plt, float[] mx)
    {
        int tt = p.millis();
        p.loadPixels();
        for (int xx = x0; xx < x0+dimx; xx++) {
            for (int yy = y0; yy < y0+dimy; yy++) {
                double[] cc = plt.getWorldCoord(xx,yy);
                Complex c = new Complex(mx[0], mx[1]);
                Complex x = new Complex(cc);
                int i;
                for (i = 0; i < niter; i++) {
                    x.mult(x).add(c);
                    if (x.norm() > 2)
                        break;
                }
                if (i == niter) {

                    p.pixels[yy * p.width + xx] = p.color(0);
                }
                else{
                    p.pixels[xx+yy*p.width] = p.color((i % 16)*16, i, i);
                }

            }
        }
        p.updatePixels();
    }

    //display para Julia automático
    public void display2(PApplet p, SubPlot plt, float[] mx)
    {
        int tt = p.millis();
        p.loadPixels();
        for (int xx = x0; xx < x0+dimx; xx++) {
            for (int yy = y0; yy < y0+dimy; yy++) {
                double[] cc = plt.getWorldCoord(xx,yy);
                //ao utilizarmos um angulo que está sempre a ser incrementado podemos percorrer as várias posições do conjunto de Julia automaticamente
                Complex c = new Complex(Math.cos(angle*3.213),Math.sin(angle));
                Complex x = new Complex(cc);
                int i;
                for (i = 0; i < niter; i++) {
                    x.mult(x).add(c);
                    if (x.norm() > 2)
                        break;
                }
                if (i == niter) {

                    p.pixels[yy * p.width + xx] = p.color(0);
                }
                else{
                    p.pixels[xx+yy*p.width] = p.color((i % 16)*16, (i % 16)*16, i);
                }
            }
        }
        p.updatePixels();
    }
}
