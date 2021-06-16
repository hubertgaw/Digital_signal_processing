package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import pl.cps.signal.emiters.*;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;


public class ChartComponent extends HBox {

    private NumberAxis xAxis, yAxis;
    private XYChart.Series<Double, Double> series;
    private ObservableList<XYChart.Data<Double, Double>> data;
    private Signal generatedSignal;
    private OperationSignal resultSignal;
    private LineChart continuousSignalChart;
    private ScatterChart discreteSignalChart;

    public ChartComponent() {

        xAxis = new NumberAxis();
        xAxis.setLabel("numer próbki");

        yAxis = new NumberAxis();
        yAxis.setLabel("liczba urojona");


        series = new XYChart.Series<>();

        data = series.getData();

    }

    public ObservableList<XYChart.Data<Double, Double>> getData() {
        return data;
    }

    public void drawContinuousChart(String signalName) {
        getChildren().clear();
        continuousSignalChart = new LineChart(xAxis, yAxis);
        continuousSignalChart.setTitle(signalName);
        continuousSignalChart.getData().add(series);
        continuousSignalChart.setCreateSymbols(false);
        continuousSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
        getChildren().add(continuousSignalChart);
    }

    public void drawDiscreteChart(String signalName) {
        getChildren().clear();
        discreteSignalChart = new ScatterChart(xAxis, yAxis);
        discreteSignalChart.setTitle(signalName);
        discreteSignalChart.getData().add(series);
        discreteSignalChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)
        getChildren().add(discreteSignalChart);
    }

    public List<Data> generateSignal(Signal signal, double sampleFrequency) throws SignalIsNotTransmittedInThisTime {
        data.clear();
        generatedSignal = signal;
        return generatedSignal.generateChart(data, sampleFrequency);
    }

    // method for operation signal
    public void generateSignal(List<Data> resultPoints) {
        data.clear();
        resultSignal = new OperationSignal();
        resultSignal.generateChart(data, resultPoints);
    }

}