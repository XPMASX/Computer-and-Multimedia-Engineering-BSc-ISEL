package ca;

import processing.core.PApplet;
import tools.SubPlot;

public class MajorityCA extends CellularAutomata{
    public MajorityCA(PApplet p, SubPlot plt, int nRows, int nCols, int nStates, int radius) {
        super(p, plt, nRows, nCols, nStates, radius);
    }

    @Override
    protected void createCells()
    {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                cells[i][j] = new MajorityCell(this, i, j);
            }
        }
        setMooreNeighbours();
    }

    public boolean majorityRule()
    {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                ((MajorityCell) cells[i][j]).computeHistogram();
            }
        }

        boolean anyChanged = false;
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                boolean changed =
                        ((MajorityCell) cells[i][j]).applyMajorityRule();
                if (changed) anyChanged =true;
            }
        }
        return anyChanged;
    }
}
