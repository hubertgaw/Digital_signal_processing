package pl.cps.signal.emiters;

import java.util.Random;

public class UniformlyDistributedNoise extends Signal {

    private Random rand;

    public UniformlyDistributedNoise(double amplitude, double startTime, double duration, Random rand) {
        super(amplitude, startTime, duration);
        this.rand = rand;
    }

    public UniformlyDistributedNoise() {
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        return (rand.nextDouble() % (getAmplitude() * 2)) - getAmplitude();
    }
}
