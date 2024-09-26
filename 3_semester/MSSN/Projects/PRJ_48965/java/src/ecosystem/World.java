package ecosystem;

import processing.core.PApplet;
import setup.IProcessingApp;
import tools.SubPlot;
import tools.TimeGraph;

public class World implements IProcessingApp {

    private final float timeDuration = 60;
    private final float refPopulation = 15;
    private final float refMeanMaxSpeed = 1f;
    private final float refStdMaxSpeed = 0.2f;
    private final float refMeanSize = 0.3f;
    private final float refMeanSense = 0.5f;

    private final float[] viewport = {0f, 0f, .7f, 1f};

    private final double[] winGraph1 = {0, timeDuration, 0, 2*refPopulation};
    private final double[] winGraph2 = {0, timeDuration, 0, 2*refMeanMaxSpeed};
    private final double[] winGraph3 = {0, timeDuration, 0, 2*refStdMaxSpeed};

    private final float[] viewGraph1 = {.71f, .04f, .28f, .28f};
    private final float[] viewGraph2 = {.71f, .37f, .28f, .28f};
    private final float[] viewGraph3 = {.71f, .70f, .28f, .28f};

    private SubPlot plt, pltGraph1, pltGraph2, pltGraph3;
    private TimeGraph tg1, tg2, tg3;

    private Terrain terrain;
    private Population population;
    private float timer, updateGraphTime;
    private final float intervalUpdate = 1;

    //game
    private int mode = 1;
    private boolean pause = false;
    private boolean debug = false;

    @Override
    public void setup(PApplet p) {
        plt = new SubPlot(WorldConstants.WINDOW, viewport, p.width, p.height);
        pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
        pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
        pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);

