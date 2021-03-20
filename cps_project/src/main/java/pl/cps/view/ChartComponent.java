package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import pl.cps.logic.model.UniformNoise;

/**
 * @author Thomas Darimont
 */
public class ChartComponent extends HBox {

    public ChartComponent() {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("x");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Sine");

        ObservableList<XYChart.Data<Number, Number>> data = series.getData();

        UniformNoise uniformNoise = new UniformNoise(6, 1.0, 7.5);
        uniformNoise.generate(data);


        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Sine function");
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)

        getChildren().add(lineChart);
    }
}