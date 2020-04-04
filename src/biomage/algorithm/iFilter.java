/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import java.awt.image.BufferedImage;

/**
 *
 * @author mariuster
 */
public interface iFilter {

    public void apply(BufferedImage image);

    public String getId();
     

}
