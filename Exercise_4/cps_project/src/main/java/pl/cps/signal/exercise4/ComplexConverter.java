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

    public static List<Data> convertComplexRealPartToData(List<Complex> originalPoints) {
        List<Data> returnPoints = new ArrayList<>();
        for (int i = 0; i < originalPoints.size(); i++) {
            returnPoints.add(new Data((double) i, originalPoints.get(i).getReal()));
        }
        return returnPoints;
    }

    public static List<Data> convertComplexImaginaryPartToData(List<Complex> originalPoints) {
        List<Data> returnPoints = new ArrayList<>();
        for (int i = 0; i < originalPoints.size(); i++) {
            returnPoints.add(new Data((double) i, originalPoints.get(i).getImaginary()));
        }
        return returnPoints;
    }

    public static List<Data> convertComplexAbsToData(List<Complex> originalPoints) {
        List<Data> returnPoints = new ArrayList<>();
        for (int i = 0; i < originalPoints.size(); i++) {
            returnPoints.add(new Data((double) i, originalPoints.get(i).abs()));
        }
        return returnPoints;
    }

    public static List<Data> convertComplexArgumentToData(List<Complex> originalPoints) {
        List<Data> returnPoints = new ArrayList<>();
        for (int i = 0; i < originalPoints.size(); i++) {
            returnPoints.add(new Data((double) i, originalPoints.get(i).getArgument()));
        }
        return returnPoints;
    }

}
