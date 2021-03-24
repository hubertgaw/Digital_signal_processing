package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.view.MainLayout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PointsToIntervalAssigner {
    private List<Double> intervalLimits = new ArrayList<>();
    private List<Integer> pointsInEachInterval = new ArrayList<>();
    private Map<String, Integer> pointsInIntervals = new LinkedHashMap<>();

    public Map<String, Integer> assign(double[] minMax, double intervalSize,
                       ObservableList<XYChart.Data<Double, Double>> data) {
        createIntervalLimits(minMax[0], minMax[1], intervalSize);
        assignPointsToIntervals(data);
        return (convertListsToMap(minMax[0], intervalSize));
    }

    public void createIntervalLimits(double min, double max, double internalSize) {

        // zeby wartosci byly w takiej samej "formie", nie powstawaly bledy przy zaokraglaniu
        min = BigDecimal.valueOf(min)
                .setScale(7, RoundingMode.HALF_UP)
                .doubleValue();

        max = BigDecimal.valueOf(max)
                .setScale(7, RoundingMode.HALF_UP)
                .doubleValue();

        internalSize = BigDecimal.valueOf(internalSize)
                .setScale(7, RoundingMode.HALF_UP)
                .doubleValue();

        //caly czas tu sie minimalnie zle generuje, prawdopodobnie, ze przy dodawaniu nie dodaje dokladnie
        //tylko czasem stosuje przyblizenie
        for (double i = min + internalSize; i <= max; i = i + internalSize) {
            intervalLimits.add(i);
        }
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

    public Map<String, Integer> convertListsToMap(double min, double interval) {
        // pierwsszy przedział zamkniety z obu stron (nie wiem czy to dobre podejscie
        // ale ktorys przedzial musi byc zamkniety z obu stron
        pointsInIntervals.put("[" + String.format("%.2f", min) + ","
                + String.format("%.2f", min + interval) + "]", pointsInEachInterval.get(0));
        for (int i = 1; i < intervalLimits.size(); i++) {
            pointsInIntervals.put("(" + String.format("%.2f", intervalLimits.get(i) - interval) + ","
                    + String.format("%.2f", intervalLimits.get(i)) + "]", pointsInEachInterval.get(i));
        }
        return pointsInIntervals;
    }
}
