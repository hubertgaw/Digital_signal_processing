package pl.cps.view;

import javafx.scene.layout.GridPane;

/**
 * @author Thomas Darimont
 */
public class MainLayout extends GridPane {

    private final ChartComponent chartComponent;

    public MainLayout(ChartComponent chartComponent) {

        this.chartComponent = chartComponent;
        initComponent();
    }

    public void initComponent() {

        add(this.chartComponent, 0, 1);
    }
}