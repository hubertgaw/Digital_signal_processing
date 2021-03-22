package pl.cps.view;

import javafx.scene.layout.GridPane;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;


public class MainLayout extends GridPane {

    private final ChartComponent chartComponent;

    public MainLayout(ChartComponent chartComponent) {

        this.chartComponent = chartComponent;
        try {
            chartComponent.generateSinusoidalSignal();
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        chartComponent.drawChart();
//        chartComponent.getData().get(1).getYValue();
        initComponent();
    }

    public void initComponent() {

        add(this.chartComponent, 0, 1);
    }
}