package pl.cps.signal.exercise3;

import org.junit.jupiter.api.Test;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KorelacjaTest {
    @Test
    public void calculateDirectTest(){
        List<Data> w1 = new ArrayList<Data>(), w2 = new ArrayList<Data>();
        w1.add(new Data(0.0,5.0));
        w1.add(new Data(1.0,6.0));
        w1.add(new Data(2.0,7.0));

        w2.add(new Data(0.0,1.0));
        w2.add(new Data(1.0,2.0));
        w2.add(new Data(2.0,3.0));
        w2.add(new Data(3.0,4.0));

        List<Data> r = new Correlation().calculateDirect(w2,w1);
    }
}