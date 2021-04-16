package pl.cps.signal.emiters;

import java.util.Random;

public class UniformlyDistributedNoise extends Signal {

    private Random rand;

    public UniformlyDistributedNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
        this.rand = new Random();
    }

    public UniformlyDistributedNoise() {
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        return -getAmplitude() + (getAmplitude() - (-getAmplitude())) * rand.nextDouble();
//      from formula: double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }
}
