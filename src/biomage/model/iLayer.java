/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.model;
 
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public interface iLayer {
	public void paintComponent(final Graphics g);
        public void loadImage(BufferedImage img);
        public BufferedImage getImage();
}
