package biomage.model;

import java.util.List;

public class Punct {

    private int x = -1;
    private int y = -1;
    private int rgbBase;
    private double distance;
    public Punct[] puncte;
    public Punct varf1,varf2;
    
    public Punct() {
    }

    public Punct(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

   public Punct(final int x, final int y, final int rgbBase) {
        this(x, y);
        this.rgbBase = rgbBase;
    }

   public Punct(List<Punct> puncte, double distance) {
        for (int i = 0; i < puncte.size(); i++) {
            this.puncte[i] = puncte.get(i);
        }
        this.distance = distance;
    }
    
    
   public Punct(Punct p1, Punct p2, double distance){
        this.varf1=p1;
        this.varf2=p2;
        this.distance=distance;
    }

   public int getX() {
        return x;
    }

   public int getY() {
        return y;
    }

   public int getRGBBase() {
        return rgbBase;

    }
   
   public double getDistance(){
       return this.distance;
   }

   public void setX(int x) {
        this.x = x;
    }

   public void setY(int y) {
        this.y = y;
    }

   public void setRGB(int rgb) {
        this.rgbBase = rgb;
    }

    public String toString() {
        return "[" + this.x + "," + this.y + "]";

    }

}
