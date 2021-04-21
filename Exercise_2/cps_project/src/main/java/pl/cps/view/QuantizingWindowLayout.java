package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.List;

public class QuantizingWindowLayout extends GridPane {
    private ChartComponent quantizedChart;

    public QuantizingWindowLayout() throws SignalIsNotTransmittedInThisTime {
        quantizedChart = new ChartComponent();
    }

    //void because we don't generate points in this method
    //points were generated earlier, we just need put this
    //points into chart
    public void addQuantizedChart(List<Data> points) {
        quantizedChart.generateSignal(points);
    }

    public void initQuantizedChart() {
        quantizedChart.drawDiscreteChart("Sygnał po kwantyzacji");
        add(quantizedChart, 0, 1);
    }
}
