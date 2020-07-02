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
    private boolean pixelDelete;
    private boolean convHullOp;
    private Color newColor;

    public FloodFillOptionsObj(final int iToleranta, final int fAFactor, final int nrVecini, Color newColor,
            boolean convHullOp) {

        this.iToleranta = iToleranta;
        this.fAFactor = fAFactor;
        this.nrVecini = nrVecini;
        this.convHullOp = convHullOp;
        this.newColor = newColor;
    }

    public boolean getDeleteOp() {
        return this.pixelDelete;
    }

    public void setDeleteOp(boolean opt) {
        this.pixelDelete = opt;
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

    public String toString() {
        return "Toleranta:" + iToleranta + " FA:" + fAFactor + " Vecini:" + nrVecini + " ConvexHull:" //+ onvHullOp
                + " Delete Pixels:" + pixelDelete;

    }

}
