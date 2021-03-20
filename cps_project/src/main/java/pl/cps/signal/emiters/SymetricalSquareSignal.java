package pl.cps.signal.emiters;

public class SymetricalSquareSignal extends SquareSignal {
    public SymetricalSquareSignal(double amplitude, double startTime, double duration, double frequency, int kw) {
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
