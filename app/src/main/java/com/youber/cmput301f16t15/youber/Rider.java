package com.youber.cmput301f16t15.youber;

import java.util.UUID;

/**
 * Created by Reem on 2016-10-13.
 */
public class Rider{
    public Request getRequest(UUID uuid) {
        return new Request();
    }

    public RequestCollection getOpenRequests() {
        return null;
    }

    public RequestCollection getClosedRequests() {
        return null;
    }

    public RequestCollection getRequests() {
        return null;
    }

    public String getStatus(UUID uuid) {
        return null;
    }

    public boolean call(Driver driver) {
        return false;
    }

    public boolean email(Driver driver) {
        return false;
    }

    public void makePayment(UUID uuid) {

    }
}
