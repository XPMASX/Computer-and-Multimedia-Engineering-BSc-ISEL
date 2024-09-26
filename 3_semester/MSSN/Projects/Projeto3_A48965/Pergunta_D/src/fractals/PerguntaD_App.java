package fractals;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class PerguntaD_App implements IProcessingApp {

    private double[] window = {-2, 2, -2, 2};
    private float[] viewport = {0, 0, 0.5f, 0.5f};
    private float[] viewport2 = {0.51f, 0.51f, 0.48f, 0.48f};
    private SubPlot plt,plt2;
    private MandelbrotSet ms;
    private JuliaSet js;
    private int mx0, my0, mx1, my1;
    private float[] mx = new float[2];
    private float angle = 0;
    private boolean display = true;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(window, viewport, p.width, p.height);
        plt2 = new SubPlot(window, viewport2, p.width, p.height);
        ms = new MandelbrotSet(300,plt);
        js = new JuliaSet(300,plt2, angle);
        System.out.println("""
                Programa para desenhar conjuntos de Mandelbrot e conjuntos de Julia
                Com a tecla '1' selecionamos o modo de controlo do conjunto de Julia com o arrasto do mouse
                ou ao clicar na tecla 'espaço' por cima de um ponto no conjunto de Mandelbrot.
                Com a tecla '2' os conjuntos de Julia são percorridos automaticamente.
                Com a tecla 'r' reiniciamos a aplicação.""");

    }

    @Override
    public void draw(PApplet p, float dt) {
        ms.display(p,plt);
        if (display)
            js.display(p, plt2, mx);
        else
            js.display2(p, plt2, mx);
        displayNewWindow(p);

        //a cada instância do draw incrementamos o angulo
        if (!display) {
            js.setAngle(angle);
            angle += 0.015;
        }
    }

    private void displayNewWindow(PApplet p)
    {
        p.pushStyle();
        p.noFill();
        p.strokeWeight(3);
        p.stroke(255);
        p.rect(mx0, my0, mx1-mx0, my1-my0);
        p.popStyle();
    }

    @Override
    public void mousePressed(PApplet p) {
        if (plt.isInside(p.mouseX, p.mouseY)) {
            mx0 = mx1 = p.mouseX;
            my0 = my1 = p.mouseY;

        }

        if (plt2.isInside(p.mouseX, p.mouseY)) {
            double[] w = plt2.getWorldCoord(p.mouseX, p.mouseY);
            mx[0] = (float) w[0];
            mx[1] = (float) w[1];
        }
    }


    @Override
    public void mouseReleased(PApplet p) {
        if (plt.isInside(p.mouseX, p.mouseY)) {
            double[] xy0 = plt.getWorldCoord(mx0, my0);
            double[] xy1 = plt.getWorldCoord(p.mouseX, p.mouseY);
            double xmin = Math.min(xy0[0], xy1[0]);
            double xmax = Math.max(xy0[0], xy1[0]);
            double ymin = Math.min(xy0[1], xy1[1]);
            double ymax = Math.max(xy0[1], xy1[1]);
            double[] window = {xmin, xmax, ymin, ymax};
            plt = new SubPlot(window, viewport, p.width, p.height);
            mx0 = my0 = my1 = mx1 = 0;
        }

    }

    @Override
    public void mouseDragged(PApplet p) {
        if (plt.isInside(p.mouseX, p.mouseY)) {
            mx1 = p.mouseX;
            my1 = p.mouseY;
        }
        if (plt2.isInside(p.mouseX, p.mouseY)) {
            double[] w = plt2.getWorldCoord(p.mouseX, p.mouseY);
            mx[0] = (float) w[0];
            mx[1] = (float) w[1];
        }
    }

    @Override
    public void keyPressed(PApplet p) {
        if (p.key == ' ' && plt.isInside(p.mouseX, p.mouseY))
        {
            double[] w = plt.getWorldCoord(p.mouseX, p.mouseY);
            mx[0] = (float) w[0];
            mx[1] = (float) w[1];
        }
        if (p.key == 'r' )
            setup(p);
        if (p.key == '1' )
            display=true;
        if (p.key == '2' )
            display=false;
    }
}
