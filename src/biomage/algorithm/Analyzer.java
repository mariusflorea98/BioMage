/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import biomage.algorithm.filter.FloodFill;
import biomage.model.flood.FloodData;
import biomage.model.flood.FloodFillOptionsObj;
import biomage.model.flood.ImageOptionsObj;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marius
 */
public class Analyzer {
    
    private BufferedImage img;
    private ImageOptionsObj imgOpt;
    private FloodData fd;
    private FloodFillOptionsObj floodOpt;
    
   public Analyzer(BufferedImage img, ImageOptionsObj imgOpt,FloodFillOptionsObj floodOpt, FloodData fd){
        this.img=img;
        this.imgOpt=imgOpt;
        this.fd=fd;
        this.floodOpt=floodOpt;
        
    }
    
    public void start(){
         int imgArea = img.getHeight() * img.getWidth();
        int rejected_area = imgArea - fd.iTotalArea;
        float rej_avgLum = 0;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (img.getRGB(x, y) == 0);
                rej_avgLum += fd.luminance[y][x];
            }
        }

       fd.avgRegionsLum /= fd.regions;
        //fd.rejected_avgRegionsLum = rej_avgLum / rejected_area;
        System.out.println("avgRegionLum: " + fd.avgRegionsLum);
        System.out.println("regions: " + fd.regions);
        System.out.println("pixeli<lum_min: " + fd.pixels_belowInterval);
        System.out.println("pixeli intre: " + fd.pixels_inInterval);
        System.out.println("pixeli>lum_max: " + fd.pixels_aboveInterval);

        try {
            FileWriter writer = new FileWriter(new File("cells.csv"), true);
            StringBuilder csvWriter = new StringBuilder();

            csvWriter.append("\n");

            csvWriter.append(imgOpt.getArieMin() + " - " + imgOpt.getArieMax());
            csvWriter.append(",");
            csvWriter.append(imgOpt.getAverageLumMin() + " - " + imgOpt.getAverageLumMax());
            csvWriter.append(",");
            csvWriter.append(floodOpt.getToleranta());
            csvWriter.append(",");
            csvWriter.append(floodOpt.getFAFactor());
            csvWriter.append(",");
            csvWriter.append(floodOpt.getVecini());
            csvWriter.append(",");
            csvWriter.append(fd.regions);
            csvWriter.append(",");
            csvWriter.append(fd.avgRegionsLum);
            csvWriter.append(",");
            csvWriter.append(fd.pixels_belowInterval);
            csvWriter.append(",");
            csvWriter.append(fd.pixels_inInterval);
            csvWriter.append(",");
            csvWriter.append(fd.pixels_aboveInterval);
            writer.write(csvWriter.toString());

            writer.close();

            System.out.println("Exported...");
        } catch (IOException ex) {
            Logger.getLogger(FloodFill.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    
    private void initCSV() {
        try {
            FileWriter writer = new FileWriter(new File("cells.csv"), true);

            StringBuilder csvWriter = new StringBuilder();
            csvWriter.append("Input_Area");
            csvWriter.append(",");
            csvWriter.append("Input_AverageLuminance");
            csvWriter.append(",");
            csvWriter.append("Input_Threshold");
            csvWriter.append(",");
            csvWriter.append("Input_AdaptiveFactor");
            csvWriter.append(",");
            csvWriter.append("Input_Neighbors");
            csvWriter.append(",");
            csvWriter.append("cells");
            csvWriter.append(",");
            csvWriter.append("avgRegionLum");
            csvWriter.append(",");
            csvWriter.append("pixels<lum_min");
            csvWriter.append(",");
            csvWriter.append("pixels intre");
            csvWriter.append(",");
            csvWriter.append("pixels>lum_max");

        } catch (IOException ex) {
            Logger.getLogger(FloodFill.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
