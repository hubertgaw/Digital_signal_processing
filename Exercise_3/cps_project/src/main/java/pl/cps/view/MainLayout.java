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
    //sample frequency for Continuous Signals:
    private static final double sampleFrequency = 1000.0;


    public MainLayout() {

        charts.add(new ChartComponent());
        charts.add(new ChartComponent());
        charts.add(new ChartComponent());

        try {
            //TU ZMIANA GENEROWANEGO SYGNALU
            charts.get(0).generateSignal(App.getSelectedSignals().get(0), sampleFrequency);
            charts.get(1).generateSignal(App.getSelectedSignals().get(1), sampleFrequency);
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
        if (whichSignalToShow == 0) {
            String name = App.getSellectdOptionFromMenu(App.getSignalTwoMenu());
            if (name.equals("Szum Impulsowy") || name.equals("Impuls jednostkowy")) {
                charts.get(whichSignalToShow).drawDiscreteChart(name);
            } else {
                charts.get(whichSignalToShow).drawContinuousChart(name);
            }
        } else if (whichSignalToShow == 1) {
            String name = App.getSellectdOptionFromMenu(App.getSignalOneMenu());
            if (name.equals("Szum Impulsowy") || name.equals("Impuls jednostkowy")) {
                charts.get(whichSignalToShow).drawDiscreteChart(name);
            } else {
                charts.get(whichSignalToShow).drawContinuousChart(name);
            }
        } else if (whichSignalToShow == 2) {
            charts.get(whichSignalToShow).drawContinuousChart("Sygnał wynikowy");
        }
        add(charts.get(whichSignalToShow), 0, 1);
    }

    public void initHistogram(int whichSignalToShow) {
        this.histogramComponent = new HistogramComponent();
        histogramComponent.drawHistogram(charts.get(whichSignalToShow).getData());
        add(histogramComponent, 0, 2);
    }

}