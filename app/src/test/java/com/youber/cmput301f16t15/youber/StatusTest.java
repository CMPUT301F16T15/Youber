package com.youber.cmput301f16t15.youber;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Reem on 2016-10-13.
 */


public class StatusTest
{
    @Test
    public void testRiderStatus()
    {
        Rider user = new Rider();
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        user = request1.addRider(user);
        user.getRequest(request1.getUUID()).accept();
        assertEquals("is Accepted", user.getStatus(request1.getUUID()));
    }

    @Test
    public void testDriverStatus()
    {
        Driver user = new Driver();
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        user = request1.addRider(user);
        user.getRequest(request1.getUUID()).driverAccept();
        assertEquals("is Accepted", user.getStatus(request1.getUUID()));
    }

}
