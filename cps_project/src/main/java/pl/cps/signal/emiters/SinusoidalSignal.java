package pl.cps.signal.emiters;

import static java.lang.Math.PI;
import static java.lang.Math.sin;

public class SinusoidalSignal extends Signal {
    private double term;

    public SinusoidalSignal(double amplitude, double startTime, double duration, double term) {
        super(amplitude, startTime, duration);
        this.term = term;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        return getAmplitude() * sin(2 * PI * (time - getStartTime()) / term);
    }
}
