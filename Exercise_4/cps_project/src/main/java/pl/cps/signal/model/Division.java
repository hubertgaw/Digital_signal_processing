package pl.cps.signal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static pl.cps.signal.model.MathematicalOperation.isEqualWithAccuracy;

public class Division {

    private static double ITERATIONS_STEP_SIZE = 0.001, COMPARISON_ACCURRACY=ITERATIONS_STEP_SIZE/1000;

    public static List<Data> performCalculating(List<Data> pointsFromFirstSignal,
                                                List<Data> pointsFromSecondSignal) {
        boolean firstSignalLonger = pointsFromFirstSignal.size() >= pointsFromSecondSignal.size();
        boolean foundBothPointsInThisIteration = false;
        double beginX = Double.MAX_VALUE, endX = Double.MIN_VALUE;
        int firstIterator = 0, secondIterator = 0;
        double value1 = 0,value2 = 0;
        List<Data> resultPoints = new ArrayList<>();

        Collections.sort(pointsFromFirstSignal);
        Collections.sort(pointsFromSecondSignal);
        beginX = pointsFromFirstSignal.get(0).getX();
        endX = pointsFromFirstSignal.get(pointsFromFirstSignal.size() - 1).getX();
        if (beginX > pointsFromSecondSignal.get(0).getX()) {
            beginX = pointsFromSecondSignal.get(0).getX();
        }
        if (endX < pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX()) {
            endX = pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX();
        }
        for (double i = 0; i < endX; i += ITERATIONS_STEP_SIZE) {
            foundBothPointsInThisIteration = true;

            if (isEqualWithAccuracy(pointsFromFirstSignal.get(firstIterator).getX(), i, COMPARISON_ACCURRACY)) {
                value1 = pointsFromFirstSignal.get(firstIterator).getY();
                firstIterator++;
                firstIterator = firstIterator % pointsFromFirstSignal.size();
            } else {
                foundBothPointsInThisIteration = false;
            }
            if (isEqualWithAccuracy(pointsFromSecondSignal.get(secondIterator).getX(), i, COMPARISON_ACCURRACY)) {
                value2 = pointsFromSecondSignal.get(secondIterator).getY();
                secondIterator++;
                secondIterator = secondIterator % pointsFromSecondSignal.size();

            } else {
                foundBothPointsInThisIteration = false;
            }
            if (foundBothPointsInThisIteration) {
                if (value2 == 0) {
                    Data maxValueData = resultPoints.stream().max(Comparator.comparing(v -> v.getY())).orElse(new Data(0d,0d));
                    Double maxValue = maxValueData.getY();
                    resultPoints.add(new Data(i, maxValue));
                } else {
                    resultPoints.add(new Data(i, value1 / value2));
                }
            } else {
                resultPoints.add(new Data(i, 0.0));
            }
        }
        return resultPoints;
    }
}
