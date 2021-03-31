package pl.cps.signal.model;

import java.util.ArrayList;
import java.util.List;

public class Addition implements Operation {

    private double firstX;
    private double lastX;
    private List<Data> resultPoints = new ArrayList<>();

    @Override
    public void performCalculating(List<Data> pointsFromFirstSignal,
                                   List<Data> pointsFromSecondSignal) {
        // Listy są uporządkowane, więc wystarczy, że porownamy pierwszy element, zeby
        // znalezc od jakiego X zaczac
        if (pointsFromFirstSignal.get(0).getX() <= pointsFromSecondSignal.get(0).getX()) {
            firstX = pointsFromFirstSignal.get(0).getX();
        } else {
            firstX = pointsFromSecondSignal.get(0).getX();
        }
        if (pointsFromFirstSignal.get(pointsFromFirstSignal.size() - 1).getX() >=
                pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX()){
            lastX = pointsFromFirstSignal.get(pointsFromFirstSignal.size() - 1).getX();
        } else {
            lastX = pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX();
        }

    }
}
