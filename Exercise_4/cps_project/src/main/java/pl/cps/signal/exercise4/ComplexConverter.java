package pl.cps.signal.exercise4;

import org.apache.commons.math3.complex.Complex;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class ComplexConverter {

    public static List<Complex> convertDataToComplex(List<Data> originalPoints) {
        List<Complex> complexPoints = new ArrayList<>();
        for (Data point : originalPoints) {
            complexPoints.add(new Complex(point.getY()));
        }
        return complexPoints;
    }
}
