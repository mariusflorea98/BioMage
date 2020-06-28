package biomage.model.flood;

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

    public FloodFillOptionsObj(final int iToleranta, final int fAFactor, final int nrVecini, boolean pixelDelete,
            boolean convHullOp) {

        this.iToleranta = iToleranta;
        this.fAFactor = fAFactor;
        this.nrVecini = nrVecini;
        this.pixelDelete = pixelDelete;
        this.convHullOp = convHullOp;
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
