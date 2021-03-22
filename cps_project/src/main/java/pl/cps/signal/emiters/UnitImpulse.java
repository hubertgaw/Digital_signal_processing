package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class UnitImpulse extends Signal {

    private double frequency;

    public int jumpNumber;

    public UnitImpulse(double amplitude, double startTime, double duration,
                       double frequency, int jumpNumber) {
        super(amplitude, startTime, duration);
        this.frequency = frequency;
        this.jumpNumber = jumpNumber;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getJumpNumber() {
        return jumpNumber;
    }

    @Override
    public double calculateValue(double sampleNumber) throws SignalIsNotTransmittedInThisTime {
        if (sampleNumber == getJumpNumber()) {
            return getAmplitude();
        } else {
            return 0.0;
        }
    }

    @Override
    public void generateChart(ObservableList<XYChart.Data<Number, Number>> data) throws SignalIsNotTransmittedInThisTime {
        int sampleCounter = 0;
        for (double x = getStartTime(); x < getStartTime() + getDuration(); x += 1/getFrequency()) {
            data.add(new XYChart.Data<Number, Number>(x, calculateValue(sampleCounter)));
            sampleCounter ++;
        }
    }
}
