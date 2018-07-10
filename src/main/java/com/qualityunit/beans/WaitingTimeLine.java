package com.qualityunit.beans;

import java.util.Date;


/**
 * Created by Maksim Ovcharenko
 * 09.07.2018
 */

public class WaitingTimeLine extends DataLine {

    private Date date;
    private int waitingTime;

    public WaitingTimeLine() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}
