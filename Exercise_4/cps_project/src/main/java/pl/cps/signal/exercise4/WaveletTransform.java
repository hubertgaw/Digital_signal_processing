package pl.cps.signal.exercise4;

import pl.cps.signal.exercise3.filtration.HighPassFiltration;
import pl.cps.signal.exercise3.filtration.LowPassFiltration;
import pl.cps.signal.exercise3.filtration.windows.RectangularWindow;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class WaveletTransform {


    public List<Data> waveletTransform(List<Data> x){
        List<Data> result = new ArrayList<>();
        LowPassFiltration low = new LowPassFiltration();
        HighPassFiltration high = new HighPassFiltration();
        List<Data> lowPoints , highPoints ;
        lowPoints = low.calculate(x,x.size(),(x.get(1).getX()-x.get(0).getX()),0.2,new RectangularWindow());
        highPoints = high.calculate(x,x.size(),(x.get(1).getX()-x.get(0).getX()),1,new RectangularWindow());
        for (int i = 0; i < x.size(); i+=2) {
            result.add(lowPoints.get(i));
            result.add(highPoints.get(i));
        }
        return result;
    }


//    public List<Data> waveletTransform(List<Data> x ) {
//        int N = x.size();
//        double[] h = {
//                (1.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
//                (3.0 + Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
//                (3.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2)),
//                (1.0 - Math.sqrt(3.0)) / (4.0 * Math.sqrt(2))
//        };
//        double[] X = new double[N];
//        for (int i = 0; i < N; i++) {
//            double sum = 0.0;
//            int begin = (i / 2) * 2;
//            for (int j = begin; j < begin + h.length; j++) {
//                double factor;
//                if (i % 2 == 0) {
//                    factor = h[(j - begin)];
//                } else {
//                    factor = h[(h.length - (j - begin) - 1)];
//                    if ((j - begin) % 2 == 1) {
//                        factor *= -1;
//                    }
//                }
//                sum += (factor * x.get(j % N).getY());
//            }
//            X[i] = sum;
//        }
//        /* mix samples */
//        double[] mixedX = new double[N];
//        int iterator = 0;
//        for (int i = 0; i < N; i += 2) {
//            mixedX[iterator++] = X[i];
//        }
//        for (int i = 1; i < N; i += 2) {
//            mixedX[iterator++] = X[i];
//        }
//
//        List<Data> result = new ArrayList<>();
//        for (int i = 0; i < mixedX.length; i++) {
//            result.add(new Data(i+0.0,mixedX[i]));
//        }
//        return result;
//    }
}
