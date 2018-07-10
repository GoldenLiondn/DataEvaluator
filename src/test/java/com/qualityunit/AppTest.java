package com.qualityunit;

import com.qualityunit.beans.DataLine;
import com.qualityunit.beans.QueryLine;
import com.qualityunit.beans.WaitingTimeLine;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AppTest 
{

    @Test
    public void getInputData() {
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
        WaitingTimeLine queryLine3 = (WaitingTimeLine) inputDataObjects.get(2);

        assertEquals(1, queryLine1.getService_id());
        assertEquals(1, queryLine1.getVariation_id());
        assertEquals(8, queryLine1.getQuestion_type_id());
        assertEquals(true, queryLine1.isFirstAnswer());
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date dateFrom = ft.parse("01.01.2012");
            Date dateTo = ft.parse("01.12.2012");
            assertEquals(dateFrom, queryLine1.getDateFrom());
            assertEquals(dateTo, queryLine1.getDateTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
