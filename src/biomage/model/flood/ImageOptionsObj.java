/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model.flood;

/**
 *
 * @author Marius
 */
public class ImageOptionsObj {

    final private int iArieMax;
    final private int iArieMin;
    final private float average_lum_max;
    final private float average_lum_min;

    public ImageOptionsObj(final int iArieMin, final int iArieMax, final float average_lum_min
                ,final float average_lum_max) {
        this.iArieMin = iArieMin;
        this.iArieMax= iArieMax;
        this.average_lum_min=average_lum_min;
        this.average_lum_max = average_lum_max;
        
    }

   public int getArieMax(){
    return iArieMax;}
    
   public int getArieMin(){
        return iArieMin;
    }
    
   public float getAverageLumMax(){
    return average_lum_max;}
    
   public float getAverageLumMin(){
        return average_lum_min;
    }
    
    
    public String toString(){
          return "ArieMin:"+iArieMin+" ArieMax:"+iArieMax+" AvgMin:"+average_lum_min+" AvgMax:"+average_lum_max;

    }
    
    
    
}
