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
        xAxis.setLabel("t[s]");

        yAxis = new NumberAxis();
        yAxis.setLabel("A");


        series = new XYChart.Series<>();

        data = series.getData();

    }

    public ObservableList<XYChart.Data<Double, Double>> getData() {
        return data;
    }

    public void drawChart(String signalName) {
        getChildren().clear();
        if (generatedSignal instanceof ImpulseNoise || generatedSignal instanceof UnitImpulse) {
            discreteSignalChart = new ScatterChart(xAxis, yAxis);
            discreteSignalChart.setTitle(signalName);
            discreteSignalChart.getData().add(series);
            discreteSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
            getChildren().add(discreteSignalChart);
        } else {
            continuousSignalChart = new LineChart(xAxis, yAxis);
            continuousSignalChart.setTitle(signalName);
            continuousSignalChart.getData().add(series);
            continuousSignalChart.setCreateSymbols(false);
            continuousSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
            getChildren().add(continuousSignalChart);
        }

    }

    public void generateSignal(Signal signal) throws SignalIsNotTransmittedInThisTime {
        data.clear();
        generatedSignal = signal;
        generatedSignal.generateChart(data);
    }

    // method for operation signal
    public void generateSignal(List<Data> resultPoints) {
        data.clear();
        resultSignal = new OperationSignal();
        resultSignal.generateChart(data, resultPoints);
    }

}