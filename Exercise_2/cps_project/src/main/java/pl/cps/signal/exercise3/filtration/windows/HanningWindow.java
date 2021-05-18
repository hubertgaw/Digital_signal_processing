package pl.cps.signal.exercise3.filtration.windows;

public class HanningWindow implements Window {
    @Override
    public double value(int n, int M) {
        return 0.5 - 0.5 * Math.cos((2.0 * Math.PI * n) / M);
    }
}
