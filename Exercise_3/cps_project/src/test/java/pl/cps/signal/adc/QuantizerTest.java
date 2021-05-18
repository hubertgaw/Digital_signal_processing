package pl.cps.signal.adc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.cps.signal.emiters.SignalIsNotTransmittedInThisTime;
import pl.cps.signal.model.Data;

import java.util.ArrayList;
import java.util.List;

public class QuantizerTest {
    @Test
    public void findNearestLowerNeighbourInLevelsTest() {
        List<Data> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) { //trwa 10s, amplituda 0-10
            data.add(new Data(i / 10.0, i % 10.0));
        }
        Quantizer quantizer = new Quantizer();
        try {
            quantizer.createLevels(data, 10); //poziomy od 0 do 9
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        assertEquals(1, quantizer.findNearestLowerNeighbourInLevels(1.99));
        assertEquals(0, quantizer.findNearestLowerNeighbourInLevels(0.99));
        assertEquals(2, quantizer.findNearestLowerNeighbourInLevels(2));
        assertEquals(9, quantizer.findNearestLowerNeighbourInLevels(11));
    }

    @Test
    public void truncatedQuantizationTest() {
        List<Data> data = new ArrayList<>(), resultData = new ArrayList<>();
        for (int i = 0; i < 100; i++) { //trwa 10s, amplituda 0-10
            data.add(new Data(i % 10.0, i / 10.0));
            resultData.add(new Data(i % 10.0, Math.floor(i / 10.0)));
        }
        Quantizer quantizer = new Quantizer();
        try {
            quantizer.createLevels(data, 11); //poziomy od 0 do 10
        } catch (SignalIsNotTransmittedInThisTime signalIsNotTransmittedInThisTime) {
            signalIsNotTransmittedInThisTime.printStackTrace();
        }
        for (int i = 0; i < 99; i++) { //trwa 10s, amplituda 0-10
            assertEquals(quantizer.getLevels().get((int) Math.floor(i / 10)), quantizer.truncatedQuantization(data, 11).get(i).getY());
        }
        assertEquals(quantizer.getLevels().get(10), quantizer.truncatedQuantization(data, 11).get(99).getY());

    }
}