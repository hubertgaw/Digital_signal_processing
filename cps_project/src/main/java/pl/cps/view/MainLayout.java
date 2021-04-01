package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.App;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.*;

import java.util.ArrayList;
import java.util.List;


public class MainLayout extends GridPane {

    // list of chartComponents - one component - one signal
    private List<ChartComponent> charts = new ArrayList<>(3);
    private HistogramComponent histogramComponent;


    public MainLayout() {
        try {
            charts.add(new ChartComponent());
            charts.add(new ChartComponent());
            charts.add(new ChartComponent());
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
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
    }

    public void initChart(int whichSignalToShow) {
        getChildren().clear();
        charts.get(whichSignalToShow).drawChart();
        add(charts.get(whichSignalToShow), 0, 1);
    }

    public void initHistogram(int whichSignalToShow) {
        this.histogramComponent = new HistogramComponent();
        histogramComponent.drawHistogram(charts.get(whichSignalToShow).getData());
        add(histogramComponent, 0, 2);
    }

}