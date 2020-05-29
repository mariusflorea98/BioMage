/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biomage.algorithm;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author Marius
 */
public class PHash {

    int mean = 0;
    BufferedImage image1 = null, image2 = null;

    public PHash() {

    }

    public void readImages() throws IOException {

        try {
            image1 = ImageIO.read(new File("hash/img2.png"));
       

        } catch (IOException e) {
        }

        image2 = resize(image1, 8, 8);

        grayscale();
    }

    public void grayscale() throws IOException {
        int sum = 0;
        for (int i = 0; i < image2.getWidth(); i++) {
            for (int j = 0; j < image2.getHeight(); j++) {

                Color c = new Color(image2.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                int gray = (red + green + blue) / 3;
                sum += gray;

            }
        }
        mean = sum / (image2.getWidth() * image2.getHeight());

        compute();
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public void compute() throws IOException {
        for (int i = 0; i < image2.getWidth(); i++) {
            for (int j = 0; j < image2.getHeight(); j++) {
                Color c = new Color(image2.getRGB(i, j));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                int gray = (red + green + blue) / 3;

                if (gray > mean) {

                    image2.setRGB(i, j, 0xFFFFFFFF);
                } else {
                    image2.setRGB(i, j, 0xFF000000);
                }

            }
        }

        File outputfile = new File("./output/binary" + ".png");

        ImageIO.write(image2, "png", outputfile);
        System.out.println("Saved image as binary.png in the /output directory");

        hash();
    }

    public int hammingDist(String str1, String str2) 
{ 
    int i = 0, count = 0; 
    while (i < str1.length()) 
    { 
        if (str1.charAt(i) != str2.charAt(i)) 
            count++; 
        i++; 
    } 
    return count; 
}  

    public void hash() {
        File f = new File("./output/binary.png");
        String encodstring = encodeFileToBase64Binary(f);
        System.out.println(encodstring);


        File f2 = new File("./output/binary2.png");
        String encodstring2 = encodeFileToBase64Binary(f2);
        System.out.println(encodstring2);
        System.out.println(hammingDist(encodstring, encodstring2));
    }

    private static String encodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = DatatypeConverter.printBase64Binary(bytes);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }

}