        switch (mode) {
            case 1 -> {
                winGraph1[3] = 2 * refPopulation;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refPopulation, "Population");
                winGraph2[3] = 2 * refMeanMaxSpeed;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refMeanMaxSpeed, "MeanMaxSpeed");
                winGraph3[3] = 2 * refStdMaxSpeed;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refStdMaxSpeed, "StdMaxSpeed");
            }
            case 2 -> {
                winGraph1[3] = 2 * refPopulation;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refPopulation, "Population");
                winGraph2[3] = 2 * refMeanMaxSpeed;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refMeanMaxSpeed, "MeanMaxSpeed");
                winGraph3[3] = 2 * refMeanSize;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refMeanSize, "MeanSize");
            }
            case 3 -> {
                winGraph1[3] = 2 * refMeanSense;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refMeanSense, "MeanSense");
                winGraph2[3] = 2 * refMeanMaxSpeed;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refMeanMaxSpeed, "MeanMaxSpeed");
                winGraph3[3] = 2 * refMeanSize;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refMeanSize, "MeanSize");
            }
            case 4 -> {
                winGraph1[3] = 2 * refPopulation;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refPopulation, "Preys");
                float refPredators = 3;
                winGraph2[3] = 2 * refPredators;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refPredators, "Predators");
                winGraph3[3] = 2 * refMeanSize;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refMeanSize, "MeanSize");
            }
            case 5 -> {
                winGraph1[3] = 2 * refPopulation;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refPopulation, "Preys");
                float refPredators = 3;
                winGraph2[3] = 2 * refPredators;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refPredators, "Predators");
                float refVegan = 8;
                winGraph3[3] = 2 * refVegan;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refVegan, "Vegan");
            }
        }

        terrain = new Terrain(p, plt);
        terrain.setStateColors(getColors(p));
        if (mode != 4 && mode != 5)
            terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB1);
        if(mode == 4)
            terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB2);
        if(mode == 5)
            terrain.initRandomCustom(WorldConstants.PATCH_TYPE_PROB3);

        for (int i = 0; i < 2; i++) {
            terrain.majorityRule();
        }
        population = new Population(p, plt, terrain, mode);
        timer = 0;
        updateGraphTime = timer + intervalUpdate;
    }

    @Override
    public void draw(PApplet p, float dt) {
        if (!pause) {
            timer += dt;

            terrain.regenerate();
            population.update(dt, terrain, mode, p);

            terrain.display(p);
            population.display(p, plt, dt, debug);

            if (timer > updateGraphTime) {
                System.out.printf("time = %ds%n", (int) timer);
                System.out.println("numAnimals = " + population.getNumAnimals());
                switch (mode) {
                    case 1 -> {
                        System.out.println("MeanMaxSpeed = " + population.getMeanMaxSpeed());
                        System.out.println("StdMaxSpeed = " + population.getStdMaxSpeed());
                        System.out.println();
                        tg1.plot(timer, population.getNumAnimals());
                        tg2.plot(timer, population.getMeanMaxSpeed());
                        tg3.plot(timer, population.getStdMaxSpeed());
                        updateGraphTime = timer + intervalUpdate;
                    }
                    case 2 -> {
                        System.out.println("MeanMaxSpeed = " + population.getMeanMaxSpeed());
                        System.out.println("MeanSize = " + population.getMeanSize());
                        System.out.println();
                        tg1.plot(timer, population.getNumAnimals());
                        tg2.plot(timer, population.getMeanMaxSpeed());
                        tg3.plot(timer, population.getMeanSize());
                        updateGraphTime = timer + intervalUpdate;
                    }
                    case 3 -> {
                        System.out.println("MeanSense = " + population.getMeanSense());
                        System.out.println("MeanMaxSpeed = " + population.getMeanMaxSpeed());
                        System.out.println("MeanSize = " + population.getMeanSize());
                        System.out.println();
                        tg1.plot(timer, population.getMeanSense());
                        tg2.plot(timer, population.getMeanMaxSpeed());
                        tg3.plot(timer, population.getMeanSize());
                        updateGraphTime = timer + intervalUpdate;
                    }
                    case 4 -> {
                        System.out.println("numPreys = " + population.getNumOmni());
                        System.out.println("numPredators = " + population.getNumPredators());
                        System.out.println("MeanSize = " + population.getMeanSize());
                        System.out.println();
                        tg1.plot(timer, population.getNumOmni());
                        tg2.plot(timer, population.getNumPredators());
                        tg3.plot(timer, population.getMeanSize());
                        updateGraphTime = timer + intervalUpdate;
                    }
                    case 5 -> {
                        System.out.println("numPreys = " + population.getNumOmni());
                        System.out.println("numPredators = " + population.getNumPredators());
                        System.out.println("numVegan = " + population.getNumVegan());
                        System.out.println();
                        tg1.plot(timer, population.getNumOmni());
                        tg2.plot(timer, population.getNumPredators());
                        tg3.plot(timer, population.getNumVegan());
                        updateGraphTime = timer + intervalUpdate;
                    }
                }
            }
        }

    }

    @Override
    public void mousePressed(PApplet p) {
        if (!pause) {
            winGraph1[0] = timer;
            winGraph1[1] = timer + timeDuration;
            if (mode != 3) {
                if (mode == 4 || mode == 5){
                    winGraph1[3] = 2 * population.getNumOmni();
                    pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                    tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), population.getNumOmni(), "Prey");
                }else {
                    winGraph1[3] = 2 * population.getNumAnimals();
                    pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                    tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), population.getNumAnimals(), "Population");
                }
            }else {
                winGraph1[3] = 2 * refMeanSense;
                pltGraph1 = new SubPlot(winGraph1, viewGraph1, p.width, p.height);
                tg1 = new TimeGraph(p, pltGraph1, p.color(255, 0, 0), refMeanSense, "MeanSense");

            }


            winGraph2[0] = timer;
            winGraph2[1] = timer + timeDuration;
            if (mode!=3)
                if (mode==4 || mode == 5) {
                    winGraph2[3] = 2 * population.getNumPredators();
                    pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                    tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), population.getNumPredators(), "Predators");
                }
                else
                    tg2 = new TimeGraph(p, pltGraph2, p.color(255,0,0), refMeanMaxSpeed, "MeanMaxSpeed");
            else {
                winGraph2[3] = 2 * refMeanMaxSpeed;
                pltGraph2 = new SubPlot(winGraph2, viewGraph2, p.width, p.height);
                tg2 = new TimeGraph(p, pltGraph2, p.color(255, 0, 0), refMeanMaxSpeed, "MeanMaxSpeed");
            }

            winGraph3[0] = timer;
            winGraph3[1] = timer + timeDuration;
            if (mode==1)
                tg3 = new TimeGraph(p, pltGraph3, p.color(255,0,0), refStdMaxSpeed, "StdMaxSpeed");
            else if (mode == 5) {
                winGraph3[3] = 2 * population.getNumVegan();
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), population.getNumVegan(), "Vegan");
            }
            else {
                winGraph3[3] = 2 * refMeanSize;
                pltGraph3 = new SubPlot(winGraph3, viewGraph3, p.width, p.height);
                tg3 = new TimeGraph(p, pltGraph3, p.color(255, 0, 0), refMeanSize, "MeanSize");
            }
        }
        else
        {
            Patch patch = terrain.pixel2Cell(p.mouseX, p.mouseY);
            if (patch.getState()==WorldConstants.PatchType.EMPTY.ordinal())
                patch.setState(WorldConstants.PatchType.FOOD.ordinal());
            else
                patch.setState(WorldConstants.PatchType.EMPTY.ordinal());
            terrain.display(p);
            population.display(p,plt,1,debug);
        }

    }

    @Override
    public void keyPressed(PApplet p) {
        //se pressionar a tecla '1' A mutação ativa é a velocidade
        if (p.key== '1') {
            mode = 1;
            setup(p);
            System.out.println("A mutação ativa é a velocidade");
        }

        //se pressionar a tecla '2' As mutações ativas são a velocidade e o peso
        if (p.key== '2') {
            mode = 2;
            setup(p);
            System.out.println("As mutações ativas são a velocidade e o peso");
        }

        //se pressionar a tecla '3' As mutações ativas são a velocidade, o peso, e o sentido
        if (p.key== '3') {
            mode = 3;
            setup(p);
            System.out.println("As mutações ativas são a velocidade, o peso, e o sentido");
        }

        //se pressionar a tecla '4' modo de jogo QUADLIFE
        if (p.key== '4') {
            mode = 4;
            setup(p);
            System.out.println("Introdução dos canibais");
        }

        //se pressionar a tecla '5' modo de jogo LIFE_COLOR
        if (p.key== '5') {
            mode = 5;
            setup(p);
            System.out.println("Introdução dos vegetarianos");
        }

        //se pressionar a tecla do espaço pausa o jogo
        if (p.key== ' ') {
            pause = !pause;
            if (pause)
                System.out.println("O jogo está em pausa");
            else
                System.out.println("O jogo está a correr");
        }

        if (p.key== 'd' || p.key=='D') {
            if (mode != 1)
                debug= !debug;
            if (debug)
                System.out.println("O modo debug está ligado");
            else
                System.out.println("O modo debug está desligado");
        }
    }

    private int[] getColors(PApplet p)
    {
        int[] colors = new int[WorldConstants.NSTATES];
        for (int i = 0; i < WorldConstants.NSTATES; i++) {
            colors[i] = p.color(WorldConstants.TERRAIN_COLORS[i][0],
                    WorldConstants.TERRAIN_COLORS[i][1],
                    WorldConstants.TERRAIN_COLORS[i][2]);
        }
        return colors;
    }

    @Override
    public void mouseReleased(PApplet p) {

    }

    @Override
    public void mouseDragged(PApplet p) {

    }
}
