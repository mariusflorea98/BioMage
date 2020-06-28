/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.filter.Histogram;
import biomage.algorithm.template.HistogramTemplate;
import biomage.view.gui.MainFrameGUI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author Marius
 */
public class WassersteinDistance {

    private BufferedImage baseImage;
    private int threshold;
    private BufferedImage[] images;
    private String[] pipeNames;
    private final File file = new File("./models/");

    private BufferedImage getCopyOf(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public WassersteinDistance(BufferedImage baseImage, int threshold) throws IOException {

        this.baseImage = getCopyOf(baseImage);

        this.threshold = threshold;

        File[] files = file.listFiles();
        images = new BufferedImage[files.length];
        pipeNames = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            try {
                images[i] = getCopyOf(ImageIO.read(files[i]));
                pipeNames[i] = files[i].getName();
            } catch (IOException ex) {
                Logger.getLogger(MainFrameGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getTemplateType() {
        int selectedIndex = 0, minValue = threshold;
        HistogramTemplate histTemp = new HistogramTemplate();
        histTemp.setType("GRAYSCALE");
        histTemp.setShow(false);

        Histogram hist1 = new Histogram();
        Histogram hist2 = new Histogram();

        hist1.loadTemplate(histTemp);
        hist2.loadTemplate(histTemp);

        int[] similarityIndex = new int[images.length];

        hist1.apply(baseImage);
        int h1[] = hist1.getEqualizedHist();

        for (int j = 0; j < images.length; j++) {
            hist2.apply(images[j]);

            double distance = 0;

            int h2[] = hist2.getEqualizedHist();

            double prev = 0;
            for (int i = 0; i < h1.length; i++) {
//                final double current = (h1[i] + prev) - h2[i];
//                distance += Math.abs(current);
//                prev = current;   //wasserstein
                
//                    final double current = h1[i]-h2[i];
//                    distance += Math.abs(current); //simple diff
                
                 final double current = h1[i]-h2[i];
                 distance+=Math.abs(current);

            }
            
            
            System.out.println((int) distance);
            similarityIndex[j] = (int) distance;

            if (minValue >= similarityIndex[j]) {
                minValue = similarityIndex[j];
                selectedIndex = j;
            }

        }
        System.out.println(pipeNames[selectedIndex].split("\\.", 2)[0]);
        return pipeNames[selectedIndex].split("\\.", 2)[0];

    }

}
