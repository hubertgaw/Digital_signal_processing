package pl.cps.signal.model;

import static java.lang.Math.abs;

public class MathematicalOperation {
    protected static boolean isEqualWithAccuracy(double d1, double d2, double accuracy) {
        if (abs(d1 - d2) < accuracy) {
            return true;
        }
        return false;
    }
}
