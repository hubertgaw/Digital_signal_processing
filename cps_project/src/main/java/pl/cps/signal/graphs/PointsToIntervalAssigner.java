package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.view.MainLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointsToIntervalAssigner {
    private List<Double> intervalLimits = new ArrayList<>();
    private List<Integer> pointsInEachInterval = new ArrayList<>();
    private Map<String, Integer> pointsInIntervals = new HashMap<>();

    public void assign(double[] minMax, double intervalSize,
                       ObservableList<XYChart.Data<Double, Double>> data) {
        createIntervalLimits(minMax[0], minMax[1], intervalSize);
        assignPointsToIntervals(data);
        convertListsToMap(minMax[0], intervalSize);
    }

    public void createIntervalLimits(double min, double max, double internalSize) {
        for (double i = min + internalSize; i < max - internalSize; i = i + internalSize) {
            intervalLimits.add(i);
        }
        intervalLimits.add(max); //zeby zawsze max konczyl ostatni przedzial
        for (int i = 0; i < intervalLimits.size(); i++) {
            pointsInEachInterval.add(0);
        }
    }

    public void assignPointsToIntervals(ObservableList<XYChart.Data<Double, Double>> data) {
        for (XYChart.Data<Double, Double> chartData : data) {
            for (int i = 0; i < intervalLimits.size(); i++) {
                if (chartData.getYValue() <= intervalLimits.get(i)) {
                    pointsInEachInterval.set(i, pointsInEachInterval.get(i) + 1);
                    break;
                }
            }
        }
    }

    public void convertListsToMap(double min, double interval) {
        // pierwsszy przedział zamkniety z obu stron (nie wiem czy to dobre podejscie
        // ale ktorys przedzial musi byc zamkniety z obu stron
        pointsInIntervals.put("[" + String.format("%.2f", min) + ","
                + String.format("%.2f", min + interval) + "]", pointsInEachInterval.get(0));
        for (int i = 1; i < intervalLimits.size(); i++) {
            pointsInIntervals.put("(" + String.format("%.2f", intervalLimits.get(i) - interval) + ","
                    + String.format("%.2f", intervalLimits.get(i)) + "]", pointsInEachInterval.get(i));
        }
    }
}
