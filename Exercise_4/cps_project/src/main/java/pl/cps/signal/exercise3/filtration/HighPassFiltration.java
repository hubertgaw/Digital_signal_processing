package pl.cps.signal.exercise3.filtration;

import pl.cps.signal.exercise3.filtration.windows.Window;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class HighPassFiltration extends LowPassFiltration{
    @Override
    public List<Data> calculate(List<Data> pointsToFilter, int M, double fo, double fp, Window window) {
        List<Data> pointsAfterLowPass = new ArrayList<>();
        List<Data> pointsAfterMiddlePass = new ArrayList<>();
        pointsAfterLowPass = super.calculate(pointsToFilter,  M, fo, fp, window);
        for (int n = 0; n < pointsAfterLowPass.size(); n++) {
            pointsAfterMiddlePass.add(new Data(pointsAfterLowPass.get(n).getX(),
                    pointsAfterLowPass.get(n).getY() * Math.pow(-1,n)));//2.0 * Math.sin(Math.PI * n / 2.0)));
        }
        return pointsAfterMiddlePass;
    }
}
