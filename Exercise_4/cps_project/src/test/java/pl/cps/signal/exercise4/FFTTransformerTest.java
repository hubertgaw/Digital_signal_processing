package pl.cps.signal.exercise4;

import org.apache.commons.math3.complex.Complex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FFTTransformerTest {

    @Test
    void fftTransform() {
        Complex[] x = {
                new Complex(0.5568836254037923),
                new Complex(0.8735842104393365),
                new Complex(0.6099699812709252),
                new Complex(0.5631502515566189),
                new Complex(-0.518857260970139),
                new Complex(-0.5946393148293805),
                new Complex(0.47144753318047794),
                new Complex(-0.3501597962417593)
        };
        Complex[] X = {
                new Complex(1.6113792298098721),
                new Complex(1.4681239692650163,- 1.8225209872296184),
                new Complex(-1.0433911500177497, - 0.06595444029509645),
                new Complex(0.6833578034828462, - 1.545476091048724),
                new Complex(0.6275085279602408),
                new Complex(0.6833578034828462, 1.545476091048724),
                new Complex(-1.0433911500177497 ,  0.06595444029509645),
                new Complex(1.4681239692650163, 1.8225209872296184)
        };

        List<Complex> xList = Arrays.asList(x);
        List<Complex> XList = Arrays.asList(X);

        List<Complex> result = FFTTransformer.fftTransform(xList);
        for (int i = 0; i < x.length; i++) {
            Assertions.assertEquals(XList.get(i).getReal(), result.get(i).getReal(), 0.0001);
            Assertions.assertEquals(XList.get(i).getImaginary(), result.get(i).getImaginary(), 0.0001);
        }
    }
}