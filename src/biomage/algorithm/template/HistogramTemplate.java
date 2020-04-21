/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm.template;

import javax.swing.JPanel;

/**
 *
 * @author Marius
 */
public class HistogramTemplate implements iFilterTemplate {

    private final String id = "Histogram";

    @Override
    public JPanel getPanel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getId() {
        return this.id;
    }

    //TODO
}
