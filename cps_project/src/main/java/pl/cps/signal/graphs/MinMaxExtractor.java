package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Comparator;

public class MinMaxExtractor {
    public double[] extract(ObservableList<XYChart.Data<Double, Double>> data) {
        Double max = data
                .stream()
                .max(Comparator.comparing(XYChart.Data::getYValue))
                .get()
                .getYValue();
        System.out.println(max);
        Double min = data
                .stream()
                .min(Comparator.comparing(XYChart.Data::getYValue))
                .get()
                .getYValue();
        System.out.println(min);

        return new double[]{min, max} ;
    }
}
