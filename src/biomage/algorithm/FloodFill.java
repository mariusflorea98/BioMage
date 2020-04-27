package biomage.algorithm;

import biomage.algorithm.template.BlurTemplate;
import biomage.algorithm.template.FloodFillTemplate;
import biomage.algorithm.template.iFilterTemplate;
import biomage.model.Layer;
import biomage.model.Punct;
import biomage.model.flood.FloodData;
import biomage.model.flood.FloodFillOptionsObj;
import biomage.model.flood.ImageOptionsObj;
import biomage.model.iLayer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

/**
 *
 * @author Marius
 */
public class FloodFill implements iFilter {

    private final String id = "FloodFill";
    private int maxSelected = 0;
    private FloodData fd;
    private int newC = 0x80FF0000;
    private final Color colorCH = new Color(0, 150, 0, 200);
    private final Color distanceCH = new Color(0, 0, 150, 200);
    private final Color yellow = new Color(255, 255, 0, 160);
    FloodFillTemplate template;
    FloodFillOptionsObj floodOpt;
    ImageOptionsObj imgOpt;

    public FloodData ReadImagePixels(BufferedImage img) {

        int x = img.getWidth();
        int y = img.getHeight();
        int totalArea = 0;
        boolean[][] pixel_visited = new boolean[y][x];
        float[][] luminance = new float[y][x];

        iLayer floodLayer = new Layer();
        floodLayer.loadImage(img);
        iLayer hullLayer = new Layer();
        hullLayer.loadImage(img);

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                pixel_visited[y][x] = false;

                final int rgbBase = img.getRGB(x, y);
                final int iRBase = (rgbBase >> 16) & 0xFF;
                final int iGBase = (rgbBase >> 8) & 0xFF;
                final int iBBase = (rgbBase) & 0xFF;
                luminance[y][x] = ((iRBase * 0.2126f
                        + iGBase * 0.7152f
                        + iBBase * 0.0722f) / 255);

            }

        }

        FloodData floodData = new FloodData(totalArea, floodLayer, hullLayer, pixel_visited, luminance);
        return floodData;
    }

    double shoelaceArea(List<Punct> cH) {

        double area = 0.0;
        for (int i = 0; i < cH.size() - 1; i++) {
            area += cH.get(i).getX()
                    * cH.get(i + 1).getY()
                    - cH.get(i + 1).getX()
                    * cH.get(i).getY();
        }

        return Math.abs(area + cH.get(cH.size() - 1).getX()
                * cH.get(0).getY()
                - cH.get(0).getX()
                * cH.get(cH.size() - 1).getY())
                / 2.0;
    }

    void analiza(FloodData floodData, BufferedImage imgStart) {
        int imgArea = imgStart.getHeight() * imgStart.getWidth();
        int rejected_area = imgArea - floodData.iTotalArea;
        float rej_avgLum = 0;
        for (int y = 0; y < imgStart.getHeight(); y++) {
            for (int x = 0; x < imgStart.getWidth(); x++) {
                if (imgStart.getRGB(x, y) == 0);
                rej_avgLum += floodData.luminance[y][x];
            }
        }

        floodData.avgRegionsLum /= floodData.regions;
        floodData.rejected_avgRegionsLum = rej_avgLum / rejected_area;

        System.out.println("avgRegionLum: " + floodData.avgRegionsLum);
        System.out.println("rejected avgRegionLum: " + floodData.rejected_avgRegionsLum);
        System.out.println("regions: " + floodData.regions);
        System.out.println("pixeli<lum_min: " + floodData.pixels_belowInterval);
        System.out.println("pixeli intre: " + floodData.pixels_inInterval);
        System.out.println("pixeli>lum_max: " + floodData.pixels_aboveInterval);

    }

    Punct pointsDistance(List<Punct> cH) {
        double max = 0;
        Punct p1 = new Punct();
        Punct p2 = new Punct();

        double distance = 0;
        for (int i = 0; i < cH.size() - 1; i++) {
            for (int j = 1; j < cH.size(); j++) {
                distance = Math.sqrt((cH.get(j).getY() - cH.get(i).getY())
                        * (cH.get(j).getY() - cH.get(i).getY()) + (cH.get(j).getX() - cH.get(i).getX())
                        * (cH.get(j).getX() - cH.get(i).getX()));
                if (distance > max) {
                    max = distance;
                    p1 = new Punct(cH.get(i).getX(), cH.get(i).getY());
                    p2 = new Punct(cH.get(j).getX(), cH.get(j).getY());

                }
            }
        }

        Punct p = new Punct(p1, p2, distance);
        return p;
    }

