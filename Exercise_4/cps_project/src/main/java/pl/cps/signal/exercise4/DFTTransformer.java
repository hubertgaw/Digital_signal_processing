package pl.cps.signal.exercise4;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;

public class DFTTransformer {
    public static List<Complex> simpleDFTTransform(List<Complex> inputPoints) {
        List<Complex> outputPoints = new ArrayList<>();
        int n = inputPoints.size();
        for (int k = 0; k < n; k++) {
            double sumReal = 0;
            double sumImag = 0;
            for (int t = 0; t < n; t++) {
                double angle = 2.0 * Math.PI * t * k / n;
                sumReal += inputPoints.get(t).getReal() * Math.cos(angle) +
                        inputPoints.get(t).getImaginary() * Math.sin(angle);
                sumImag += -inputPoints.get(t).getReal() * Math.sin(angle) +
                        inputPoints.get(t).getImaginary() * Math.cos(angle);
            }
            outputPoints.add(new Complex(sumReal, sumImag));
        }
        return outputPoints;
    }
}
