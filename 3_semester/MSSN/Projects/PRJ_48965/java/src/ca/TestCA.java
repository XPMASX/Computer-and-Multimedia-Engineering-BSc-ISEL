package ca;
import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;

public class TestCA implements IProcessingApp {

    private int nrows = 200;
    private int ncols = 250;
    private int nStates = 2;
    private int mode = 1;
    private int neighbour = 1;
    private boolean pause = false;
    private SubPlot plt;
    private double[] window = {0, 10, 0,10};
    private float[] viewport = {0.3f, 0.3f, 0.5f, 0.6f};
    CellularAutomata ca;

    @Override
    public void setup(PApplet p) {

        plt = new SubPlot(window,viewport,p.width,p.height);

        //os modos de jogo LIFE, HIGHLIFE e LIFE_COLOR possuem 2 estados
        if (mode == 1 || mode == 2 || mode == 5) nStates=2;

        //o modo de jogo Immigration possui 3 estados
        if (mode == 3) nStates = 3;

        //o modo de jogo QUADLIFE possui 5 estados
        if (mode == 4) nStates = 5;

        ca = new CellularAutomata(p, plt, nrows, ncols, nStates, neighbour);
        ca.init();
        //se tivermos no modo de jogo LIFE_COLOR atribui a cada cell viva uma cor
        if (mode==5)
            ca.setColors(p);
        ca.display(p);

    }

    @Override
    public void draw(PApplet p, float dt)
    {
        if (!pause)
        {
            //entra no modo de jogo que o utilizador selecionar
            switch (mode)
            {
                case 1:
                    ca.Life();
                    break;
                case 2:
                    ca.HighLife();
                    break;
                case 3:
                    ca.Immigration();
                    break;
                case 4:
                    ca.QuadLife();
                    break;
                case 5:
                    ca.Life_Color();
            }
        }
        ca.display(p);
    }

    @Override
    public void mousePressed(PApplet p) {
        Cell cell = ca.pixel2Cell(p.mouseX, p.mouseY);

        //se o jogo estiver em pausa torna o pixel em que o utilizador selecionou no estado contrario ao que esta
        if (pause)
            if (cell.getState()==0)
                cell.setState(1);
            else
                cell.setState(0);

    }

    public void keyPressed(PApplet p)
    {
        //se pressionar a tecla 'r' da reset ao jogo
        if (p.key=='r'  || p.key=='R')
            setup(p);

        //se pressionar a tecla '1' modo de jogo LIFE
        if (p.key== '1') {
            mode = 1;
            setup(p);
            System.out.println("Estás a jogar Life (23/3)");
        }

        //se pressionar a tecla '2' modo de jogo HIGHLIFE
        if (p.key== '2') {
            mode = 2;
            setup(p);
            System.out.println("Estás a jogar HighLife (23/36)");
        }

        //se pressionar a tecla '3' modo de jogo IMMIGRATION
        if (p.key== '3') {
            mode = 3;
            setup(p);
            System.out.println("Estás a jogar Immigration (23/3)");
        }

        //se pressionar a tecla '4' modo de jogo QUADLIFE
        if (p.key== '4') {
            mode = 4;
            setup(p);
            System.out.println("Estás a jogar QuadLife (23/3)");
        }

        //se pressionar a tecla '5' modo de jogo LIFE_COLOR
        if (p.key== '5') {
            mode = 5;
            setup(p);
            System.out.println("Estás a jogar a versão com cores de Life (23/3)");
        }

        //se pressionar a tecla do espaço pausa o jogo
        if (p.key== ' ') {
            pause = !pause;
            if (pause)
                System.out.println("O jogo está em pausa");
            else
                System.out.println("O jogo está a correr");
        }

        //se pressionar a tecla 'c' mata todas as cells
        if (p.key== 'c' || p.key=='C') {
            pause=true;
            ca.clear();
        }
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
