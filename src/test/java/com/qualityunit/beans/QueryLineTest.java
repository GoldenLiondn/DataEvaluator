package com.qualityunit.beans;

import com.qualityunit.App;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class QueryLineTest {


    @Test
    public void evaluateTest() {

        String[] input = {
                "7",
                "C 1.1 8.15.1 P 15.10.2012 83",
                "C 1 10.1 P 01.12.2012 65",
                "C 1.1 5.5.1 P 01.11.2012 117",
                "D 1.1 8 P 01.01.2012-01.12.2012",
                "C 3 10.2 N 02.10.2012 100",
                "D 1 * P 8.10.2012-20.11.2012",
                "D 3 10 P 01.12.2012"
        };


        ArrayList<DataLine> inputDataObjects = App.getInputData(input);
        QueryLine queryLine1 = (QueryLine) inputDataObjects.get(3);
        QueryLine queryLine2 = (QueryLine) inputDataObjects.get(5);
        QueryLine queryLine3 = (QueryLine) inputDataObjects.get(6);

        assertEquals("83", queryLine1.evaluate(inputDataObjects, 3));
        assertEquals("100", queryLine2.evaluate(inputDataObjects, 5));
        assertEquals("-", queryLine3.evaluate(inputDataObjects, 6));


    }
}