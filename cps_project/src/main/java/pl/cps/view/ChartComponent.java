package pl.cps.view;

import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import pl.cps.logic.model.UniformNoise;
import pl.cps.signal.emiters.*;

/**
 * @author Thomas Darimont
 */
public class ChartComponent extends HBox {

    public ChartComponent() throws SignalIsNotTransmittedInThisTime {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("x");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Sine");

        ObservableList<XYChart.Data<Number, Number>> data = series.getData();

        UniformNoise uniformNoise = new UniformNoise(6, 1.0, 7.5);
//        uniformNoise.generate(data);


        Signal gaussianNoise = new GaussianNoise(4);
//        gaussianNoise.generateChart(data); //todo fix

        Signal impulseNoise = new ImpulseNoise(2.0, 1.0, 3.0, 1);
//        impulseNoise.generateChart(data);

        Signal oneHalfSinusoidalSignal = new OneHalfSinusoidalSignal(3.0, 0.0, 3.0,2.0);
//        oneHalfSinusoidalSignal.generateChart(data);

        Signal sinus = new SinusoidalSignal(4, 0.0, 10.0, 2);
//        sinus.generateChart(data);

        Signal squareSignal = new SquareSignal(3.0,1.0, 4.0, 2.0, 0.5);
//        squareSignal.generateChart(data); //todo fix

        Signal symetricalSquareSignal  = new SymetricalSquareSignal(2.0, 1.0, 3.0, 1.0, 1);
//        symetricalSquareSignal.generateChart(data); //todo fix

        Signal twoHalfSinusoidalSignal = new TwoHalfSinusoidalSignal(2.0, 1.0, 3.0, 2.0);
//        twoHalfSinusoidalSignal.generateChart(data);

        Signal uniformlyDistributedNoise = new UniformlyDistributedNoise(2.0,1.0,3.0);
        uniformlyDistributedNoise.generateChart(data);

        LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Sine function");
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
        lineChart.getStyleClass().add("graphStyle.css"); //TODO nie dziala polaczenie z cssem (do zmiany grubosci linii)

        getChildren().add(lineChart);
    }
}