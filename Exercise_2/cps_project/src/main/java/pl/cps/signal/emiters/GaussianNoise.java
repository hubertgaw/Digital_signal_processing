package pl.cps.signal.emiters;

import java.util.Random;

public class GaussianNoise extends Signal{
    private Random rand;

    public GaussianNoise(double amplitude, double startTime, double duration) {
        super(amplitude, startTime, duration);
        this.rand = new Random();
    }

    @Override
    public double calculateValue(double time){
        //rand.nextGaussian zwraca wartosc od 0 do 1
        return getAmplitude() * (rand.nextGaussian() * 2.0 - 1.0);
    }
}
