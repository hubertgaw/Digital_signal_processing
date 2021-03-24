package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Map;

public interface Converter {
     Map<String, Integer> convert(ObservableList<XYChart.Data<Double, Double>> data, int barsNumber);

}
