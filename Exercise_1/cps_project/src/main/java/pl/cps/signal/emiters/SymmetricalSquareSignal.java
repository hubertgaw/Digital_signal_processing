package pl.cps.signal.emiters;

public class SymmetricalSquareSignal extends SquareSignal {
    public SymmetricalSquareSignal(double amplitude, double startTime, double duration, double term, double kw) {
        super(amplitude, startTime, duration, term, kw);
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
//        System.out.println("CV-Symmetrical squareSignal"+counter);
//        counter++;
        if (shouldReturnZero(time)) {
            return (-1) * (getAmplitude());
        }
        return getAmplitude();
    }
}