//    void drawHull(Vector<ConvexHullObj> ch, FloodData fd) {
//        int nrHull = 0;
//        final Graphics2D grImg = (Graphics2D) fd.hullLayer.layerImage.getGraphics();
//
//        grImg.setPaintMode();
//        grImg.setStroke(new BasicStroke(2));
//        Polygon poly;
//
//        int perimeter;
//        double roundness;
//        for (ConvexHullObj ch1 : ch) {
//            perimeter = 0;
//            if (ch1.area > fd.convOptions.areaLower
//                    && ch1.area < fd.convOptions.areaUpper
//                    && ((float) ch1.selected_pixels) / (ch1.area) < fd.convOptions.selectedUpper
//                    && ((float) ch1.selected_pixels) / ch1.area >= fd.convOptions.selectedLower) {
//
//                nrHull++;
//                grImg.setColor(colorCH);
//                poly = new Polygon();
//                Punct pix = ch1.points.get(0);
//                int xloc = pix.getX(), yloc = pix.getY();
//                for (int j = 1; j < ch1.points.size(); j++) {   //replace with draw poly
//                    pix = ch1.points.get(j);
//                    poly.addPoint(pix.getX(), pix.getY());
//                    grImg.drawLine(xloc, yloc, pix.getX(), pix.getY());
//                    perimeter += sqrt((pix.getX() - xloc) ^ 2 + (pix.getY() - yloc) ^ 2);
//
//                    xloc = pix.getX();
//                    yloc = pix.getY();
//
//                }
//                if (perimeter > 5) {
//                    roundness = ((4 * Math.PI * ch1.area) / (perimeter ^ 2));
//                    //System.out.println("roundness: " + roundness);
//                }
//                // Rectangle2D poz = poly.getBounds2D();              //bounding box
//
//                grImg.setColor(yellow);
//                grImg.fillPolygon(poly);
//                grImg.setColor(colorCH);
//                grImg.drawLine(xloc, yloc, ch1.points.get(0).getX(), ch1.points.get(0).getY());
//                Punct p = pointsDistance(ch1.points);
//                grImg.setColor(distanceCH);
//                grImg.drawLine(p.varf1.getX(), p.varf1.getY(),
//                        p.varf2.getX(), p.varf2.getY());
//            }
//        }
//        System.out.println(nrHull);
//    }
    public void floodFill(BufferedImage imgStart, Punct pozitie,
            final int rgbOriginal, FloodFillOptionsObj floodOpt, ImageOptionsObj imgOpt) {

        final Queue<Punct> queFill = new LinkedList<>();
        final List<Punct> listaPuncte = new ArrayList<>();
        // final List<Punct> convexHull;

        float luminanceSum = 0;
        Punct pix;

        queFill.add(new Punct(pozitie.getX(), pozitie.getY(), rgbOriginal));

        while (queFill.size() > 0) {

            final Punct pixel = queFill.poll();
            final int x = pixel.getX();
            final int y = pixel.getY();

            if (x < 0 || x >= imgStart.getWidth() || y < 0 || y >= imgStart.getHeight()) {
                continue;
            }
            if (fd.pixel_visited[y][x] == true) {
                continue;
            }

            final int rgbPixel = imgStart.getRGB(x, y);
            final int iR = (rgbPixel >> 16) & 0xFF;
            final int iG = (rgbPixel >> 8) & 0xFF;
            final int iB = (rgbPixel) & 0xFF;

            final int rgbBase = pixel.getRGBBase();
            final int iRBase = (rgbBase >> 16) & 0xFF;
            final int iGBase = (rgbBase >> 8) & 0xFF;
            final int iBBase = (rgbBase) & 0xFF;

            final int cmp = Math.abs(iRBase - iR)
                    + Math.abs(iGBase - iG)
                    + Math.abs(iBBase - iB);

            if (cmp > floodOpt.getToleranta()) {
                continue;

            }

            fd.pixel_visited[y][x] = true;

            pix = new Punct(x, y);
            listaPuncte.add(pix);

            luminanceSum += fd.luminance[y][x];

            final float fA = floodOpt.getFAFactor() / 100.0f;
            final float fN = 1.01f - fA;
            final int r = (int) (fA * iR + fN * iRBase);
            final int g = (int) (fA * iG + fN * iGBase);
            final int b = (int) (fA * iB + fN * iBBase);

            final int rgbNext = (r << 16) | (g << 8) | b;

            queFill.add(new Punct(x + 1, y, rgbNext));
            queFill.add(new Punct(x - 1, y, rgbNext));
            queFill.add(new Punct(x, y + 1, rgbNext));
            queFill.add(new Punct(x, y - 1, rgbNext));

            if (floodOpt.getVecini() == 8) {
                queFill.add(new Punct(x + 1, y + 1, rgbNext));
                queFill.add(new Punct(x - 1, y - 1, rgbNext));
                queFill.add(new Punct(x - 1, y + 1, rgbNext));
                queFill.add(new Punct(x + 1, y - 1, rgbNext));
            }

        }

        int nr_pixels = listaPuncte.size();
        float avgLuminance = luminanceSum / nr_pixels;
        if (maxSelected < nr_pixels) {
            maxSelected = nr_pixels;
        }
        if (avgLuminance < imgOpt.getAverageLumMax() && avgLuminance > imgOpt.getAverageLumMin()
                && nr_pixels < imgOpt.getArieMax() && nr_pixels > imgOpt.getArieMin()) {

            fd.pixels_inInterval += nr_pixels;
            fd.avgRegionsLum += avgLuminance;
            fd.iTotalArea += nr_pixels;
            fd.regions++;

            for (int j = 0; j < nr_pixels; j++) {
                pix = listaPuncte.get(j);
                fd.floodLayer.getImage().setRGB(pix.getX(), pix.getY(), newC);

            }

//            if (floodOpt.getConvHullOp() == true) {
//                if (nr_pixels > 3 && GrahamScan.Coliniaritate(listaPuncte) == 0) {
//                    convexHull = GrahamScan.ConvexHull(listaPuncte);
//                    double area = shoelaceArea(convexHull);
//
//                    ConvexHullObj ch = new ConvexHullObj(convexHull, area, nr_pixels);
//
//                    fd.hulls.add(ch);
//
//                }
//            }
        } else {
            fd.rejected_avgRegionsLum += avgLuminance;

            if (avgLuminance < imgOpt.getAverageLumMin()) {
                fd.pixels_belowInterval++;
            } else if (avgLuminance > imgOpt.getAverageLumMax()) {
                fd.pixels_aboveInterval++;
            }

            
        }

    }

    public void FloodFillImage(BufferedImage imgStart, FloodFillOptionsObj floodOpt,
            ImageOptionsObj imgOpt
    //ConvexHullOptionsObj convOpt, 
    ) {

        fd = ReadImagePixels(imgStart);
        BufferedImage copy = new BufferedImage(imgStart.getWidth(), imgStart.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < imgStart.getWidth(); i++) {
            for (int j = 0; j < imgStart.getHeight(); j++) {
                copy.setRGB(i, j, imgStart.getRGB(i, j));
            }
        }

        Punct pozitie = new Punct();
//        if (convOpt != null) {
//            System.out.println(convOpt);
//        }
        for (int y = 0; y < imgStart.getHeight(); y++) {
            for (int x = 0; x < imgStart.getWidth(); x++) {
                {
                    if (fd.pixel_visited[y][x] == false) {

                        pozitie.setX(x);
                        pozitie.setY(y);
                        final int prevC = imgStart.getRGB(pozitie.getX(), pozitie.getY());
                        floodFill(copy, pozitie, prevC, floodOpt, imgOpt);

                    }
                }
            }
        }

       

        //analiza(floodData, imgStart);
//        floodData.convOptions = convOpt;
//        if (floodData.hulls.size() != 0) {
//            drawHull(floodData.hulls, floodData);
//        }
        // return floodData;
    }

    @Override
    public void apply(BufferedImage image) {

    FloodFillImage(image, floodOpt, imgOpt);
    BufferedImage img = fd.floodLayer.getImage();
    image.getGraphics().drawImage(img, 0, 0, null);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void loadTemplate(iFilterTemplate bTemp) {
        template = (FloodFillTemplate) bTemp;
        floodOpt = template.getFloodOpt();
        imgOpt = template.getImageOpt();
    }

    @Override
    public iFilterTemplate getTemplate() {
        return this.template;
    }

}
