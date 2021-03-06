package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import pl.cps.App;
import pl.cps.signal.graphs.SignalGraphToHistogramConverter;

import java.util.Map;

public class HistogramComponent extends HBox {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private XYChart.Series<String, Number> series;
    private SignalGraphToHistogramConverter converter = new SignalGraphToHistogramConverter();


    public HistogramComponent() {
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
        this.series = new XYChart.Series<>();
    }

    public void drawHistogram(ObservableList<XYChart.Data<Double, Double>> data) {
        BarChart<String, Number> histogram =
                new BarChart<String, Number>(xAxis, yAxis);
        Map<String, Integer> histogramData = converter.convert(data,
                Integer.parseInt(App.getSellectdOptionFromMenu(App.getBarsNumberMenu())));
        for (Map.Entry<String, Integer> entry : histogramData.entrySet()) {
            String tmpString = entry.getKey();
            Integer tmpValue = entry.getValue();
            XYChart.Data<String, Number> dataChart = new XYChart.Data<>(tmpString, tmpValue);
            series.getData().add(dataChart);

        }

        histogram.getData().addAll(series);
        getChildren().add(histogram);
    }


}
