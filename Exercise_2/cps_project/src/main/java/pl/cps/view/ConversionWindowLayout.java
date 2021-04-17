package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.Signal;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;

// layout for window which appears after clicking sampleBtn
public class ConversionWindowLayout extends GridPane {

    private ChartComponent sampleChart;

    public ConversionWindowLayout() throws SignalIsNotTransmittedInThisTime {
        sampleChart = new ChartComponent();
    }

    public void addSampledChart(Signal signal) throws SignalIsNotTransmittedInThisTime {

//        sampleChart.generateSignal(signal);
    }
}
