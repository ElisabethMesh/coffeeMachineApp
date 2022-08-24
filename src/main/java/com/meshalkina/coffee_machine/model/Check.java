package com.meshalkina.coffee_machine.model;

import java.time.LocalDateTime;

public class Check {

    private Long checkId;
    private LocalDateTime dateTime;
    private double sum;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "Check{" +
                "checkId=" + checkId +
                ", dateTime=" + dateTime +
                ", sum=" + sum +
                '}';
    }
}
