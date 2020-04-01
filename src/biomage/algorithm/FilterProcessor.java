/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import biomage.algorithm.Blur;
import java.awt.image.BufferedImage;

/**
 *
 * @author Marius
 */
public class FilterProcessor {
    private BufferedImage image;
    private List<iFilter> filters = new ArrayList<iFilter>();
    private List<String> ids = new ArrayList<String>();
    private Class cls = null;
    private iFilter filter = null;

    FilterProcessor() {
    }

    public FilterProcessor(Object[] array) {

        for (Object o : array) {
            this.ids.add((String) o);
        }

    }
    
    public void loadImage(BufferedImage img){
        this.image=img;
    }
    
    public BufferedImage getImage(){
        return this.image;
    }

    public void execute() {

        for (iFilter f : filters) {
            f.execute(image);
        }

    }

    public void create() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        for (String id : ids) {
            filter = (iFilter) Class.forName("biomage.algorithm." + id).newInstance();
            filters.add(filter);
        }
    }

    public void add(String id) {
        this.ids.add(id);
    }

    public void remove(String id) {
        this.ids.remove(id);
    }

}
