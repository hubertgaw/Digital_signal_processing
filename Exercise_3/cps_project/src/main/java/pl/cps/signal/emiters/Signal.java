package pl.cps.signal.emiters;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import pl.cps.signal.model.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public abstract class Signal {
    private double amplitude, startTime, duration;
    private List<Data> points = new ArrayList<>();

    public Signal(double amplitude, double startTime, double duration) {
        this.amplitude = amplitude;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Signal(double amplitude) {
        this.amplitude = amplitude;
    }

    public Signal() {
    }


    public List<Data> getPoints() {
        return points;
    }

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public abstract double calculateValue(double time) throws SignalIsNotTransmittedInThisTime;

    protected void checkTimePeriod(double time) throws SignalIsNotTransmittedInThisTime {
        if ((time < getStartTime()) || (time > getStartTime() + getDuration())) {
            throw new SignalIsNotTransmittedInThisTime();
        }
    }

    public List<Data> generateChart(ObservableList<XYChart.Data<Double, Double>> data, double sampleFrequency) throws SignalIsNotTransmittedInThisTime {
        points.clear();
        double jumpRange = 1.0/sampleFrequency;
        double jumpRange_3decimalPoints = BigDecimal.valueOf(jumpRange)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        for (double x = startTime; x < startTime + duration; x += jumpRange_3decimalPoints) {
            double x_2decimalPoints = BigDecimal.valueOf(x)
                    .setScale(3, RoundingMode.HALF_UP)
                    .doubleValue();
            double y = calculateValue(x_2decimalPoints);
            data.add(new XYChart.Data<>(x_2decimalPoints, y));
            points.add(new Data(x_2decimalPoints, y));
        }
        return points;
    }

    public double getAvarageValue() {
        double count = 0, sum = 0;
        for (Data point : points) {
            sum += point.getY();
            count++;
        }
        return sum / count;
    }

    public double getAbsAvarageValue() {
        double count = 0, sum = 0;
        for (Data point : points) {
            sum += abs(point.getY());
            count++;
        }
        return sum / count;
    }

    public double getAvaragePower() {
        double count = 0, sum = 0;
        for (Data point : points) {
            sum += point.getY() * point.getY();
            count++;
        }
        return sum / count;
    }

    public double getVariation(){
        double count = 0, sum = 0, avg = this.getAbsAvarageValue();
        for (Data point : points) {
            sum += abs(point.getY()-avg)*abs(point.getY()-avg);
            count++;
        }
        return sum / count;
    }

    public double getEffectiveValue(){
        return sqrt(this.getAvaragePower());
    }
    public double getAbsAvarageValue(Signal secondSignal) {
        double count = 0, sum = 0;
        for (Data point : points) {
            sum += abs(point.getY());
            count++;
        }
        for (Data point : secondSignal.points) {
            sum += abs(point.getY());
            count++;
        }

        return sum / count;
    }

    public double getAvaragePower(Signal secondSignal) {
        double count = 0, sum = 0;
        for (Data point : points) {
            sum += point.getY() * point.getY();
            count++;
        }
        for (Data point : secondSignal.points) {
            sum +=  point.getY() * point.getY();
            count++;
        }
        return sum / count;
    }

    public double getVariation(Signal secondSignal){
        double count = 0, sum = 0, avg = this.getAbsAvarageValue();
        for (Data point : points) {
            sum += abs(point.getY()-avg)*abs(point.getY()-avg);
            count++;
        }
        for (Data point : secondSignal.points) {
            sum +=  abs(point.getY()-avg)*abs(point.getY()-avg);
            count++;
        }
        return sum / count;
    }

    public double getEffectiveValue(Signal secondSignal){
        return sqrt(this.getAvaragePower(secondSignal));
    }


    public static double getAvarageValue(List<Data> datas) {
        double count = 0, sum = 0;
        for (Data point : datas) {
            sum += point.getY();
            count++;
        }
        return sum / count;
    }

    public static double getAbsAvarageValue(List<Data> datas) {
        double count = 0, sum = 0;
        for (Data point : datas) {
            sum += abs(point.getY());
            count++;
        }
        return sum / count;
    }

    public static double getAvaragePower(List<Data> datas) {
        double count = 0, sum = 0;
        for (Data point : datas) {
            sum += point.getY() * point.getY();
            count++;
        }
        return sum / count;
    }

    public static double getVariation(List<Data> datas){
        double count = 0, sum = 0, avg = getAbsAvarageValue(datas);
        for (Data point : datas) {
            sum += abs(point.getY()-avg)*abs(point.getY()-avg);
            count++;
        }
        return sum / count;
    }

    public static double getEffectiveValue(List<Data> datas){
        return sqrt(getAvaragePower(datas));
    }


}
