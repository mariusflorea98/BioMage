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
public class SobelTemplate implements iFilterTemplate {
    
    private final String id = "SobelTemplate";
    private iKernel kernel;

    public SobelTemplate() {

        KernelFactory kf = new KernelFactory();
        this.kernel = kf.Sobel();

    }

    public iKernel getKernel() {
        return this.kernel;
    }

    public JPanel getPanel() {
        return null;
      
    }

    public String getId(){
        return this.id;
    }
  

}
