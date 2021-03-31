package pl.cps.signal.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Division implements Operation{

    private List<Data> resultPoints = new ArrayList<>();


    @Override
    public List<Data> performCalculating(List<Data> pointsFromFirstSignal,
                                   List<Data> pointsFromSecondSignal) {
        boolean firstSignalLonger = pointsFromFirstSignal.size() >= pointsFromSecondSignal.size();
        boolean found = false;

        // todo zrobic dla reszty dziedziny, anie tylko pokrywajacej sie
        if (firstSignalLonger) {
            for (Data data1 : pointsFromFirstSignal) {
                for (Data data2 : pointsFromSecondSignal) {
                    if (data1.getX() == data2.getX()) {
                        if (data2.getY().equals(0d)) {
                            Data maxValueData = resultPoints.stream().max(Comparator.comparing(v -> v.getY())).get();
                            Double maxValue = maxValueData.getY();
                            resultPoints.add(new Data(data1.getX(), maxValue));
                        } else {
                            resultPoints.add(new Data(data1.getX(), data1.getY() / data2.getY()));
                            break;
                        }
                    }
                }
            }
        } else {
            for (Data data2 : pointsFromSecondSignal) {
                for (Data data1 : pointsFromFirstSignal) {
                    if (data2.getX() == data1.getX()) {
                        if (data1.getY().equals(0d)) {
                            Data maxValueData = resultPoints.stream().max(Comparator.comparing(v -> v.getY())).get();
                            Double maxValue = maxValueData.getY();
                            resultPoints.add(new Data(data2.getX(), maxValue));
                        } else {
                            resultPoints.add(new Data(data2.getX(), data2.getY() / data1.getY()));
                            break;
                        }
                    }
                }
            }
        }
        return resultPoints;
    }
}
