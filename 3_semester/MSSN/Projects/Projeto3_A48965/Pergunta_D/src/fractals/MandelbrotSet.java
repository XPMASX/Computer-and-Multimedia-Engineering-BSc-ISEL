package fractals;

import processing.core.PApplet;
import tools.Complex;
import tools.SubPlot;

public class MandelbrotSet {
    private int niter;
    private int x0, y0;
    private int dimx, dimy;

    public MandelbrotSet(int niter, SubPlot plt) {
        this.niter = niter;
        float[] bb = plt.getBoundingBox();
        x0 = (int) bb[0];
        y0 = (int) bb[1];
        dimx = (int) bb[2];
        dimy = (int) bb[3];

    }

    public void display(PApplet p, SubPlot plt)
    {
        int tt = p.millis();
        p.loadPixels();
        for (int xx = x0; xx < x0+dimx; xx++) {
            for (int yy = y0; yy < y0+dimy; yy++) {
                double[]cc = plt.getWorldCoord(xx,yy);
                Complex c = new Complex(cc);
                Complex x = new Complex();
                int i;
                for (i = 0; i < niter; i++) {
                    x.mult(x).add(c);
                    if (x.norm() > 2)
                        break;
                }

                p.pixels[yy*p.width+xx] = (i == niter) ? p.color(0) : p.color((i % 16)*16, 0, i);;
            }
        }
        p.updatePixels();
    }
}
