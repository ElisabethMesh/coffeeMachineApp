package com.meshalkina.coffee_machine.model;

public class CheckLine {

    private Long checkLineId;
    private Check check;
    private Good good;
    private int numString;
    private int amountGoods;
    private double sumString;

    public Long getCheckLineId() {
        return checkLineId;
    }

    public void setCheckLineId(Long checkLineId) {
        this.checkLineId = checkLineId;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public int getNumString() {
        return numString;
    }

    public void setNumString(int numString) {
        this.numString = numString;
    }

    public int getAmountGoods() {
        return amountGoods;
    }

    public void setAmountGoods(int amountGoods) {
        this.amountGoods = amountGoods;
    }

    public double getSumString() {
        return sumString;
    }

    public void setSumString(double sumString) {
        this.sumString = sumString;
    }

    @Override
    public String toString() {
        return "CheckLine{" +
                "checkLineId=" + checkLineId +
                ", checkId=" + check +
                ", goodId=" + good +
                ", numString=" + numString +
                ", amountGoods=" + amountGoods +
                ", sumString=" + sumString +
                '}';
    }
}
