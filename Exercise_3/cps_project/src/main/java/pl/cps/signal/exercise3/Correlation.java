package pl.cps.signal.exercise3;

import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class Correlation {

    public static List<Data> calculateDirect(List<Data> h, List<Data> x){
        List<Data> results = new ArrayList<Data>();
        int m = h.size()+x.size()-1;
        double xP=0,yP=0,jump=(h.get(1).getX()-h.get(0).getX());

        for (int n = -(x.size()-1); n < (m-(x.size()-1)); n++) {
            yP=0;
            //System.out.print(String.format("R-hx(%d)= ",n));
            for (int k = 0; k < Math.max(x.size(),h.size()); k++) {
                if(!(((k-n)<0)||(x.size()<((k-n)+1)))) {
                    //System.out.print(String.format("  h(%d)*x(%d)  ",k,(k-n)));
                    yP+=h.get(k).getY() * x.get( k - n).getY();
                }//else System.out.print(String.format("  ||k=%d, n=%d, k-n=%d||  ",k,n,(k-n)));
            }
            System.out.println();
            results.add(new Data(xP,yP));
            xP+=jump;
        }
        return results;
    }

    public static List<Data> calculateWithConvolution(List<Data> h, List<Data> x){
        return Splot.calculate(h,x);
    }



}