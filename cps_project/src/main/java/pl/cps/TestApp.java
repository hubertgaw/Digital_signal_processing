package pl.cps;

import pl.cps.signal.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TestApp {
    public static void main(String[] args) {
        int d1S=0,d1T=10,d2S=8,d2T=5,step=1;
        List<Data> d1 = new ArrayList<Data>(), d2 = new ArrayList<Data>();

        for(int i = d1S;i<=(d1S+(d1T*step));i++){

            d1.add(new Data(i/1000.0,2.0));
;
        }
        for(int i = d2S;i<=(d2S+(d2T*step));i++){
            d2.add(new Data(i/1000.0,2.0));


        }

        for (Data d : d1){
            System.out.print(d.getX()*1000+":"+d.getY()+" | ");

        }
        System.out.println();System.out.println();
        for (Data d : d2){
            System.out.print(d.getX()*1000+":"+d.getY()+" | ");

        }
        System.out.println();System.out.println();
        int it = 1;
        for (Data d : Division.performCalculating(d1,d2)){
            System.out.print(it+":"+d.getY()+" | ");
it++;
        }
    }
}
