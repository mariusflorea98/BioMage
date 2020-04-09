/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.GrayscaleTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.awt.image.BufferedImage;

/**
 *
 * @author Marius
 */
public class Grayscale implements iFilter {

    private final String id = "Grayscale";
    GrayscaleTemplate template;

    Grayscale() {
    }

    @Override
    public void apply(BufferedImage image) {
        int x = image.getWidth();
        int y = image.getHeight();
        int p;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                p = image.getRGB(i, j);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                int avg = (r + g + b) / 3;

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(i, j, p);
            }

        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void loadTemplate(iFilterTemplate template) {
        this.template = (GrayscaleTemplate) template;
    }

    @Override
    public iFilterTemplate getTemplate() {
        return this.template;
    }

}
