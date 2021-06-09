package pl.cps.signal.emiters;

import java.util.Random;

public class GaussianNoise extends Signal {
    private Random rand;

    public GaussianNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
        this.rand = new Random();
    }

    @Override
    public double calculateValue(double time) {
        return getAmplitude() * (((rand.nextGaussian() % 1.0)));
    }
}
