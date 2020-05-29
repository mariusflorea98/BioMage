package biomage.algorithm.filter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import biomage.algorithm.iFilter;
import biomage.algorithm.template.HistogramTemplate;
import biomage.algorithm.template.iFilterTemplate;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class Histogram implements iFilter {

    private String hType;
    private final String id = "Histogram";
    private BufferedImage histo, image;
    private int[] lumFreq;
    private int[] redFreq, greenFreq, blueFreq;
    private int[] normRedFreq, normGreenFreq, normBlueFreq;
    private int[] eqHist;
    private int[][] luminanceV;
    private int max, maxIndex;
    private int panelWidth, panelHeight;
    private Color maxRGB;
    private HistogramTemplate template;
    private final Color red = new Color(250, 0, 0, 200);
    private final Color green = new Color(0, 150, 0, 150);
    private final Color blue = new Color(0, 0, 250, 100);
    private boolean show = false;

    public Histogram() {
    }

    @Override
    public void apply(BufferedImage img) {

        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(null);
        this.image = img;// new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        readPixels();

        if (show == true) {
            setSize();
            createHistogram();
        }
    }

    private void setSize() {
        if (hType.equals("GRAYSCALE")) {
            panelWidth = 503;
            panelHeight = 500;
        } else {
            panelWidth = 300;
            panelHeight = 450;
        }

    }

    private void readPixels() {

        max = 0;

        lumFreq = new int[256];
        redFreq = new int[256];
        greenFreq = new int[256];
        blueFreq = new int[256];
        eqHist = new int[256];
        normRedFreq = normGreenFreq = normBlueFreq = new int[101];
        luminanceV = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                final int rgbBase = image.getRGB(x, y);

                if (rgbBase != 0) {

                    switch (hType) {

                        case "GRAYSCALE":

                            final int iRBase = (rgbBase >> 16) & 0xFF;
                            final int iGBase = (rgbBase >> 8) & 0xFF;
                            final int iBBase = (rgbBase) & 0xFF;
                            float luminance = ((iRBase * 0.2126f
                                    + iGBase * 0.7152f
                                    + iBBase * 0.0722f));
                            int lum = (int) (luminance);

                            luminanceV[y][x] = lum;
                            lumFreq[lum]++;

                            if (max < lumFreq[lum]) {
                                max = lumFreq[lum];
                                maxIndex = lum;
                                maxRGB = new Color(iRBase, iGBase, iBBase);
                            }
                            break;

                        case "COLOR":

                            Color c = new Color(image.getRGB(x, y));

                            redFreq[c.getRed()]++;
                            greenFreq[c.getGreen()]++;
                            blueFreq[c.getBlue()]++;

                            normRedFreq[(int) ((c.getRed() / 255.0f) * 100)]++;
                            normGreenFreq[(int) ((c.getGreen() / 255.0f) * 100)]++;
                            normBlueFreq[(int) ((c.getBlue() / 255.0f) * 100)]++;

                            break;
                    }
                }

            }
        }

        if (hType == "GRAYSCALE") {

            eqHist[0] = lumFreq[0];
            for (int i = 1; i < 256; i++) {
                eqHist[i] = eqHist[i - 1] + lumFreq[i];
            }

            int total = image.getWidth() * image.getHeight();

            for (int i = 0; i < 256; i++) {
                eqHist[i] = (int) (float) ((eqHist[i] * 255.0) / (float) (total));

            }

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {

                    int p = image.getRGB(x, y);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;

                    int avg = (r + g + b) / 3;

                    int nVal = (int) eqHist[avg];
                    p = (a << 24) | (nVal << 16) | (nVal << 8) | nVal;
                    image.setRGB(x, y, p);

                }
            }
        }
    }

    public int[] getEqualizedHist() {
        return this.eqHist;
    }

    private void createHistogram() {

        this.histo = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D grImg = (Graphics2D) this.histo.getGraphics();

        grImg.setPaintMode();
        grImg.setStroke(new BasicStroke(5));
        JFrame frame = new JFrame();
        grImg.setFont((new Font("TimesRoman", Font.PLAIN, 18)));
        Panel panel = new Panel(this.histo);

        switch (hType) {
            case "GRAYSCALE":

                int pixelsCount = image.getWidth() * image.getHeight();
                double p[] = new double[256];
                double Mt = 0;
                for (int i = 0; i < 256; i++) {

                    grImg.setColor(green);
                    grImg.drawLine(i, (this.histo.getHeight() - lumFreq[i] / 100), i, this.histo.getHeight());
                    grImg.setColor(red);

                    p[i] = (double) lumFreq[i] / (double) pixelsCount;
                    Mt += i * p[i];
                }

//                int optimalTreshold1 = 0;
//                int optimalTreshold2 = 0;
//                int optimalTreshold3 = 0;
//
//                double maxBetweenVar = 0;
//
//                double w0 = 0;
//                double m0 = 0;
//                double c0 = 0;
//                double p0 = 0;
//
//                double w1 = 0;
//                double m1 = 0;
//                double c1 = 0;
//                double p1 = 0;
//
//                double w2 = 0;
//                double m2 = 0;
//                double c2 = 0;
//                double p2 = 0;
//                for (int tr1 = 0; tr1 < 256; tr1++) {
//                    p0 += p[tr1];
//                    w0 += (tr1 * p[tr1]);
//                    if (p0 != 0) {
//                        m0 = w0 / p0;
//                    }
//                    c0 = p0 * (m0 - Mt) * (m0 - Mt);
//
//                    c1 = 0;
//                    w1 = 0;
//                    m1 = 0;
//                    p1 = 0;
//
//                    for (int tr2 = tr1 + 1; tr2 < 256; tr2++) {
//
//                        p1 += p[tr2];
//                        w1 += (tr2 * p[tr2]);
//                        if (p1 != 0) {
//                            m1 = w1 / p1;
//                        }
//
//                        c1 = p1 * (m1 - Mt) * (m1 - Mt);
//
//                        c2 = 0;
//                        w2 = 0;
//                        m2 = 0;
//                        p2 = 0;
//                        for (int tr3 = tr2 + 1; tr3 < 256; tr3++) {
//
//                            p2 += p[tr3];
//                            w2 += (tr3 * p[tr3]);
//                            if (p2 != 0) {
//                                m2 = w2 / p2;
//                            }
//
//                            c2 = p2 * (m2 - Mt) * (m2 - Mt);
//
//                            double p3 = 1 - (p0 + p1 + p2);
//                            double w3 = Mt - (w0 + w1 + w2);
//                            double m3 = w3 / p3;
//                            double c3 = p3 * (m3 - Mt) * (m3 - Mt);
//
//                            double c = c0 + c1 + c2 + c3;
//
//                            if (maxBetweenVar < c) {
//                                maxBetweenVar = c;
//                                optimalTreshold1 = tr1;
//                                optimalTreshold2 = tr2;
//                                optimalTreshold3 = tr3;
//                            }
//                        }
//                    }
//
//                }
//                System.out.println("Threshold 1 = " + optimalTreshold1 + " Threshold 2 = " + optimalTreshold2 + " Threshold 3 = " + optimalTreshold3);
//
//                for (int i = 0; i < image.getWidth(); i++) {
//                    for (int j = 0; j < image.getHeight(); j++) {
//                        if (luminanceV[j][i] < optimalTreshold1) {
//                            image.setRGB(i, j, 0xFFE0E0E0);
//                        } else if (luminanceV[j][i] > optimalTreshold1 && luminanceV[j][i] < optimalTreshold2) {
//                            image.setRGB(i, j, 0xFF808080);
//                        } else if (luminanceV[j][i] > optimalTreshold2 && luminanceV[j][i] < optimalTreshold3) {
//                            image.setRGB(i, j, 0xFF606060);
//                        } else {
//                            image.setRGB(i, j, 0xFF202020);
//                        }
//                    }
//
//                }
                grImg.setColor(blue);
                grImg.drawString("25", 25 * 5, 12);
                grImg.drawString("50", 50 * 5, 12);
                grImg.drawString("75", 75 * 5, 12);
                grImg.setColor(green);
                grImg.drawString("Selected", 15, 35);
                grImg.drawString("Most freq. color: ", 15, 55);
                grImg.drawString("Position: " + maxIndex, 15, 75);
                grImg.drawString("Frequency: " + max, 15, 95);
                grImg.setColor(red);
                grImg.drawString("Everything", 15, 15);
                grImg.setColor(maxRGB);
                grImg.fill3DRect(145, 40, 20, 20, true);

                break;

            case "COLOR":
                for (int i = 0; i < 256; i++) {
                    if (redFreq[i] > 0) {
                        grImg.setColor(red);
                        grImg.drawLine(i, (this.histo.getHeight() - redFreq[i] / 100), i, this.histo.getHeight());

                    }

                    if (greenFreq[i] > 0) {
                        grImg.setColor(green);
                        grImg.drawLine(i, (this.histo.getHeight() - greenFreq[i] / 100), i, this.histo.getHeight());
                    }
                    if (blueFreq[i] > 0) {
                        grImg.setColor(blue);
                        grImg.drawLine(i, (this.histo.getHeight() - blueFreq[i] / 100), i, this.histo.getHeight());
                    }

                }
                break;
        }

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public BufferedImage getHistogram() {
        return this.histo;
    }

    public int[] getDistribution() {
        return this.lumFreq;
    }

    @Override
    public String getId() {
        return this.id;

    }

    @Override
    public void loadTemplate(iFilterTemplate hTemp) {
        this.template = (HistogramTemplate) hTemp;
        hType = this.template.hType;
        show = this.template.show;
    }

    @Override
    public iFilterTemplate getTemplate() {
        return this.template;
    }

}

class Panel extends JPanel {

    BufferedImage image;

    public Panel(BufferedImage img) {
        this.image = img;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}
