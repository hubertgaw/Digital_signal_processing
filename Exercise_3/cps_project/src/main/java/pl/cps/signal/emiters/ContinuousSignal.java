package pl.cps.signal.emiters;

public abstract class ContinuousSignal extends Signal {

    @Override
    public abstract double calculateValue(double time) throws SignalIsNotTransmittedInThisTime;



}
