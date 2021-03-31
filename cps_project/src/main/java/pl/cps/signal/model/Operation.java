package pl.cps.signal.model;

import java.util.List;

public interface Operation {

    public void performCalculating(List<Data> pointsFromFirstSignal,
                                   List<Data> pointsFromSecondSignal);
}
