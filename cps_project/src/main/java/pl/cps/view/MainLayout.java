package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.App;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;



public class MainLayout extends GridPane {

    private ChartComponent chartComponent;
    private HistogramComponent histogramComponent;


    public MainLayout() {
        try {
            this.chartComponent = new ChartComponent();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        try {
            //TU ZMIANA GENEROWANEGO SYGNALU
            chartComponent.generateSignal(App.getSelectedSignals().get(0));
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        this.histogramComponent = new HistogramComponent();
        chartComponent.drawChart();
        histogramComponent.drawHistogram(chartComponent.getData());
        initComponent();
    }

    public void initComponent() {
        //TU TRZEBA ZMIENIC, CZY CHCEMY HISTOGRAM CZY WYKRES ZOBACZYC
        add(chartComponent, 0, 1);
    }
}