package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.signal.model.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
    public List<Data> generateChart(ObservableList<XYChart.Data<Double, Double>> data, double sampleFrequency) throws SignalIsNotTransmittedInThisTime {
        for (double x = getStartTime(); x < getStartTime() + getDuration(); x += 1/getFrequency()) {
            double x_3decimalPoints = BigDecimal.valueOf(x)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
            double y = calculateValue(x_3decimalPoints);
            data.add(new XYChart.Data<>(x_3decimalPoints, y));
            getPoints().add(new Data(x_3decimalPoints, y));
        }
        return getPoints();
    }
}
