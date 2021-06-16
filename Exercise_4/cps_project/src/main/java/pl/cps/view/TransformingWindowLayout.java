package pl.cps.view;

import javafx.scene.layout.GridPane;
import org.apache.commons.math3.complex.Complex;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.List;

public class TransformingWindowLayout extends GridPane {
    private ChartComponent transformedChart;

    public TransformingWindowLayout() {
        transformedChart = new ChartComponent();
    }



    public void addTransformedChart(List<Data> points) {

        transformedChart.generateSignal(points);
    }

    public void initReconstructedChart() {
        transformedChart.drawContinuousChart("Sygnał po transformacji");
        add(transformedChart, 0, 0);
    }
}
