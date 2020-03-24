/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Layer implements iLayer {

    private BufferedImage layerImage;

    public Layer() {
        layerImage = null;
    }

    public Layer(BufferedImage img) {
        loadLayer(img);
    }

    public void loadLayer(BufferedImage img) {

        layerImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

    }

    @Override
    public BufferedImage getLayer() {
        return this.layerImage;
    }

    @Override
    public void paintComponent(Graphics g) {

        if (layerImage != null) {
            g.drawImage(layerImage, 0, 0, null);

        }
    }

}
