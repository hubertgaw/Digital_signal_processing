package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.App;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.*;

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
//            this.chartComponentFirstSignal = new ChartComponent();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        try {
            //TU ZMIANA GENEROWANEGO SYGNALU
            charts.get(0).generateSignal(App.getSelectedSignals().get(0));
            charts.get(1).generateSignal(App.getSelectedSignals().get(1));
            List<Data> resultsPoints = null;
            if (App.getSelectedOperation().equalsIgnoreCase("dodawanie")) {
                resultsPoints = Addition.performCalculating(
                        App.getSelectedSignals().get(1).getPoints(),
                        App.getSelectedSignals().get(0).getPoints());
            } else if (App.getSelectedOperation().equalsIgnoreCase("odejmowanie")) {
                resultsPoints = Subtraction.performCalculating(
                        App.getSelectedSignals().get(1).getPoints(),
                        App.getSelectedSignals().get(0).getPoints());
            } else if (App.getSelectedOperation().equalsIgnoreCase("mnożenie")) {
                resultsPoints = Multiplication.performCalculating(
                        App.getSelectedSignals().get(1).getPoints(),
                        App.getSelectedSignals().get(0).getPoints());
            } else if (App.getSelectedOperation().equalsIgnoreCase("dzielenie")) {
                resultsPoints = Division.performCalculating(
                        App.getSelectedSignals().get(1).getPoints(),
                        App.getSelectedSignals().get(0).getPoints());
            }
            charts.get(2).generateSignal(resultsPoints);
//            chartComponentFirstSignal.generateSignal(App.getSelectedSignals().get(0));
//            chartComponentSecondSignal.generateSignal(App.getSelectedSignals().get(1));
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        this.histogramComponent = new HistogramComponent();
        charts.get(2).drawChart();
//        chartComponentFirstSignal.drawChart();
        histogramComponent.drawHistogram(charts.get(2).getData());
        initComponent();
    }

    public void initComponent() {
        //TU TRZEBA ZMIENIC, CZY CHCEMY HISTOGRAM CZY WYKRES ZOBACZYC
//        add(chartComponentFirstSignal, 0, 1);
        add(charts.get(2), 0, 1);
    }
}