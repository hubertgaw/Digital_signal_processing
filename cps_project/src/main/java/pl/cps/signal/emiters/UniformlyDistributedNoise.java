package pl.cps.signal.emiters;

import java.util.Random;

public class UniformlyDistributedNoise extends Signal {

    private final Random rand = new Random();

    public UniformlyDistributedNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
    }

    public UniformlyDistributedNoise() {
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        return (rand.nextDouble() % (getAmplitude() * 2)) - getAmplitude();
    }
}
