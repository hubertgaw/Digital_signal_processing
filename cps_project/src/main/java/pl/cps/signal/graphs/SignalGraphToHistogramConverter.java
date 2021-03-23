package pl.cps.signal.graphs;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class SignalGraphToHistogramConverter implements Converter {

    private MinMaxExtractor minMaxExtractor = new MinMaxExtractor();
    private IntervalSizeExtractor intervalSizeExtractor = new IntervalSizeExtractor();
    private PointsToIntervalAssigner pointsToIntervalAssigner = new PointsToIntervalAssigner();

    @Override
    public void convert(ObservableList<XYChart.Data<Double, Double>> data, int barsNumber) {
        double minMax [] = minMaxExtractor.extract(data);
        double difference = minMax[1] - minMax[0]; // max - min
        double internalSize = intervalSizeExtractor.extract(barsNumber, difference);
        System.out.println("wielkosc przedzialu: " + internalSize);

        pointsToIntervalAssigner.assign(minMax, internalSize, data);
    }
}
