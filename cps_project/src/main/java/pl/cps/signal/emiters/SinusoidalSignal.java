package pl.cps.signal.emiters;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class SinusoidalSignal extends Signal {
    private double frequency;

    public SinusoidalSignal(double amplitude, double startTime, double duration, double frequency) {
        super(amplitude, startTime, duration);
        this.frequency = frequency;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        return getAmplitude() * sin(2 * PI * (time - getStartTime()) * frequency);
    }
}
