package pl.cps.signal.emiters;

public class UnitJump extends Signal {

    private double jumpTime;

    public UnitJump(double amplitude, double startTime, double duration, double jumpTime) {
        super(amplitude, startTime, duration);
        this.jumpTime = jumpTime;
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        if (time > jumpTime) {
            return getAmplitude();
        } else if (time == jumpTime) {
            return getAmplitude() / 2.0;
        } else {
            return 0;
        }
    }
}
