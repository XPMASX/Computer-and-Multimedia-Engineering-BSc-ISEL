package ecosystem;

import ca.Cell;
import ca.MajorityCA;
import physics.Body;
import processing.core.PApplet;
import tools.SubPlot;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends MajorityCA {
    public Terrain(PApplet p, SubPlot plt) {
        super(p, plt, WorldConstants.NROWS, WorldConstants.NCOLS, WorldConstants.NSTATES, 1);
    }

    @Override
    protected void createCells()
    {
        int minRT = (int) (WorldConstants.REGENARATION_TIME[0]*1000);
        int maxRT = (int) (WorldConstants.REGENARATION_TIME[1]*1000);
        for (int i = 0; i < WorldConstants.NROWS; i++) {
            for (int j = 0; j < WorldConstants.NCOLS; j++) {
                int timeToGrow = (int) (minRT+(maxRT-minRT)*Math.random());
                cells[i][j] = new Patch(this, i, j, timeToGrow);
            }
        }
        setMooreNeighbours();

    }

    public void regenerate()
    {
        for (int i = 0; i < WorldConstants.NROWS; i++) {
            for (int j = 0; j < WorldConstants.NCOLS; j++) {
                ((Patch)cells[i][j]).regenerate();
            }
        }
    }

    public List<Body> getObstacles()
    {
        List<Body> bodies = new ArrayList<Body>();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (cells[i][j].getState() == WorldConstants.PatchType.OBSTACLE.ordinal()){
                    Body b = new Body(this.getCenterCell(i, j));
                    bodies.add(b);
                }
            }
        }
        return bodies;
    }

    public Patch pixel2Cell(float x, float y)
    {
        int row = (int) ((y-ymin)/cellHeight);
        int col = (int) ((x-xmin)/cellWidth);
        if (row >= nRows)
            row = nRows - 1;
        if (col >= nCols)
            col = nCols - 1;
        if (row < 0) row = 0;
        if (col < 0) col = 0;
        return (Patch) cells[row][col];
    }

    public List<Body> getFood()
    {
        List<Body> bodies = new ArrayList<Body>();
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                if (cells[i][j].getState() == WorldConstants.PatchType.FOOD.ordinal()){
                    Body b = new Body(this.getCenterCell(i, j));
                    bodies.add(b);
                }
            }
        }
        return bodies;
    }
}
