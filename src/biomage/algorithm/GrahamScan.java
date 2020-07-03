package biomage.algorithm;
/*
 * Copyright (c) 2010, Bart Kiers
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */ 
 
 
import biomage.model.Punct;
import java.util.*;

public class GrahamScan {

    public final int clockwise = -1, countercl = 1, collinear = 0;

    public GrahamScan() {
        
    }

    public  int Coliniaritate(List<Punct> Puncte) {
        if (Puncte.size() <= 2) {
            return 1;
        }

        Punct a = Puncte.get(0);
        Punct b = Puncte.get(1);

        for (int i = 2; i < Puncte.size(); i++) {

            Punct c = Puncte.get(i);

            if (Orientare(a, b, c) != collinear) {
                {
                    return 0;

                }

            }
        }
        return 1;
    }

    public List<Punct> ConvexHull(List<Punct> Puncte) throws IllegalArgumentException {
        List<Punct> sorted = new ArrayList<Punct>(sortarePuncte(Puncte));
        if (sorted.size() < 3) {
            throw new IllegalArgumentException("O invelitoare convexa este formata din minim 3 puncte.");
        } else if (Coliniaritate(sorted) != 0) {

            throw new IllegalArgumentException("O invelitoare convexa nu poate fi formata doar din puncte coliniare.");

        }

        Stack<Punct> stack = new Stack<Punct>();
        stack.push(sorted.get(0));
        stack.push(sorted.get(1));

        for (int i = 2; i < sorted.size(); i++) {

            Punct prim = sorted.get(i);
            Punct mijloc = stack.pop();
            Punct ultim = stack.peek();

            int ori = Orientare(ultim, mijloc, prim);

            switch (ori) {
                case countercl:
                    stack.push(mijloc);
                    stack.push(prim);
                    break;
                case clockwise:
                    i--;
                    break;
                case collinear:
                    stack.push(prim);
                    break;
            }
        }

        stack.push(sorted.get(0));
        return new ArrayList<Punct>(stack);

    }


    public Set<Punct> sortarePuncte(List<Punct> Puncte) {

         Punct min2 = Puncte.get(0);

        for (int i = 1; i < Puncte.size(); i++) {

            Punct aux = Puncte.get(i);

            if (aux.getY() < min2.getY() || (aux.getY() == min2.getY() && aux.getX() < min2.getX())) {
                min2 = aux;
            }
        }

        
        Punct min = min2;

        TreeSet<Punct> set = new TreeSet<Punct>(new Comparator<Punct>() {

            public int compare(Punct a, Punct b) {

                if (a == b || a.equals(b)) {
                    return 0;
                }

                double thetaA = Math.atan2((long) a.getY() - min.getY(), (long) a.getX() - min.getX());
                double thetaB = Math.atan2((long) b.getY() - min.getY(), (long) b.getX() - min.getX());

                if (thetaA < thetaB) {
                    return -1;
                } else if (thetaA > thetaB) {
                    return 1;
                } else {
               
                    double distanceA = Math.sqrt((((long) min.getX() - a.getX()) * ((long) min.getX() - a.getX()))
                            + (((long) min.getY() - a.getY()) * ((long) min.getY() - a.getY())));
                    double distanceB = Math.sqrt((((long) min.getX() - b.getX()) * ((long) min.getX() - b.getX()))
                            + (((long) min.getY() - b.getY()) * ((long) min.getY() - b.getY())));
                    if (distanceA < distanceB) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        });

        set.addAll(Puncte);

        return set;
    }

    public int Orientare(Punct a, Punct b, Punct c) {

        long produsVectorial = ((b.getX() - a.getX()) * (c.getY() - a.getY())) - ((b.getY() - a.getY()) * (c.getX() - a.getX()));

        if (produsVectorial > 0) {

            return countercl;
        } else if (produsVectorial < 0) {

            return clockwise;
        } else {
            return collinear;
        }
    }

}
