package pl.cps.signal.emiters;

import java.util.Random;

public class ImpulseNoise extends Signal{
    private double posibility;
    private Random rand;

    public ImpulseNoise(double amplitude, double startTime, double duration, double posibility) {
        super(amplitude, startTime, duration);
        this.posibility = posibility;
        this.rand = new Random();
    }

    @Override
    public double calculateValue(double time) throws SignalIsNotTransmittedInThisTime {
        checkTimePeriod(time);
        int random = rand.nextInt(100);
        if(random < posibility){
            return this.getAmplitude();
        }
        return 0.0;
    }
}
