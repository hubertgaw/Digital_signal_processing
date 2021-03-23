package pl.cps.view;

import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;

import java.util.Comparator;


public class MainLayout extends GridPane {

    private ChartComponent chartComponent;
    private HistogramComponent histogramComponent;

//    {
//        try {
//            chartComponent = new ChartComponent();
//        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
//            signalIsNotTransmittedInThisTime.printStackTrace();
//        }
//    }

//    private final HistogramComponent histogramComponent;

    public MainLayout() {
        try {
            this.chartComponent = new ChartComponent();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
//        this.chartComponent = chartComponent;
        try {
            chartComponent.generateSquareSignal();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        this.histogramComponent = new HistogramComponent();
        chartComponent.drawChart();
        histogramComponent.drawHistogram(chartComponent.getData());
//        Double max = chartComponent.getData().stream()
//                .max(Comparator.comparing(XYChart.Data::getYValue))
//                .get()
//                .getYValue();
//        System.out.println(max);
//        Double min = chartComponent.getData().stream()
//                .min(Comparator.comparing(XYChart.Data::getYValue))
//                .get()
//                .getYValue();
//        System.out.println(min);
        initComponent();
    }

    public void initComponent() {
        add(chartComponent, 0, 1);
    }
}