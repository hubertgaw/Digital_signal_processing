package pl.cps.signal.exercise4;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DFTTransformerTest {
    @Test
    public void transform() {
        Complex[] x = {
                new Complex(1.0),
                new Complex(2.0, -1.0),
                new Complex(0.0, -1.0),
                new Complex(-1.0,2.0)
        };
        Complex[] X = {
                new Complex(2.0, 0.0),
                new Complex(-2.0, -2.0),
                new Complex(0.0, -2.0),
                new Complex(4.0, 4.0)
        };

        List<Complex> xList = Arrays.asList(x);
        List<Complex> XList = Arrays.asList(X);

        List<Complex> result = DFTTransformer.simpleDFTTransform(xList);
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(XList.get(i).getReal(), result.get(i).getReal(), 0.0000001);
            Assertions.assertEquals(XList.get(i).getImaginary(), result.get(i).getImaginary(), 0.0000001);
        }
    }

}