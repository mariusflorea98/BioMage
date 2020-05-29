/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model.flood;

import biomage.algorithm.filter.Histogram;
import biomage.model.iLayer;
import java.util.Vector;

/**
 *
 * @author Marius
 */
public class FloodData {


    public int iTotalArea;
    public int regions = 0;
    public int pixels_inInterval = 0;
    public int pixels_belowInterval = 0;
    public int pixels_aboveInterval = 0;
    public float avgRegionsLum = 0;
    public float rejected_avgRegionsLum = 0;
    public Histogram crudeHisto;

    public iLayer floodLayer;
    public iLayer hullLayer;
    public boolean[][] pixel_visited;
    public float[][] luminance;
    //public Vector<ConvexHullObj> hulls; 
    // public ConvexHullOptionsObj convOptions;

    public FloodData(int iTotalArea, iLayer floodLayer, iLayer hullLayer, boolean[][] pixel_visited, float[][] luminance) {

        this.iTotalArea = iTotalArea;
        this.floodLayer = floodLayer;
        this.hullLayer = hullLayer;
        this.pixel_visited = pixel_visited;
        this.luminance = luminance;
        //this.hulls=new Vector();
    }

    public FloodData() {
    }
}
