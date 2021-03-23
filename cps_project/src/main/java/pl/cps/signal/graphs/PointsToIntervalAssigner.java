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

    public void assign(double [] minMax, double internalSize,
                       ObservableList<XYChart.Data<Double, Double>> data) {
        createIntervalLimits(minMax[0], minMax[1], internalSize);
        assignPointsToIntervals(data);
    }

    public void createIntervalLimits (double min, double max, double internalSize) {
        for (double i = min + internalSize; i < max - internalSize; i = i + internalSize) {
            intervalLimits.add(i);
        }
        intervalLimits.add(max); //zeby zawsze max konczyl ostatni przedzial
        for (int i = 0; i < intervalLimits.size(); i++) {
            pointsInEachInterval.add(0);
        }
    }

    public void assignPointsToIntervals (ObservableList<XYChart.Data<Double, Double>> data) {
        for (XYChart.Data<Double, Double> chartData : data) {
            for (int i = 0; i < intervalLimits.size(); i++) {
                if (chartData.getYValue() <= intervalLimits.get(i)) {
                    pointsInEachInterval.set(i, pointsInEachInterval.get(i) + 1);
                    break;
                }
            }
        }
        int m = 3 + 6;

    }
}
