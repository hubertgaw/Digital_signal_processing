package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.List;

public class ReconstructingWindowLayout extends GridPane {
    private ChartComponent reconstructedChart;

    public ReconstructingWindowLayout() throws SignalIsNotTransmittedInThisTime {
        reconstructedChart = new ChartComponent();
    }

    //void because we don't generate points in this method
    //points were generated earlier, we just need put this
    //points into chart
    public void addReconstructedChart(List<Data> points) {
        reconstructedChart.generateSignal(points);
    }

    public void initReconstructedChart() {
        reconstructedChart.drawContinuousChart("Sygnał po rekonstrukcji");
        add(reconstructedChart, 0, 1);
    }
}
