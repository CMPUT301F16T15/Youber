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
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 * @see com.youber.cmput301f16t15.youber.requests.Request
 * @see GeoLocation
 */
public class Payment {

    private double actualCost;


    /**
     * Instantiates a new Payment.
     *
     * @param actualCost the actual cost
     */
    public Payment(double actualCost) {
        this.actualCost = actualCost;
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
