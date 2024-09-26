package CA;

import processing.core.PApplet;

import java.util.Random;

public class CellularAutomata {
    private int nRows;
    private int nCols;
    private int nStates;
    private Cell[][] cells;
    private int[] colors;
    private int cellWidth, cellHeight;

    public CellularAutomata(PApplet p,int nRows, int nCols, int nStates)
    {
        this.nRows= nRows;
        this.nCols= nCols;
        this.nStates= nStates;
        cells = new Cell[nRows][nCols];
        colors = new int[nStates];
        cellWidth = p.width/nCols;
        cellHeight = p.height/nRows;
        createCells();
        setStateColors(p);
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    //da cor a cada estado
    public void setStateColors(PApplet p)
    {
        colors[0] = p.color(50);
        //de acordo com o numero de estados sabe em que modo de jogo esta logo sabe que cores colocar
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

    //cria as celulas
    private void createCells()
    {
        for (int i=0;i<nRows;i++){
            for (int j=0;j<nCols;j++) {
                cells[i][j] = new Cell(this,i,j);
            }
        }
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

    //devolve a celula a partir das coordenadas x e y
    public Cell pixel2Cell(int x, int y)
    {
        int row = y/cellHeight;
        int col = x/cellWidth;
        if (row >= nRows)
            row = nRows - 1;
        if (col >= nCols)
            col = nCols - 1;
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
