package pl.cps.signal.emiters;

public class SquareSignal extends Signal {

    private double frequency;
    private int kw;

    public SquareSignal(double amplitude, double startTime, double duration, double frequency, int kw) {
        super(amplitude, startTime, duration);
        this.frequency = frequency;
        this.kw = kw;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        if (shouldReturnZero(time)) {
            return 0.0;
        }
        return getAmplitude();
    }

    protected boolean shouldReturnZero(double time) {
        int k = 0;
        while (((1 / frequency) * k) < (getStartTime() + getDuration())) {
            if ((time > ((kw / frequency) - ((kw / frequency) + (k / frequency)))) &&
                    (time < ((1 / frequency) + (k / frequency) + getStartTime()))) {
                return true;
            }
            k++;
        }
        return false;
    }
}
