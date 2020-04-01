/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import java.awt.image.BufferedImage;

/**
 *
 * @author mariuster
 */
public class Blur implements iFilter {
    private final String id="Blur";
    
    public void execute(BufferedImage image){
        System.out.println("blur call");
    }
    
    public String getId(){
        return this.id;
    }
}
