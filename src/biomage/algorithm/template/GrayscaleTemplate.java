/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.template;

import biomage.algorithm.iKernel;
import javax.swing.JPanel;

/**
 *
 * @author Marius
 */
public class GrayscaleTemplate implements iFilterTemplate {

    private final String id = "Grayscale";
    public iKernel kernel;
    @Override
    public JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
