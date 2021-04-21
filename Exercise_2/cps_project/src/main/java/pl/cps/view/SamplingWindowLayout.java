package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.Signal;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.List;

// layout for window which appears after clicking sampleBtn
public class SamplingWindowLayout extends GridPane {

    private ChartComponent sampleChart;

    public SamplingWindowLayout() throws SignalIsNotTransmittedInThisTime {
        sampleChart = new ChartComponent();
    }

    public List<Data> addSampledChart(Signal signal, Double freqValue) throws SignalIsNotTransmittedInThisTime {
        return sampleChart.generateSignal(signal, freqValue);
    }

    public void initSampledChart() {
        sampleChart.drawDiscreteChart("Sygnał po próbkowaniu");
        add(sampleChart, 0, 1);
    }
}
