/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

/**
 *
 * @author Marius
 */
public class Kernel {

    public iKernel Sobel() {
        return new iKernel() {
            @Override
            public float[][] getKernel() {
                return new float[][]{
                    {-1f, 0, 1f},
                    {-2f, 0, 2f},
                    {-1f, 0, 1f}};
            }

        };
    }

    public iKernel BoxBlur() {
        return new iKernel() {

            @Override
            public float[][] getKernel() {
                return new float[][]{
                    {1f / 9, 1f / 9, 1f / 9},
                    {1f / 9, 1f / 9, 1f / 9},
                    {1f / 9, 1f / 9, 1f / 9}};
            }

        };
    }

    public iKernel GaussianBlur() {
        return new iKernel() {

            @Override
            public float[][] getKernel() {
                return new float[][]{
                    {1f / 16, 2f / 16, 1f / 16},
                    {2f / 16, 4f / 16, 2f / 16},
                    {1f / 16, 2f / 16, 1f / 16}};
            }

        };
    }

    public iKernel Sharpen() {
        return new iKernel() {

            public float[][] getKernel() {
                return new float[][]{
                    {0, -1, 0},
                    {-1, 5, -1},
                    {0, -1, 0}};
            }

        };
    }

    public iKernel Emboss() {
        return new iKernel() {

            public float[][] getKernel() {
                return new float[][]{
                    {-2, -1, 0},
                    {-1, 0, -1},
                    {0, 1, 2}};
            }

        };
    }

}
