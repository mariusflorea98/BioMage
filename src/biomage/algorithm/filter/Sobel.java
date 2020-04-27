/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.filter;
 
import biomage.algorithm.Convolution;
import biomage.algorithm.iFilter;
import biomage.algorithm.template.SobelTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marius
 */
public class Sobel extends Convolution implements iFilter {

    private final String id = "Sobel";
    SobelTemplate template;
    
    public void loadTemplate(iFilterTemplate sTemp) {
         
        template = (SobelTemplate) sTemp;
        direction = template.getDirection();
        kernel = template.getKernel();
       

    }

    public void apply(BufferedImage image) {

        try { 
            convolve(image);
        } catch (IOException ex) {
            Logger.getLogger(Sobel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public iFilterTemplate getTemplate() {
        return template;
    }

}
