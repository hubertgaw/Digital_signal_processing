package pl.cps.signal.emiters;

public class SymmetricalSquareSignal extends SquareSignal {
    public SymmetricalSquareSignal(double amplitude, double startTime, double duration, double frequency, double kw) {
        super(amplitude, startTime, duration, frequency, kw);
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        if (shouldReturnZero(time)) {
            return (-1) * (getAmplitude() / 2);
        }
        return getAmplitude() / 2;
    }
}
