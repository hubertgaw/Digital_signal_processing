package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.App;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;

import java.util.ArrayList;
import java.util.List;


public class MainLayout extends GridPane {

    private ChartComponent chartComponentFirstSignal;
    private ChartComponent chartComponentSecondSignal;
    // list of chartComponents - one component - one signal
    private List<ChartComponent> charts = new ArrayList<>(3);
    private HistogramComponent histogramComponent;


    public MainLayout() {
        try {
            charts.add(new ChartComponent());
            charts.add(new ChartComponent());
            charts.add(new ChartComponent());
            this.chartComponentFirstSignal = new ChartComponent();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        try {
            //TU ZMIANA GENEROWANEGO SYGNALU
            charts.get(0).generateSignal(App.getSelectedSignals().get(0));
            charts.get(1).generateSignal(App.getSelectedSignals().get(1));
//            chartComponentFirstSignal.generateSignal(App.getSelectedSignals().get(0));
//            chartComponentSecondSignal.generateSignal(App.getSelectedSignals().get(1));
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        this.histogramComponent = new HistogramComponent();
        charts.get(0).drawChart();
//        chartComponentFirstSignal.drawChart();
        histogramComponent.drawHistogram(charts.get(0).getData());
        initComponent();
    }

    public void initComponent() {
        //TU TRZEBA ZMIENIC, CZY CHCEMY HISTOGRAM CZY WYKRES ZOBACZYC
//        add(chartComponentFirstSignal, 0, 1);
        add(charts.get(0), 0, 1);
    }
}