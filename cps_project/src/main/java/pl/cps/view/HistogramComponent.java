package pl.cps.view;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.HBox;

public class HistogramComponent extends HBox {
    private CategoryAxis xAxis;
    private NumberAxis yAxis;

    public HistogramComponent() {
        this.xAxis = new CategoryAxis();
        this.yAxis = new NumberAxis();
    }

    public void drawHistogram() {
        BarChart<String, Number> histogram =
                new BarChart<String, Number>(xAxis, yAxis);


    }


}
