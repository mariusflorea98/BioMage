/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.InvertTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Marius
 */
public class Invert implements iFilter {
    private final String id = "Invert";
    InvertTemplate template;
    @Override
    public void apply(BufferedImage image) {
        
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgba = image.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                image.setRGB(x, y, col.getRGB());
            }
        }
    }

    @Override
    public String getId() {
      return this.id;
    }

    @Override
    public void loadTemplate(iFilterTemplate template) {
      this.template=(InvertTemplate) template;
    }

   public iFilterTemplate getTemplate() {
        return this.template;
    }

    
    
}
