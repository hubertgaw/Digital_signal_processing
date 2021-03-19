package pl.cps.view;

import javafx.scene.layout.GridPane;

/**
 * @author Thomas Darimont
 */
public class MainLayout extends GridPane {

    private final SinChartComponent sinChartComponent;

    public MainLayout(SinChartComponent sinChartComponent) {

        this.sinChartComponent = sinChartComponent;
        initComponent();
    }

    public void initComponent() {

        add(this.sinChartComponent, 0, 1);
    }
}