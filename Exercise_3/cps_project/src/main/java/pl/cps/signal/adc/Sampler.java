package pl.cps.signal.adc;

import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

// dla sygnałów z emiters próbkowanie polega po prostu na wygenerowaniu
// sygnału z niższą częstotliwością, ale przy sygnale wynikowym potrzebujemy
// osobnej metody, która przekonwertuje nam punkty z sygnału wynikowego
// na punktu zspróbkowane - w praktyce po prostu "przerzedamy" punkty z
// sygnału wynikowego
public class Sampler {
    public static List<Data> sampling(List<Data> resultPoints, double sampleFreq) {
        List<Data> pointsAfterSampling = new ArrayList<>();
        double jumpRange = 1.0 / sampleFreq;
        double startTime = resultPoints.get(0).getX();
        double stopTime = resultPoints.get(resultPoints.size() - 1).getX();
        for (double i = startTime; i < stopTime; i += jumpRange) {
            double nearestValue = findNearestDoubleInList(resultPoints.stream().mapToDouble(Data::getX).
                    boxed().collect(Collectors.toList()), i);
            for (Data point : resultPoints) {
                if (point.getX() == nearestValue) {
                    pointsAfterSampling.add(point);
                    break;
                }
            }
        }

        return pointsAfterSampling;
    }

  //  private static Data findNearestDoubleInList(DoubleStream mapToDouble) {
   // }

    private static double findNearestDoubleInList(List<Double> list, double d){
        if(list.size() == 0){
            return -1;
        }

        if(list.size() == 1){
            return list.get(0);
        }

        double current = list.get(0);
        double currentMin = Math.abs(list.get(0) - d);

        for(int i = 1; i < list.size(); i ++){

            double difference = Math.abs(list.get(i) - d);

            if(currentMin > difference){
                currentMin = difference;

                current = list.get(i);
            }
        }

        return current;
    }
}
