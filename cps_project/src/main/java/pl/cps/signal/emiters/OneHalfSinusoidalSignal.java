package pl.cps.signal.emiters;

public class OneHalfSinusoidalSignal extends SinusoidalSignal {
    public OneHalfSinusoidalSignal(double amplitude, double startTime, double duration, double frequency) {
        super(amplitude, startTime, duration, frequency);
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        double ret = super.calculateValue(time);
        if (ret < 0) {
            return 0.0;
        }
        return ret;
    }
}
