package pl.cps.signal.model;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import static pl.cps.signal.model.MathematicalOperation.isEqualWithAccuracy;

public class Addition {

    private static double ITERATIONS_STEP_SIZE = 0.0625, COMPARISON_ACCURRACY=ITERATIONS_STEP_SIZE;

    public static List<Data> performCalculating(List<Data> pointsFromFirstSignal,
                                                List<Data> pointsFromSecondSignal) {
        boolean firstSignalLonger = pointsFromFirstSignal.size() >= pointsFromSecondSignal.size();
        boolean found = false;
        double beginX = Double.MAX_VALUE, endX = Double.MIN_VALUE;
        int firstIterator = 0, secondIterator = 0;
        double value;
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
        for (double i = beginX; i < endX; i += ITERATIONS_STEP_SIZE) {
            value = 0;

            if (isEqualWithAccuracy(pointsFromFirstSignal.get(firstIterator).getX(), i,COMPARISON_ACCURRACY)) {
                value += pointsFromFirstSignal.get(firstIterator).getY();
                firstIterator++;
                firstIterator = firstIterator % pointsFromFirstSignal.size();
            }
            if (isEqualWithAccuracy(pointsFromSecondSignal.get(secondIterator).getX(), i,COMPARISON_ACCURRACY)) {

                value += pointsFromSecondSignal.get(secondIterator).getY();
                secondIterator++;
                secondIterator = secondIterator % pointsFromSecondSignal.size();
            }

            resultPoints.add(new Data(i, value));
        }


        return resultPoints;
    }


}
