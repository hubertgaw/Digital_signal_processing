package pl.cps.signal.exercise4;

import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://www.sanfoundry.com/java-program-compute-discrete-fast-fourier-transform-approach/

public class FFTTransformer {

    public static List<Complex> fftTransform(List<Complex> inputPoints) {
        int N = inputPoints.size();
        if (N == 1) {
            return Arrays.asList(inputPoints.get(0));
        }
        if (N % 2 != 0) {
            throw new RuntimeException("N nie jest potęgą 2");
        }
        List<Complex> evenList = new ArrayList<>(N/2);
        for (int k = 0; k < N/2; k++) {
            evenList.add(inputPoints.get(2 * k));
        }
        List<Complex> evenAfterFft = fftTransform(evenList);

        List<Complex> oddList = evenList; //użycie jeszcze raz tablicy o określonej wielkości
        for (int k = 0; k <  N/2; k++) {
            oddList.set(k, inputPoints.get(2 * k + 1));
        }
        List<Complex> oddAfterFft = fftTransform(oddList);

        //initialize with default values
        List<Complex> finalList = new ArrayList<>(N);
        for (int i = 0; i < N; i++){
            finalList.add(null);
        }

        //combine
        for (int k = 0; k < N/2; k++) {
            double angle = -2 * k * Math.PI / N;
            Complex number = new Complex(Math.cos(angle), Math.sin(angle));
            finalList.set(k, evenAfterFft.get(k).add(number.multiply(oddAfterFft.get(k))));
            finalList.set(k + N / 2, evenAfterFft.get(k).subtract(number.multiply(oddAfterFft.get(k))));
        }
        return finalList;
    }
}
