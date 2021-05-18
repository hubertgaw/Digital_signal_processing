package pl.cps.signal.exercise3;

import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class Splot {
    /**
     * ZALOZENIA: Sygnaly maja te sama czestotliwość próbkowania, moga miec rozna liczbe probek
     * @param h próbki pierwszego sygnalu
     * @param x probki drugiego sygnalu
     * @return
     */
    public static List<Data> calculate(List<Data> h, List<Data> x){
        List<Data> results = new ArrayList<Data>();
        int m = h.size()+x.size()-1;
        double xP=0,yP=0,jump=(h.get(1).getX()-h.get(0).getX());

        for (int i = 0; i < m; i++) {
            yP=0;
            for (int j = 0; j < Math.min(x.size(),h.size()); j++) {
                if(!(((i-j)<0)||(x.size()<((i-j)+1)))) {
                    yP+=h.get(j).getY() * x.get(i - j).getY();
                }
            }
            results.add(new Data(xP,yP));
            yP+=jump;
        }
        return results;
    }
}
