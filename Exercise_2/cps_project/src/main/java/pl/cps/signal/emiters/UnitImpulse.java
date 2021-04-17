package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.signal.model.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public void generateChart(ObservableList<XYChart.Data<Double, Double>> data, double sampleFrequency) throws SignalIsNotTransmittedInThisTime {
        int sampleCounter = 0;
        for (double x = getStartTime(); x < getStartTime() + getDuration(); x += 1/getFrequency()) {
            double x_3decimalPoints = BigDecimal.valueOf(x)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
            double y = calculateValue(sampleCounter);
            data.add(new XYChart.Data<>(x_3decimalPoints, y));
            getPoints().add(new Data(x_3decimalPoints, y));
            sampleCounter ++;
        }
    }
}
