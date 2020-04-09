/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.BlurTemplate; 
import biomage.algorithm.template.iFilterTemplate;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mariuster
 */
public class Blur implements iFilter {

    private final String id = "Blur";

    private iKernel kernel;
    private float[][] datele;
    BlurTemplate template;

    public void apply(BufferedImage image) {
        try {
            Operator(image);
        } catch (IOException ex) {
            Logger.getLogger(Sobel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void Operator(BufferedImage image) throws IOException {

        int x = image.getWidth();
        int y = image.getHeight();

        int[][] edgeColors = new int[x][y];
        int maxGradient = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int val00 = getGrayScale(image.getRGB(i - 1, j - 1));
                int val01 = getGrayScale(image.getRGB(i - 1, j));
                int val02 = getGrayScale(image.getRGB(i - 1, j + 1));

                int val10 = getGrayScale(image.getRGB(i, j - 1));
                int val11 = getGrayScale(image.getRGB(i, j));
                int val12 = getGrayScale(image.getRGB(i, j + 1));

                int val20 = getGrayScale(image.getRGB(i + 1, j - 1));
                int val21 = getGrayScale(image.getRGB(i + 1, j));
                int val22 = getGrayScale(image.getRGB(i + 1, j + 1));

                int gx = (int) (((datele[0][0] * val00) + (datele[0][1] * val01) + (datele[0][2] * val02))
                        + ((datele[1][0] * val10) + (datele[1][1] * val11) + (datele[1][2] * val12))
                        + ((datele[2][0] * val20) + (datele[2][1] * val21) + (datele[2][2] * val22)));

                int gy = (int) (((datele[0][2] * val00) + (datele[1][2] * val01) + (datele[2][2] * val02))
                        + ((datele[0][1] * val10) + (datele[1][1] * val11) + (datele[2][1] * val12))
                        + ((datele[0][0] * val20) + (datele[1][0] * val21) + (datele[2][0] * val22)));

                double gval = Math.sqrt((gx * gx) + (gy * gy));
                int g = (int) gval;

                if (maxGradient < g) {
                    maxGradient = g;
                }

                edgeColors[i][j] = g;
            }
        }

        double scale = 255.0 / maxGradient;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int edgeColor = edgeColors[i][j];
                edgeColor = (int) (edgeColor * scale);
                edgeColor = 0xff000000 | (edgeColor << 16) | (edgeColor << 8) | edgeColor;

                image.setRGB(i, j, edgeColor);
            }
        }

    }

    private int getGrayScale(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

        return gray;
    }

    public void loadTemplate(iFilterTemplate bTemp) {
        template = (BlurTemplate) bTemp;
        kernel = template.getKernel();
        datele = kernel.getKernel();
    }

    public String getId() {
        return this.id;
    }

    @Override
    public iFilterTemplate getTemplate() {
        return this.template;
    }

}
