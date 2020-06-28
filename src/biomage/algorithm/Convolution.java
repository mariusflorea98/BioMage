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
    double scaledGradient = 1;
    protected String direction;
    int gx, gy;

    protected Convolution() {

    }

    protected void convolve(BufferedImage image) throws IOException {

        matrix = kernel.getKernel();
        int x = image.getWidth();
        int y = image.getHeight();

        int[][] edges = new int[x][y];
        int maxG = -1;

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {

                int kernel00 = BW(image.getRGB(i - 1, j - 1));
                int kernel01 = BW(image.getRGB(i - 1, j));
                int kernel02 = BW(image.getRGB(i - 1, j + 1));
                int kernel10 = BW(image.getRGB(i, j - 1));
                int kernel11 = BW(image.getRGB(i, j));
                int kernel12 = BW(image.getRGB(i, j + 1));
                int kernel20 = BW(image.getRGB(i + 1, j - 1));
                int kernel21 = BW(image.getRGB(i + 1, j));
                int kernel22 = BW(image.getRGB(i + 1, j + 1));

                gx = (int) (((matrix[0][0] * kernel00) + (matrix[0][1] * kernel01) + (matrix[0][2] * kernel02))
                        + ((matrix[1][0] * kernel10) + (matrix[1][1] * kernel11) + (matrix[1][2] * kernel12))
                        + ((matrix[2][0] * kernel20) + (matrix[2][1] * kernel21) + (matrix[2][2] * kernel22)));

                switch (direction) {
                    case "HORIZONTAL":
                        edges[i][j] = gx;
                        break;

                    case "GRADIENT":
                        gy = (int) (((matrix[0][2] * kernel00) + (matrix[1][2] * kernel01) + (matrix[2][2] * kernel02))
                                + ((matrix[0][1] * kernel10) + (matrix[1][1] * kernel11) + (matrix[2][1] * kernel12))
                                + ((matrix[0][0] * kernel20) + (matrix[1][0] * kernel21) + (matrix[2][0] * kernel22)));

                        int g = (int) Math.sqrt((gx * gx) + (gy * gy));

                        if (maxG < g) {
                            maxG = g;
                        }

                        edges[i][j] = g;
                        break;

                }

            }
        }

        if (direction.equals("GRADIENT")) {
            scaledGradient = 255.0 / maxG;
        }

        for (int i = 1; i < x - 1; i++) {
            for (int j = 1; j < y - 1; j++) {
                int newColor = edges[i][j];
                newColor = (int) (newColor * scaledGradient);
                newColor = 0xff000000 | (newColor << 16) | (newColor << 8) | newColor;

                image.setRGB(i, j, newColor);
            }
        }

    }

    private int BW(int rgb) {
        int r = (rgb >> 16) & 0xff;
        int g = (rgb >> 8) & 0xff;
        int b = (rgb) & 0xff;
        int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);

        return gray;
    }

}
