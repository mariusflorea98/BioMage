/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.view.gui.ImageResultGUI;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.DefaultListModel;
import javax.swing.WindowConstants;

/**
 *
 * @author Marius
 */
public class FilterProcessor {

    private BufferedImage image = null;
    private List<iFilter> filters = new ArrayList<>();
    private Object[] filterObjects;
    private iFilter filter = null;
    private ImageResultGUI imgResult;
    private DefaultListModel listModel = null;

    FilterProcessor() {
    }

    public FilterProcessor(DefaultListModel listModel) {

        this.listModel = listModel;
        this.filterObjects = this.listModel.toArray();

    }

    public void loadImage(BufferedImage img) {

        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(null);
        image = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void execute() {

        for (iFilter f : filters) {
            f.apply(image);
            display();

        }

    }

    public DefaultListModel getListModel() {
        return this.listModel;
    }

    private void display() {

        imgResult = new ImageResultGUI();
        imgResult.display(image);
        imgResult.setVisible(true);
        imgResult.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void create() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        for (Object o : filterObjects) {
            filter = (iFilter) Class.forName("biomage.algorithm." + (String) o).newInstance();
            filters.add(filter);
        }
    }

}
