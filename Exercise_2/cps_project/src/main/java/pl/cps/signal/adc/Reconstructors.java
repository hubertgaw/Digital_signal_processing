package pl.cps.signal.adc;

import pl.cps.signal.emiters.Signal;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (lowerPoint.isEmpty() || greaterPoint.isEmpty()) {
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
        if (lowerPoint == greaterPoint) {
            return lowerPoint.getX();
        }
        if ((xValueToCalculateYValue < lowerPoint.getX()) || (xValueToCalculateYValue > greaterPoint.getX())) {
            return 0.0;
        }
        return lowerPoint.getY() + ((xValueToCalculateYValue - lowerPoint.getX()) * (greaterPoint.getY() - lowerPoint.getY()));//y1+(x−x1)*(y2−y1)
    }

    public static List<Data> sincReconstruction(List<Data> data, int samplesPerSecond, int numberOfSamplesUsedToGetFormula) {
        List<Data> resultData = new ArrayList<Data>();
        int n = numberOfSamplesUsedToGetFormula;
        double lowerPointX = data.stream()
                .min(Comparator.comparing(i -> i.getX())).get().getX(),
                greaterPointX = data.stream()
                        .max(Comparator.comparing(i -> i.getX())).get().getX();
        if (n > data.size()) {
            n = data.size();
        }
        for (double i = lowerPointX; i < greaterPointX; i += 1.0 / samplesPerSecond) {
            resultData.add(new Data(i, Reconstructors.sincReconstructionForPoint(data, i, n)));
        }
        return resultData;
    }

    public static double sincReconstructionForPoint(List<Data> data, double xValueToCalculateYValue, int numberOfSamplesUsedToGetFormula) {
        double result = 0, Ts = data.get(1).getX() - data.get(0).getX();
        for (int i = 0; i < numberOfSamplesUsedToGetFormula; i++) {
            result += data.get(i).getY() * sinc(xValueToCalculateYValue / Ts - i);
        }
        return result;
    }

    public static double sinc(double t) {
        if (t == 0) {
            return 0;
        }
        return (Math.sin(Math.PI * t)) / (Math.PI * t);
    }

    public static double meanSquareError(Signal signal, List<Data> approxDatas) {
        double errorValue = approxDatas.stream().mapToDouble(i ->
        {
            try {
                return Math.pow((i.getY() - signal.calculateValue(i.getX())), 2);
            } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
                signalIsNotTransmittedInThisTime.printStackTrace();
            }
            return 0;
        }).sum();
        return errorValue / approxDatas.size();
    }

    public static double signalToNoiseRatio(Signal signal, List<Data> approxDatas) {
        double errorValue = approxDatas.stream().mapToDouble(i ->
                i.getY() * i.getY()).sum() / approxDatas.stream().mapToDouble(i ->
        {
            try {
                return Math.pow((i.getY() - signal.calculateValue(i.getX())), 2);
            } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
                signalIsNotTransmittedInThisTime.printStackTrace();
            }
            return 0;
        }).sum();
        return 10 * Math.log10(errorValue);
    }

    public static double peakSignalToNoiseRatio(Signal signal, List<Data> approxDatas) {
        double errorValue = approxDatas.stream().max(Comparator.comparing(Data::getY)).get().getY() / meanSquareError(signal, approxDatas);
        return 10 * Math.log10(errorValue);
    }

    public static double maximumDiffrence(Signal signal, List<Data> approxDatas) {
        return approxDatas.stream().mapToDouble(i -> {
            try {
                return Math.abs(i.getY() - signal.calculateValue(i.getX()));
            } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            }
            return 0;
        }).max().getAsDouble();

    }
}
