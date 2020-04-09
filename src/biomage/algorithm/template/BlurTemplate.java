/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.template;

import biomage.algorithm.KernelFactory;
import biomage.algorithm.iKernel;
import javax.swing.JPanel;

/**
 *
 * @author Marius
 */
public class BlurTemplate implements iFilterTemplate {

    private final String id = "BlurTemplate";

    @Override
    public JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private iKernel kernel;

    public BlurTemplate() {

        KernelFactory kf = new KernelFactory();
        this.kernel = kf.GaussianBlur();

    }

    public iKernel getKernel() {
        return this.kernel;
    }

    public String getId() {
        return this.id;
    }

}
