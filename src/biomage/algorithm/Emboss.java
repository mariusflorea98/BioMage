/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.EmbossTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marius
 */
public class Emboss extends Convolution implements iFilter {

    private final String id = "Emboss";
    EmbossTemplate template;

    @Override
    public void apply(BufferedImage image) {
        try {
            convolve(image);
        } catch (IOException ex) {
            Logger.getLogger(Emboss.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadTemplate(iFilterTemplate bTemp) {
        template = (EmbossTemplate) bTemp;
        kernel = template.getKernel();
        direction = template.getDirection();

    }

    public String getId() {
        return this.id;
    }

    public iFilterTemplate getTemplate() {
        return this.template;
    }
}
