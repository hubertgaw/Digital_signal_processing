package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public interface Converter {
    public void convert(ObservableList<XYChart.Data<Double, Double>> data, int barsNumber);

}
