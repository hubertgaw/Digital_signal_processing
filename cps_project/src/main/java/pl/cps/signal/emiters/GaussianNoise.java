package pl.cps.signal.emiters;

import java.util.Random;

public class GaussianNoise extends Signal{
    private double amplitude;
    private Random rand;

    public GaussianNoise(double amplitude) {
        this.amplitude = amplitude;
        this.rand = new Random();
    }

    @Override
    public double calculateValue(double time){
        //rand.nextGaussian zwraca wartosc od 0 do 1
        return amplitude * (rand.nextGaussian() * 2.0 - 1);
    }
}
