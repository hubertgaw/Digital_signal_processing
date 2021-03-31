package pl.cps.signal.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Addition implements Operation {

    private double firstX;
    private double lastX;
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
                        resultPoints.add(new Data(data1.getX(), data1.getY() + data2.getY()));
                        break;
                    }
                }
            }
        } else {
            for (Data data2 : pointsFromSecondSignal) {
                for (Data data1 : pointsFromFirstSignal) {
                    if (data2.getX() == data1.getX()) {
                        resultPoints.add(new Data(data2.getX(), data2.getY() + data1.getY()));
                        break;
                    }
                }
            }
        }
        return resultPoints;
    }
//
//        // flagi mowiace, w którym sygnale punkty się zaczynają/kończą
//        int whichSignalFirst, whichSignalLast;
//        // Listy są uporządkowane, więc wystarczy, że porownamy pierwszy element, zeby
//        // znalezc od jakiego X zaczac
//        if (pointsFromFirstSignal.get(0).getX() <= pointsFromSecondSignal.get(0).getX()) {
//            firstX = pointsFromFirstSignal.get(0).getX();
//            whichSignalFirst = 0;
//        } else {
//            firstX = pointsFromSecondSignal.get(0).getX();
//            whichSignalFirst = 1;
//        }
//        if (pointsFromFirstSignal.get(pointsFromFirstSignal.size() - 1).getX() >=
//                pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX()){
//            lastX = pointsFromFirstSignal.get(pointsFromFirstSignal.size() - 1).getX();
//            whichSignalLast = 0;
//        } else {
//            lastX = pointsFromSecondSignal.get(pointsFromSecondSignal.size() - 1).getX();
//            whichSignalLast = 1;
//        }
////        for (double x = firstX; x <= lastX; x += 0.001) {
////            int counter = 0;
////            double x_3decimalPoints = BigDecimal.valueOf(x)
////                    .setScale(3, RoundingMode.HALF_UP)
////                    .doubleValue();
////            if (whichSignalFirst == 0) {
//////                resultPoints.add(new Data(pointsFromFirstSignal.get(counter),))
////            }
////
////        }
//        boolean found = false;
//        if (whichSignalFirst == 0 && whichSignalLast == 0) {
//            for (Data data : pointsFromFirstSignal) {
//                for (Data data2 : pointsFromSecondSignal) {
//                    if (data2.getX() == data.getX()) {
//                        resultPoints.add(new Data(data.getX(), data.getY() + data2.getY()));
//                        found = true;
//                        break;
//                    }
//                }
//
//            }
//        }


}
