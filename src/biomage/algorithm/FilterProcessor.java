/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.template.iFilterTemplate;
import biomage.view.gui.ImageResultGUI;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.WindowConstants;

/**
 *
 * @author Marius
 */
public class FilterProcessor {

    private BufferedImage[] images = null;
    private List<iFilter> filters = new ArrayList<>();
    private int x = 0, y = 0;
    private iFilter filter = null;
    private ImageResultGUI imgResult;
    private List<iFilterTemplate> templates = new ArrayList<>();

    FilterProcessor() {
    }

    public void close() {
        if (imgResult != null) {
            imgResult.dispose();
        }
    }

    public FilterProcessor(List<iFilterTemplate> temps) {
        this.templates = temps;
    }

    public void loadImages(BufferedImage[] img) {
        images = new BufferedImage[img.length];

        for (int i = 0; i < img.length; i++) {
            ColorModel cm = img[i].getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = img[i].copyData(null);
            images[i] = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        }
    }

    public BufferedImage[] getImages() {
        return this.images;
    }

    public void execute() {
        for (BufferedImage image : images) {
            for (int i = 0; i < filters.size(); i++) {

                if (filters.get(i).getTemplate() == null
                        || filters.get(i).getTemplate() != templates.get(i)) {
                    filters.get(i).loadTemplate(templates.get(i));
                }

                filters.get(i).apply(image);
                display(image);
            }
        }
    }
    
    private void move(){
         if (x < 1000 && y < 700) {
            x += 50;
            y += 50;
        }
        else{
            x=0;
            y=0;
        }
    }

    private void display(BufferedImage image) {
        move();
        imgResult = new ImageResultGUI();
        imgResult.setLocation(x, y);
        imgResult.display(image);
        imgResult.setVisible(true);
        imgResult.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void create() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (iFilterTemplate t : templates) {
            filter = (iFilter) Class.forName("biomage.algorithm." + t.getId()).newInstance();
            filters.add(filter);
        }
    }

}
