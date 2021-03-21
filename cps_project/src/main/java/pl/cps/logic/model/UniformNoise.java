package pl.cps.logic.model;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Random;

public class UniformNoise {
    private double amplitude;
    private double startTime;
    private double duration;
    Random rand = new Random();

    public UniformNoise(double amplitude, double startTime, double duration) {
        this.amplitude = amplitude;
        this.startTime = startTime;
        this.duration = duration;
    }

    public double value (double x) {
        return -amplitude + (amplitude - (-amplitude)) * rand.nextDouble();
    }

    public void generate (ObservableList<XYChart.Data<Number, Number>> data) {
        for (double x = startTime; x < startTime + duration; x += 0.05) {
            data.add(new XYChart.Data<Number, Number>(x, value(x)));
        }

    }
}
