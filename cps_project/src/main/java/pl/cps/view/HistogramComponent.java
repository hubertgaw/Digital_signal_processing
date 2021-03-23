package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import pl.cps.signal.graphs.MinMaxExtractor;
import pl.cps.signal.graphs.SignalGraphToHistogramConverter;

public class HistogramComponent extends HBox {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private SignalGraphToHistogramConverter converter = new SignalGraphToHistogramConverter();
    private final int NUMBER_OF_BARS = 10;

    public HistogramComponent() {
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
    }

    public void drawHistogram(ObservableList<XYChart.Data<Double, Double>> data) {
        BarChart<String, Number> histogram =
                new BarChart<String, Number>(xAxis, yAxis);
        converter.convert(data, NUMBER_OF_BARS);

    }


}
