/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.filter;

import biomage.algorithm.iFilter;
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
        int pixel;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                pixel = image.getRGB(i, j);

                int a = (pixel >> 24) & 0xff;
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;

                int average = (r + g + b) / 3;

                pixel = (a << 24) | (average << 16) | (average << 8) | average;

                image.setRGB(i, j, pixel);
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
