package pl.cps.signal.exercise3;

import org.junit.jupiter.api.Test;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SplotTest {
    @Test
    public void calculateTest(){
        List<Data> w1 = new ArrayList<Data>(), w2 = new ArrayList<Data>();
        w1.add(new Data(0.0,3.0));
        w1.add(new Data(1.0,1.0));
        w1.add(new Data(2.0,2.0));

        w2.add(new Data(0.0,1.0));
        w2.add(new Data(1.0,2.0));
        w2.add(new Data(2.0,1.0));
        w2.add(new Data(3.0,3.0));
        int [] exp = {3,7,7,14,5,6};
        List<Data> res = Splot.calculate(w1, w2);
        assertEquals(res.size(),6);
        for (int i = 0; i < 6; i++) {
            assertEquals(exp[i],res.get(i).getY());
        }
    }
}