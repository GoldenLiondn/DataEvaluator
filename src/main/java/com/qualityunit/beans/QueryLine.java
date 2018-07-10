package com.qualityunit.beans;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Maksim Ovcharenko
 * 09.07.2018
 */
public class QueryLine extends DataLine {

    private Date dateFrom;
    private Date dateTo;

    public QueryLine() {
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * Method returns average waiting time of all records matching specific criterias of query.
     * @param inputData list of all objects
     * @param index index of query. Only records berore it will be included.
     *                (Only matching lines defined before query line are counted.)
     * @return String interpritation of average waiting time or "-" if there no matching records
     */
    public String evaluate(ArrayList<DataLine> inputData, int index) {
        int totalTime = 0, countOfMatchingLines = 0;
        for (int i = 0; i < index; i++) {
            if (inputData.get(i) instanceof WaitingTimeLine) {
                WaitingTimeLine wtl = (WaitingTimeLine) inputData.get(i);
                //checking if Service_id() matching to query
                if (this.getService_id() == 0||(this.getService_id()==wtl.getService_id()&&this.getVariation_id()==0)
                        ||(this.getService_id()==wtl.getService_id()&&this.getVariation_id()==wtl.getVariation_id())) {
                    //checking if Question_type_id() matching to query
                    if (this.getQuestion_type_id() == 0||this.getQuestion_type_id()==wtl.getQuestion_type_id()) {
                        if (this.isFirstAnswer() == wtl.isFirstAnswer()) {
                            //checking dates matching
                            if (this.getDateTo() == null) {
                                if (!wtl.getDate().after(this.getDateFrom())
                                        && !wtl.getDate().before(this.getDateFrom())){
                                    totalTime += wtl.getWaitingTime();
                                    countOfMatchingLines++;
                                }

                            } else if (wtl.getDate().after(this.getDateFrom())
                                    && wtl.getDate().before(this.getDateTo())) {
                                totalTime += wtl.getWaitingTime();
                                countOfMatchingLines++;
                            }
                        } else continue;
                    }
                }
            }
        }
        if (countOfMatchingLines == 0) {
            return "-";
        }
        return String.valueOf(totalTime / countOfMatchingLines);
    }
}
