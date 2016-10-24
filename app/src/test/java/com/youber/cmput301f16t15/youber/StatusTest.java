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
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        rider1.getRequest(request1.getUUID()).accept();
        assertEquals("is Accepted", rider1.getStatus(request1.getUUID()));
    }

    @Test
    public void testDriverStatus()
    {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);

        driver1.getRequest(request1.getUUID()).driverAccept();
        assertEquals("is Accepted", driver1.getStatus(request1.getUUID()));
    }

}
