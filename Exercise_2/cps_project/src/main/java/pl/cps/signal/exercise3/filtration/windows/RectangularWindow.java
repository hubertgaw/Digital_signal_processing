package pl.cps.signal.exercise3.filtration.windows;

public class RectangularWindow implements Window {

    @Override
    public double value(int n, int M) {
        return 1;
    }
}
