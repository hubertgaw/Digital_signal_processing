package pl.cps.signal.emiters;

import static java.lang.Math.abs;

public class TwoHalfSinusoidalSignal extends SinusoidalSignal{

    public TwoHalfSinusoidalSignal(double amplitude, double startTime, double duration, double frequency) {
        super(amplitude, startTime, duration, frequency);
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        return abs(super.calculateValue(time));
    }
}
