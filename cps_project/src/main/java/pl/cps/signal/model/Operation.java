package pl.cps.signal.model;

import java.util.List;

public interface Operation {

    public List<Data> performCalculating(List<Data> pointsFromFirstSignal,
                                   List<Data> pointsFromSecondSignal);
}
