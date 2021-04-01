package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class OperationSignal {
    private List<Data> points = new ArrayList<>();

    public void generateChart(ObservableList<XYChart.Data<Double, Double>> data,
                              List<Data> points) {
        for (Data point : points) {
            data.add(new XYChart.Data<>(point.getX(), point.getY()));
        }

    }
}
