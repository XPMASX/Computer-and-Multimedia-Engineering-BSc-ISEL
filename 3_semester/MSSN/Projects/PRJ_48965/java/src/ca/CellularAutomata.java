package ca;

import processing.core.PApplet;
import processing.core.PVector;
import tools.CustomRandomGenerator;
import tools.SubPlot;

import java.util.Random;

public class CellularAutomata {
    protected int nRows;
    protected int nCols;
    protected int nStates;
    private int radiusNeigh;
    protected Cell[][] cells;
    private int[] colors;
    protected float cellWidth, cellHeight; //pixels
    protected float xmin,ymin;
    private SubPlot plt;

    public CellularAutomata(PApplet p, SubPlot plt, int nRows, int nCols, int nStates, int radiusNeigh)
    {
        this.nRows= nRows;
        this.nCols= nCols;
        this.nStates= nStates;
        this.radiusNeigh = radiusNeigh;
        cells = new Cell[nRows][nCols];
        colors = new int[nStates];
        float[] bb = plt.getBoundingBox();
        xmin = bb[0];
        ymin = bb[1];
        cellWidth = bb[2]/nCols;
        cellHeight = bb[3]/nRows;
        this.plt = plt;
        createCells();
        setRandColors(p);
    }

    public float getCellWidth() {
        return cellWidth;
    }

    public float getCellHeight() {
        return cellHeight;
    }

    //da cor a cada estado
    public void setStateColors(PApplet p)
    {
        colors[0] = p.color(50);
        //de acordo com o número de estados sabe em que modo de jogo esta logo sabe que cores colocar
        switch (nStates){

            case 2:
                colors[1] = p.color(0,255,0);
                break;
            case 3:
                colors[1] = p.color(255,0,0);
                colors[2] = p.color(0,0,255);
                break;
            case 5:
                colors[1] = p.color(255,0,0);
                colors[2] = p.color(0,0,255);
                colors[3] = p.color(0,255,0);
                colors[4] = p.color(255,255,0);
                break;
        }
    }

    public void setStateColors(int[] colors)
    {
        this.colors = colors;
    }

    public int[] getStateColors()
    {
        return colors;
    }

