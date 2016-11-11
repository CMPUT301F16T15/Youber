package com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests;

/**
 * Created by Calvi on 2016-11-07.
 */
public class Payment {

    private double actualCost;
    private boolean paymentAbility = false; //true if enabled otherwise false

    /**
     * Instantiates a new Payment.
     *
     * @param actualCost the actual cost
     */
    public Payment(double actualCost) {
        this.actualCost = actualCost;
    }

    /**
     * Fair fare double.
     *
     * @return the double
     */
    public double fairFare() {
        return 0;
    }

    /**
     * Send payment.
     */
    public void sendPayment() {
    }

    /**
     * Accept payment.
     */
    public void acceptPayment() {

    }

    /**
     * Enable payment option.
     */
    public void enablePaymentOption() {
        this.paymentAbility = true;
    }

    /**
     * Sets actual cost with fair fare.
     */
    public void setActualCostWithFairFare() {
        this.actualCost = fairFare();
    }

    /**
     * Gets actual cost.
     *
     * @return the actual cost
     */
    public double getActualCost() {
        return this.actualCost;
    }

}
