package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import pl.cps.signal.emiters.*;


public class ChartComponent extends HBox {

    private NumberAxis xAxis, yAxis;
    private XYChart.Series<Double, Double> series;
    private ObservableList<XYChart.Data<Double, Double>> data;
    private Signal generatedSignal;
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

    public void generateUnitJump() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new UnitJump(10.0, 0.0, 6, 2.0);
        generatedSignal.generateChart(data);
    }

    public void generateTriangularSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new TriangularSignal(3.0,1.0,6.0,2.0, 0.7);
        generatedSignal.generateChart(data);
    }

    public void generateUniformlyDistributedNoise() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new UniformlyDistributedNoise(2.0,1.0,3.0);
        generatedSignal.generateChart(data);
    }

    public void generateTwoHalfSinusoidalSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new TwoHalfSinusoidalSignal(2.0, 1.0, 3.0, 2.0);
        generatedSignal.generateChart(data);
    }

    public void generateSymmetricalSquareSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal  = new SymmetricalSquareSignal(2.0, 1.0, 3.0, 1.0, 0.3);
        generatedSignal.generateChart(data);
    }

    public void generateSquareSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new SquareSignal(3.0,1.0, 4.0, 2.0, 0.5);
        generatedSignal.generateChart(data);
    }

    public void generateSinusoidalSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new SinusoidalSignal(4, 0.0, 10.0, 2);
        generatedSignal.generateChart(data);
    }

    public void generateOneHalfSinusoidalSignal() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new OneHalfSinusoidalSignal(3.0, 0.0, 3.0,2.0);
        generatedSignal.generateChart(data);
    }

    public void generateImpulseNoise() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new ImpulseNoise(2.0, 1.0, 3.0, 3, 70);
        generatedSignal.generateChart(data);
    }

    public void generateGaussianNoise() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new GaussianNoise(1.0, 0, 5.0);
        generatedSignal.generateChart(data);
    }

    public void generateUnitImpulse() throws SignalIsNotTransmittedInThisTime {
        generatedSignal = new UnitImpulse(1.0, 0, 5.0, 2.0, 2);
        generatedSignal.generateChart(data);
    }



}