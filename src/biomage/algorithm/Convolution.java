/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Marius
 */
public class Convolution {

    protected iKernel kernel;
    private float[][] matrix;
    double scale = 1;
    String direction;
    int gx, gy;

    Convolution() {

    }

    protected void convolve(BufferedImage image) throws IOException {

        matrix = kernel.getKernel();
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

                gx = (int) (((matrix[0][0] * val00) + (matrix[0][1] * val01) + (matrix[0][2] * val02))
                        + ((matrix[1][0] * val10) + (matrix[1][1] * val11) + (matrix[1][2] * val12))
                        + ((matrix[2][0] * val20) + (matrix[2][1] * val21) + (matrix[2][2] * val22)));

                switch (direction) {
                    case "HORIZONTAL":
                        edgeColors[i][j] = gx;
                        break;

                    case "GRADIENT":
                        gy = (int) (((matrix[0][2] * val00) + (matrix[1][2] * val01) + (matrix[2][2] * val02))
                                + ((matrix[0][1] * val10) + (matrix[1][1] * val11) + (matrix[2][1] * val12))
                                + ((matrix[0][0] * val20) + (matrix[1][0] * val21) + (matrix[2][0] * val22)));

                        double gval = Math.sqrt((gx * gx) + (gy * gy));
                        int g = (int) gval;

                        if (maxGradient < g) {
                            maxGradient = g;
                        }

                        edgeColors[i][j] = g;
                        break;

                }

            }
        }

        if (direction.equals("GRADIENT")) {
            scale = 255.0 / maxGradient;
        }

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

}
