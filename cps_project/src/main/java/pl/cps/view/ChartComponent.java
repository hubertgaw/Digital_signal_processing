package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import pl.cps.signal.emiters.*;
import pl.cps.signal.model.Data;

import java.util.List;


public class ChartComponent extends HBox {

    private NumberAxis xAxis, yAxis;
    private XYChart.Series<Double, Double> series;
    private ObservableList<XYChart.Data<Double, Double>> data;
    private Signal generatedSignal;
    private OperationSignal resultSignal;
    private LineChart continuousSignalChart;
    private ScatterChart discreteSignalChart;

    public ChartComponent() throws SignalIsNotTransmittedInThisTime {

        xAxis = new NumberAxis();
        xAxis.setLabel("x");

        yAxis = new NumberAxis();
        yAxis.setLabel("y");

        series = new XYChart.Series<>();

        data = series.getData();

    }

    public ObservableList<XYChart.Data<Double, Double>> getData() {
        return data;
    }

    public void drawChart() {
        if (generatedSignal instanceof ImpulseNoise || generatedSignal instanceof UnitImpulse) {
//            ScatterChart<Number, Number> scatterChart = new ScatterChart<Number, Number>(xAxis, yAxis);
            discreteSignalChart = new ScatterChart(xAxis, yAxis);
            discreteSignalChart.setTitle("Discrete signals");
            discreteSignalChart.getData().add(series);
            discreteSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
            getChildren().add(discreteSignalChart);
        } else {
//            LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
            continuousSignalChart = new LineChart(xAxis, yAxis);
            continuousSignalChart.getData().add(series);
            continuousSignalChart.setCreateSymbols(false);
            continuousSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
            getChildren().add(continuousSignalChart);
        }

    }

    public void generateSignal(Signal signal) throws SignalIsNotTransmittedInThisTime {
        generatedSignal = signal;
        generatedSignal.generateChart(data);
    }

    // method for operation signal
    public void generateSignal(List<Data> resultPoints) {
        resultSignal = new OperationSignal();
        resultSignal.generateChart(data, resultPoints);
    }

}