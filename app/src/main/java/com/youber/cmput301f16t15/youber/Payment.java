package com.youber.cmput301f16t15.youber;

/**
 * Created by Calvi on 2016-11-07.
 */

public class Payment {

    private double actualCost;
    private boolean paymentAbility; //true if enabled otherwise false

    public Payment(double actualCost) {
        this.actualCost = actualCost;
    }

    public double fairFare() {
        return 0;
    }

    public void sendPayment() {
    }

    public void acceptPayment() {

    }

    public void setActualCostWithFairFare() {
        this.actualCost = fairFare();
    }

}
