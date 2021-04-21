package pl.cps.signal.adc;

import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class Quantizer {

    private List<Double> levels = new ArrayList<>();

    public List<Double> getLevels() {
        return levels;
    }

    public void createLevels(List<Data> pointsFromSampledSignal, int numberOfLevels)
            throws SignalIsNotTransmittedInThisTime {
        levels.clear();
        List<Data> pointsAfterQuantization = new ArrayList<>();
        Data minY = pointsFromSampledSignal
                .stream()
                .min(Comparator.comparing(Data::getY))
                .orElseThrow(NoSuchElementException::new);
        Data maxY = pointsFromSampledSignal
                .stream()
                .max(Comparator.comparing(Data::getY))
                .orElseThrow(NoSuchElementException::new);
        double step;
        if (numberOfLevels == 1) {
            step = 0;
        } else if (numberOfLevels > 1) {
            step = (maxY.getY() - minY.getY()) / (numberOfLevels - 1);
        } else {
            throw new SignalIsNotTransmittedInThisTime();
        }
        for (int i = 0; i < numberOfLevels; i++) {
            getLevels().add(minY.getY() + step * i);
        }

    }

    public double findNearestNeighbourInLevels(double point) {
        double nearestNeighbour = levels
                .stream()
                .min(Comparator.comparingDouble(i -> Math.abs(i - point)))
                .orElseThrow(NoSuchElementException::new);
        return nearestNeighbour;
    }

    public double findNearestLowerNeighbourInLevels(double point) {
        return levels.stream()
                .filter(new Predicate<Double>() {
                    @Override
                    public boolean test(Double aDouble) {
                        if (aDouble > point) {
                            return false;
                        }
                        return true;
                    }
                })
                .min(Comparator.comparingDouble(i -> Math.abs(i - point)))
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Data> roundedQuantization(List<Data> pointsFromSampledSignal,
                                          int numberOfLevels) throws SignalIsNotTransmittedInThisTime {
        List<Data> pointsAfterQuantization = new ArrayList<>();
        createLevels(pointsFromSampledSignal, numberOfLevels);
        for (Data pointFromSampledSignal : pointsFromSampledSignal) {
            //add to list after quantization point which consists (x from sampled signal,
            // y is nearest point in levels from original y point)
            pointsAfterQuantization.add(new Data(pointFromSampledSignal.getX(),
                    findNearestNeighbourInLevels(pointFromSampledSignal.getY())));
        }
        return pointsAfterQuantization;
    }

    public List<Data> cutQuantization(List<Data> pointsFromSampledSignal,
                                      int numberOfLevels) {
        List<Data> result = new ArrayList<Data>();
        try {
            createLevels(pointsFromSampledSignal, numberOfLevels);
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        pointsFromSampledSignal.stream()
                .forEach(i -> result.add(new Data(i.getX(), findNearestLowerNeighbourInLevels(i.getY()))));
        return result;
    }


    //todo implement
//    public List<Data> truncatedQuantization(List<Data> pointsFromSampledSignal,
//                                            int numberOfLevels) throws SignalIsNotTransmittedInThisTime {
//        createLevels(pointsFromSampledSignal, numberOfLevels);
//
//    }

}
