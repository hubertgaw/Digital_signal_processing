package pl.cps.signal.emiters;

public class SquareSignal extends Signal {

    private double term;
    private double kw;
    protected int counter = 0;

    public SquareSignal(double amplitude, double startTime, double duration, double term, double kw) {
        super(amplitude, startTime, duration);
        this.term = term;
        this.kw = kw;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        counter++;
        if (shouldReturnZero(time)) {
            return 0.0;
        }
        return getAmplitude();
    }

    protected boolean shouldReturnZero(double time) {
//        int k = 0;
//        while (((term) * k) < (getStartTime() + getDuration())) {
//            if ((time > ((kw * term) - ((kw * term) + (k * term)))) &&
//                    (time < ((term) + (k * term) + getStartTime()))) {
//                return true;
//            }
//            k++;
//        }
//        return false;
        if (((time - getStartTime()) / term) - Math.floor((time - getStartTime()) / term) >= kw) {
            return true;
        } else {
            return false;
        }

    }


}
