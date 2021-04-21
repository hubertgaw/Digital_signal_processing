package pl.cps.signal.adc;

import org.junit.jupiter.api.Test;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReconstructorsTest {
    @Test
    public void firstOrderInterpolationForPointTest() {
        List<Data> data = new ArrayList<Data>(), minusData = new ArrayList<Data>();
        data.add(new Data(0.0, 0.0));
        data.add(new Data(1.0, 1.0));
        minusData.add(new Data(0.0, 0.0));
        minusData.add(new Data(-1.0, -1.0));
        for (int i = 0; i <= 10; i++) {
            assertEquals(i / 10.0, Reconstructors.firstOrderInterpolationForPoint(data, i / 10.0), 0.01);
            assertEquals(-i / 10.0, Reconstructors.firstOrderInterpolationForPoint(minusData, -i / 10.0), 0.01);
        }
    }

    @Test
    public void firstOrderInterpolationTest() {
        List<Data> reconstructedData = Reconstructors.firstOrderInterpolation(
                Arrays.asList(new Data(0.0, 0.0), new Data(1.0, 1.0)), 100);
        for (int i = 0; i < 100; i++) {
            assertEquals(i / 100.0, reconstructedData.get(i).getX(), 0.0001);
            assertEquals(i / 100.0, reconstructedData.get(i).getY(), 0.0001);
        }
    }

    @Test
    public void zeroOrderInterpolationForPointTest() {
        List<Data> data = new ArrayList<Data>();
        for (int i = 0; i < 11; i++) {
            data.add(new Data((double) i, i % 3.0));
        }
        for (int i = 0; i < 110; i++) {
            if (i <= 100) {
                assertEquals(Math.floor((i / 10.0) % 3.0), Reconstructors.zeroOrderInterpolationForPoint(data, i / 10.0));
            }
            if (i > 100) {
                assertEquals(0, Reconstructors.zeroOrderInterpolationForPoint(data, i / 10.0));
            }
        }

    }

    @Test
    public void zeroOrderInterpolation() {
        List<Data> data = new ArrayList<Data>(), resultData;
        for (int i = 0; i < 11; i++) {
            data.add(new Data((double) i, i % 3.0));
        }
        resultData = Reconstructors.zeroOrderInterpolation(data, 100);
        resultData.stream().forEach(i ->
                assertEquals(Math.floor(i.getX()) % 3.0, i.getY()));
    }

}