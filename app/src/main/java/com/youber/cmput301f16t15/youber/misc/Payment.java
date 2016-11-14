package com.youber.cmput301f16t15.youber.misc;

/**
 * Created by Calvin on 2016-11-07.
 *
 * <p>
 *     This class handles the request payment from the rider to the driver.
 *     Deals with both actual payment and a fair fare based on the request's distance.
 *     Will be updated later on if necessary
 * </p>
 *
 * @see com.youber.cmput301f16t15.youber.requests.Request
 * @see GeoLocation
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
     * @return the fair Fare
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
