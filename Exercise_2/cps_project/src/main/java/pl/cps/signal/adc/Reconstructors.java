package pl.cps.signal.adc;

import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Reconstructors {

    public static double zeroOrderInterpolationForPoint(List<Data> data, double xValueToCalculateYValue) {
        Optional<Data> lowerPoint = data.stream().filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) {
                        if (data.getX() <= xValueToCalculateYValue) {
                            return true;
                        }
                        return false;
                    }
                }).max(Comparator.comparing(i -> i.getX())),
                greaterPoint = data.stream().filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) {
                        if (data.getX() >= xValueToCalculateYValue) {
                            return true;
                        }
                        return false;
                    }
                }).min(Comparator.comparing(i -> i.getX()));
        if (lowerPoint.isEmpty()||greaterPoint.isEmpty()){
            return 0;
        }

        return lowerPoint.get().getY();
    }

    public static List<Data> zeroOrderInterpolation(List<Data> data, int samplesPerSecond) {
        List<Data> resultData = new ArrayList<Data>();
        double maxX = data.stream().max(Comparator.comparing(i -> i.getX())).get().getX(),
                minX = data.stream().min(Comparator.comparing(i -> i.getX())).get().getX();
        for (double i = minX; i <= maxX; i += 1.0 / (double) samplesPerSecond) {
            resultData.add(new Data(i, zeroOrderInterpolationForPoint(data, i)));
        }
        return resultData;
    }

    public static List<Data> firstOrderInterpolation(List<Data> data, int samplesPerSecond) {
        List<Data> resultData = new ArrayList<Data>();
        double maxX = data.stream().max(Comparator.comparing(i -> i.getX())).get().getX(),
                minX = data.stream().min(Comparator.comparing(i -> i.getX())).get().getX();
        for (double i = minX; i <= maxX; i += 1.0 / (double) samplesPerSecond) {
            resultData.add(new Data(i, firstOrderInterpolationForPoint(data, i)));
        }
        return resultData;
    }

    public static double firstOrderInterpolationForPoint(List<Data> data, double xValueToCalculateYValue) {
        Data lowerPoint = data.stream().filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) {
                        if (data.getX() <= xValueToCalculateYValue) {
                            return true;
                        }
                        return false;
                    }
                }).max(Comparator.comparing(i -> i.getX())).get(),
                greaterPoint = data.stream().filter(new Predicate<Data>() {
                    @Override
                    public boolean test(Data data) {
                        if (data.getX() >= xValueToCalculateYValue) {
                            return true;
                        }
                        return false;
                    }
                }).min(Comparator.comparing(i -> i.getX())).get();
        if(lowerPoint==greaterPoint) {
            return lowerPoint.getX();
        }
        if((xValueToCalculateYValue<lowerPoint.getX())||(xValueToCalculateYValue>greaterPoint.getX())){
            return 0.0;
        }
        return lowerPoint.getY() + ((xValueToCalculateYValue - lowerPoint.getX()) * (greaterPoint.getY() - lowerPoint.getY()));//y1+(x−x1)*(y2−y1)
    }
}
