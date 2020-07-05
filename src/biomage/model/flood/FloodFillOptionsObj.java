package biomage.model.flood;

import java.awt.Color;

/**
 *
 * @author Marius
 */
public class FloodFillOptionsObj {

    final private int iToleranta;
    final private int fAFactor;
    final private int nrVecini;
    private boolean export;
    private boolean convHullOp;
    private boolean showHull;
    private Color newColor;

    public FloodFillOptionsObj(final int iToleranta, final int fAFactor, final int nrVecini, Color newColor,
            boolean convHullOp, boolean showHull, boolean export) {

        this.iToleranta = iToleranta;
        this.fAFactor = fAFactor;
        this.nrVecini = nrVecini;
        this.convHullOp = convHullOp;
        this.newColor = newColor;
        this.showHull = showHull;
        this.export = export;
    }

    public boolean getExport() {
        return this.export;
    }

    public void setExport(boolean opt) {
        this.export = opt;
    }

    public boolean getShowHull() {
        return this.showHull;
    }

    public void setShowHull(boolean show) {
        this.showHull = show;
    }

    public boolean getConvHullOp() {
        return this.convHullOp;
    }

    public void setConvHullOp(boolean opt) {
        this.convHullOp = opt;
    }

    public int getToleranta() {
        return iToleranta;
    }

    public int getFAFactor() {
        return fAFactor;
    }

    public int getVecini() {
        return nrVecini;
    }

    public Color getColor() {
        return this.newColor;
    }

    public String toString() {
        return "Toleranta:" + iToleranta + " FA:" + fAFactor + " Vecini:" + nrVecini + " ConvexHull:"
                + " Exported Pixels:" + export;

    }

}
