package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

import java.util.Random;

public class ImpulseNoise extends Signal {
    private double possibility;
    private double frequency;
    private Random rand;

    public ImpulseNoise(double amplitude, double startTime, double duration, double frequency,
                        double possibility) {
        super(amplitude, startTime, duration);
        this.possibility = possibility;
        this.frequency = frequency;
        this.rand = new Random();
    }

    public double getFrequency() {
        return frequency;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        int random = rand.nextInt(100);
        if (random < possibility) {
            return this.getAmplitude();
        }
        return 0.0;
    }

    @Override
    public void generateChart(ObservableList<XYChart.Data<Double, Double>> data) throws SignalIsNotTransmittedInThisTime {
        for (double x = getStartTime(); x < getStartTime() + getDuration(); x += 1/getFrequency()) {
            data.add(new XYChart.Data<>(x, calculateValue(x)));
        }
    }
}
