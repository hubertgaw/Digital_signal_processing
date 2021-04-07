package pl.cps.signal.emiters;

public class TriangularSignal extends Signal {

    private double term;
    private double kw;

    public TriangularSignal(double amplitude, double startTime, double duration, double term, double kw) {
        super(amplitude, startTime, duration);
        this.term = term;
        this.kw = kw;

    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        double triangularPosition = ((time - getStartTime()) / term) - Math.floor((time - getStartTime()) / term);
        if (triangularPosition < kw) {
            return triangularPosition / kw * getAmplitude();
        } else {
            return (1 - (triangularPosition - kw) / (1 - kw)) * getAmplitude();
        }
    }
}
