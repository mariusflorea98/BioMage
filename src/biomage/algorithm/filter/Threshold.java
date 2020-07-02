/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.filter;

import biomage.algorithm.iFilter;
import biomage.algorithm.template.ThresholdTemplate;
import biomage.algorithm.template.iFilterTemplate; 
import java.awt.image.BufferedImage;

/**
 *
 * @author Marius
 */
public class Threshold implements iFilter {

    private final String id = "Threshold";
    ThresholdTemplate template;
    int threshold = 0;

    @Override
    public void apply(BufferedImage image) {
 
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int color = image.getRGB(x, y);

                int red = (color >>> 16) & 0xFF;
                int green = (color >>> 8) & 0xFF;
                int blue = (color >>> 0) & 0xFF; 
                
                float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
                 
                if (luminance*100  < threshold) {
                  
                    image.setRGB(x, y, 0x99000000);
                } else {
                    image.setRGB(x, y, 0x99FFFFFF);
                }
                
                 
            }
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public void loadTemplate(iFilterTemplate template) {
        this.template = (ThresholdTemplate) template;
        this.threshold = this.template.getLevel();
    }

    public iFilterTemplate getTemplate() {
        return this.template;
    }

}
