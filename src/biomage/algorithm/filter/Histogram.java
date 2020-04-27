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
    private int max, maxIndex;
    private int panelWidth, panelHeight;
    private Color maxRGB;
    private HistogramTemplate template;
    private final Color red = new Color(250, 0, 0, 200);
    private final Color green = new Color(0, 150, 0, 150);
    private final Color blue = new Color(0, 0, 250, 100);

    public Histogram() {
    }

    @Override
    public void apply(BufferedImage img) {

        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(null);
        this.image = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        setSize();
        readPixels();
        createHistogram();

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
        float luminance;

        lumFreq = new int[101];
        redFreq = new int[256];
        greenFreq = new int[256];
        blueFreq = new int[256];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                final int rgbBase = image.getRGB(x, y);
                final int iRBase = (rgbBase >> 16) & 0xFF;
                final int iGBase = (rgbBase >> 8) & 0xFF;
                final int iBBase = (rgbBase) & 0xFF;
                luminance = ((iRBase * 0.2126f
                        + iGBase * 0.7152f
                        + iBBase * 0.0722f) / 255);
                int lum = (int) (luminance * 100);
                if (image.getRGB(x, y) != 0) {

                    switch (hType) {

                        case "GRAYSCALE":
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
                            greenFreq[c.getBlue()]++;
                            blueFreq[c.getGreen()]++;

                            break;
                    }
                }

            }
        }
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

                for (int i = 0; i < 101; i++) {

                    if (lumFreq[i] > 0) {
                        grImg.setColor(green);
                        grImg.drawLine(i*5, (this.histo.getHeight() - lumFreq[i] / 100), i*5, this.histo.getHeight());
                        grImg.setColor(red);
                    }

                }

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
