package ca;

import processing.core.PApplet;

public class Cell {
    private int row,col;
    protected int state;
    protected CellularAutomata ca;
    private int color;
    private Cell[] neighbours;

    public Cell(CellularAutomata ca,int row, int col)
    {
        this.ca = ca;
        this.row  = row;
        this.col = col;
        this.state = 0;
        this.color = 0;
        this.neighbours = null;
    }

    public Cell[] getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(Cell[] neighbours) {
        this.neighbours = neighbours;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getState()
    {
        return this.state;
    }

    public void display(PApplet p)
    {
        p.pushStyle();
        p.noStroke();
        //se as cells tiverem vivas e possuirem cor quer dizer que estão no modo de jogo a cores logo a cor atribuida a uma cell viva vai ser o atributo color dessa mesma cell
        if (getState()==1 && color!=0)
            p.fill(color);
        else
            p.fill(ca.getStateColors()[state]);
        p.rect(ca.xmin + col*ca.getCellWidth(),ca.ymin + row*ca.getCellHeight(),ca.getCellWidth(),ca.getCellHeight());
        p.popStyle();
    }
}
