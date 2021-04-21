package pl.cps.signal.adc;

import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Quantizer {

    private List<Double> levels;

    public List<Double> getLevels() {
        return levels;
    }

    public void createLevels(List<Data> pointsFromSampledSignal, int numberOfLevels)
            throws SignalIsNotTransmittedInThisTime {
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

    //todo implement
//    public List<Data> truncatedQuantization(List<Data> pointsFromSampledSignal,
//                                            int numberOfLevels) throws SignalIsNotTransmittedInThisTime {
//        createLevels(pointsFromSampledSignal, numberOfLevels);
//
//    }

}