    //coloca cores random as celulas vivas
    //e utilizada na inicializacao do modo de jogo 5
    public void setColors(PApplet p)
    {
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nCols;j++) {
                if (cells[i][j].getState()==1)
                    cells[i][j].setColor(p.color(p.random(255),p.random(255),p.random(255)));
            }
        }
    }

    public void setRandColors(PApplet p)
    {
        for (int i = 0; i < nStates; i++) {
            colors[i] = p.color(p.random(255),p.random(255),p.random(255));
        }
    }

    //cria as celulas
    protected void createCells()
    {
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nCols;j++) {
                cells[i][j] = new Cell(this,i,j);
            }
        }
        setMooreNeighbours();
    }

    //inicializa os estados das celulas
    public void init()
    {
        for (int i=0;i<nRows;i++) {
            for (int j = 0; j < nCols; j++) {
                Random randomNum = new Random();
                int stat = 1 + randomNum.nextInt(100);
                int state = 0;
                if (stat > 15) {
                    state = 0;
                }
                else {
                    //de acordo com o numero de estados sabe qual as regras de cada modo de jogo
                    switch (nStates)
                    {
                        case 2:
                            state = 1;
                            break;
                            //existe 50% chances de calhar vermelho ou azul
                        case 3:
                            if (1 + randomNum.nextInt(100) > 50)
                                state = 1;
                            else
                                state = 2;
                            break;
                        //existe 25% chances para cada cor
                        case 5:
                            int rando = 1 + randomNum.nextInt(100);
                            if (rando>75)
                                state = 1;
                            else if (rando>50)
                                state = 2;
                            else if (rando>25)
                                state = 3;
                            else
                                state = 4;
                            break;
                    }
                }
                cells[i][j].setState(state);
            }
        }
    }

    public void initRandom()
    {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                cells[i][j].setState((int) (nStates*Math.random()));
            }
        }
    }

    public void initRandomCustom(double[] pmf)
    {
        CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                cells[i][j].setState(crg.getRandomClass());
            }
        }
    }

    public PVector getCenterCell(int row, int col)
    {
        float x = (col + 0.5f) * cellWidth;
        float y = (row + 0.5f) * cellHeight;
        double[] w = plt.getWorldCoord(x,y);
        return new PVector((float) w[0], (float) w[1]);
    }

    public Cell world2Cell(double x, double y)
    {
        float[] xy = plt.getPixelCoord(x,y);
        return pixel2Cell(xy[0],xy[1]);
    }


    //devolve a celula a partir das coordenadas x e y
    public Cell pixel2Cell(float x, float y)
    {
        int row = (int) ((y-ymin)/cellHeight);
        int col = (int) ((x-xmin)/cellWidth);
        if (row >= nRows)
            row = nRows - 1;
        if (col >= nCols)
            col = nCols - 1;
        if (row < 0) row = 0;
        if (col < 0) col = 0;
        return cells[row][col];
    }

    public int getAliveNeighbours(int[][] cellsBuffer,int i, int j )
    {
        int neighbours = 0;
        for (int ii=i-1; ii<=i+1;ii++) {
            for (int jj=j-1; jj<=j+1;jj++) {
                if (((ii>=0)&&(ii<nRows))&&((jj>=0)&&(jj<nCols))) {
                    if (!((ii==i)&&(jj==j))) {
                        if (cellsBuffer[ii][jj]==1)
                            neighbours ++;
                    }
                }
            }
        }
        return neighbours;
    }

    protected void setMooreNeighbours()
    {
        int NN = (int) Math.pow(2*radiusNeigh+1,2);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                Cell[] neigh = new Cell[NN];
                int n = 0;
                for (int ii =- radiusNeigh; ii <= radiusNeigh; ii++) {
                    int row = (i +ii + nRows) % nRows;
                    for (int jj =- radiusNeigh; jj <= radiusNeigh; jj++) {
                        int col = (j + jj + nCols) %nCols;
                        neigh[n++] = cells[row][col];
                    }
                }
                cells[i][j].setNeighbours(neigh);
            }
        }
    }

    //modo de jogo classico 23/3
    public void Life() {

        int[][] cellsBuffer = new int[nRows][nCols];

        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cellsBuffer[x][y] = cells[x][y].getState();
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int neighbours = getAliveNeighbours(cellsBuffer,i,j);
                if (cellsBuffer[i][j]==1) {
                    if (neighbours < 2 || neighbours > 3)
                        cells[i][j].setState(0);
                }
                else {
                    if (neighbours == 3 )
                        cells[i][j].setState(1);
                }
            }
        }
    }

    //modo de jogo 23/36
    public void HighLife() {

        int[][] cellsBuffer = new int[nRows][nCols];

        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cellsBuffer[x][y] = cells[x][y].getState();
            }
        }
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int neighbours = getAliveNeighbours(cellsBuffer,i,j);
                if (cellsBuffer[i][j]==1) {
                    if (neighbours < 2 || neighbours > 3)
                        cells[i][j].setState(0);
                }
                else {
                    if (neighbours == 3 || neighbours==6 )
                        cells[i][j].setState(1);
                }
            }
        }
    }

    //modo de jogo red/blue
    public void Immigration() {

        int[][] cellsBuffer = new int[nRows][nCols];

        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cellsBuffer[x][y] = cells[x][y].getState();
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int red = 0;
                int blue = 0;
                for (int ii=i-1; ii<=i+1;ii++) {
                    for (int jj=j-1; jj<=j+1;jj++) {
                        if (((ii>=0)&&(ii<nRows))&&((jj>=0)&&(jj<nCols))) {
                            if (!((ii==i)&&(jj==j))) {
                                //conta o numero de vizinhos vermelhos e azuis
                                if (cellsBuffer[ii][jj]==1)
                                    red ++;
                                if (cellsBuffer[ii][jj]==2)
                                    blue++;
                            }
                        }
                    }
                }
                if (cellsBuffer[i][j]==1 || cellsBuffer[i][j]==2) {
                    if (red + blue < 2 || red + blue > 3)
                        cells[i][j].setState(0);
                }
                else {
                    //se o num de vizinhos vermelhos for maior que os azuis a cell nasce vermelha
                    if (red + blue == 3 )
                        if (red > blue)
                            cells[i][j].setState(1);
                        else
                            cells[i][j].setState(2);
                }
            }
        }
    }

    //modo de jogo red/blue/green/yellow
    public void QuadLife() {

        int[][] cellsBuffer = new int[nRows][nCols];

        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cellsBuffer[x][y] = cells[x][y].getState();
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int red = 0;
                int blue = 0;
                int green = 0;
                int yellow = 0;
                for (int ii=i-1; ii<=i+1;ii++) {
                    for (int jj=j-1; jj<=j+1;jj++) {
                        if (((ii>=0)&&(ii<nRows))&&((jj>=0)&&(jj<nCols))) {
                            if (!((ii==i)&&(jj==j))) {
                                //conta o numero de vizinhos vermelhos, azuis, verdes e amarelos
                                if (cellsBuffer[ii][jj]==1)
                                    red ++;
                                if (cellsBuffer[ii][jj]==2)
                                    blue++;
                                if (cellsBuffer[ii][jj]==3)
                                    green++;
                                if (cellsBuffer[ii][jj]==4)
                                    yellow++;
                            }
                        }
                    }
                }
                if (cellsBuffer[i][j]==1 || cellsBuffer[i][j]==2 || cellsBuffer[i][j]==3 || cellsBuffer[i][j]==4) {
                    if (red + blue + green + yellow < 2 || red + blue + green + yellow > 3)
                        cells[i][j].setState(0);
                }
                else {
                    //se a maioria dos vizinhos for de uma cor a cell nasce dessa cor
                    //se houver empate a cell nasce com a cor nao presente
                    if (red + blue + green + yellow == 3 ) {
                        if (red == 2 || red == 3)
                            cells[i][j].setState(1);
                        if (blue == 2 || blue == 3)
                            cells[i][j].setState(2);
                        if (green == 2 || green == 3)
                            cells[i][j].setState(3);
                        if (yellow == 2 || yellow == 3)
                            cells[i][j].setState(4);
                        if (red == 1 && blue == 1 && green == 1)
                            cells[i][j].setState(4);
                        if (red == 1 && blue == 1 && yellow == 1)
                            cells[i][j].setState(3);
                        if (red == 1 && yellow == 1 && green == 1)
                            cells[i][j].setState(2);
                        if (yellow == 1 && blue == 1 && green == 1)
                            cells[i][j].setState(1);
                    }
                }
            }
        }
    }

    //devolve a cor dominante entre os vizinhos
    public int getDominantColor(int[][] cellsBuffer,int i, int j )
    {
        int counter=0;
        int[] colors = new int[3];

        for (int ii=i-1; ii<=i+1;ii++) {
            for (int jj=j-1; jj<=j+1;jj++) {
                if (((ii>=0)&&(ii<nRows))&&((jj>=0)&&(jj<nCols))) {
                    if (!((ii==i)&&(jj==j))) {
                        if (cellsBuffer[ii][jj]==1) {
                            colors[counter] = cells[ii][jj].getColor();
                            counter++;
                        }
                    }
                }
            }
        }
        Random randomNum = new Random();
        //se existir uma cor dominante devolve essa cor
        //se nao devolve uma delas aleatoriamente
        if (colors[0] == colors[1])
            return colors[0];
        if (colors[0] == colors[2])
            return colors[0];
        if (colors[1] == colors[2])
            return colors[1];
        else
            return colors[1 + randomNum.nextInt(2)];

    }

    //modo de jogo classico a cores
    public void Life_Color() {

        int[][] cellsBuffer = new int[nRows][nCols];

        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cellsBuffer[x][y] = cells[x][y].getState();
            }
        }

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                int neighbours = getAliveNeighbours(cellsBuffer,i,j);
                if (cellsBuffer[i][j]==1) {
                    if (neighbours < 2 || neighbours > 3)
                        cells[i][j].setState(0);
                }
                else {
                    if (neighbours == 3 ) {
                        cells[i][j].setState(1);
                        cells[i][j].setColor(getDominantColor(cellsBuffer,i,j));
                    }
                }
            }
        }
    }

    //limpa a grelha matando todas as cells
    public void clear()
    {
        for (int x = 0; x<nRows; x++) {
            for (int y=0; y<nCols; y++) {
                cells[x][y].setState(0);
            }
        }
    }

    public void display(PApplet p)
    {
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nCols;j++) {
                cells[i][j].display(p);
            }
        }
    }

}
