package pl.cps.signal.exercise3.filtration;

import pl.cps.signal.exercise3.filtration.windows.Window;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class LowPassFiltration {

    public static List<Data> calculate(List<Data> pointsToFilter, int M, double fo, double fp, Window window) {
        double K = fp/fo;
        int firstCondition = (M - 1) / 2;
        List<Data> pointsAfterFiltration = new ArrayList<>();
        for (int n = 0; n < pointsToFilter.size() - 1; n++) {
            double w = window.value(n, M);
            double h;
            if (n == firstCondition) {
                h = 2 / K;
            } else {
                h = (Math.sin((2.0 * Math.PI * (n - (M - 1.0) / 2.0)) / K)) / (Math.PI * (n - (M - 1.0) / 2.0));
            }
            pointsAfterFiltration.add(new Data(pointsToFilter.get(n).getX(), h * w));
        }

        return pointsAfterFiltration;

    }
}