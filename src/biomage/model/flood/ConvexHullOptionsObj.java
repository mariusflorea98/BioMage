/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model.flood;

/**
 *
 * @author User
 */
public class ConvexHullOptionsObj {
    public float areaLower, areaUpper;
    public float selectedLower, selectedUpper;
    
    public ConvexHullOptionsObj(int areaLower, int areaUpper, float selLower, float selUpper){
        this.areaLower= (float)areaLower;
        this.areaUpper=(float)areaUpper;
        this.selectedLower=selLower;
        this.selectedUpper=selUpper;
        
    }
    
    public String toString(){
    return this.areaLower+" "+this.areaUpper+" "+this.selectedLower+" "+this.selectedUpper;
    
    }   
}
