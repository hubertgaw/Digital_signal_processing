package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public abstract class Signal {
    private double amplitude, startTime, duration;

    public Signal(double amplitude, double startTime, double duration) {
        this.amplitude = amplitude;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Signal(double amplitude) {
        this.amplitude = amplitude;
    }

    public Signal() {
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public abstract double calculateValue(double time) throws SignalIsNotTransmittedInThisTime;

    protected void checkTimePeriod(double time) throws SignalIsNotTransmittedInThisTime {
        if ((time < getStartTime()) || (time > getStartTime() + getDuration())) {
            throw new SignalIsNotTransmittedInThisTime();
        }
    }

    public void generateChart (ObservableList<XYChart.Data<Number, Number>> data) throws SignalIsNotTransmittedInThisTime {
        for (double x = startTime; x < startTime + duration; x += 0.01) {
            data.add(new XYChart.Data<Number, Number>(x, calculateValue(x)));
        }

    }

}
