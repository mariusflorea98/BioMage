/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model.flood;

import biomage.model.Punct;
import java.util.List;

/**
 *
 * @author User
 */
public class ConvexHullObj {

    public List<Punct> points;
    public double area;
    public int selected_pixels;
    public float roundness;

    public ConvexHullObj() {
    }

    public ConvexHullObj(List<Punct> points, double area, int selected_pixels) {
        this.points = points;
        this.area = area;
        this.selected_pixels = selected_pixels;

    }

    public String toString() {

        return " Puncte: " + this.points + " Arie: " + this.area + " Pixels: " + this.selected_pixels;
    }

}
