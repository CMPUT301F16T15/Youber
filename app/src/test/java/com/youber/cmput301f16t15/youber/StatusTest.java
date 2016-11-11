package com.youber.cmput301f16t15.youber;

import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.controllers.RequestController;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.GeoLocation;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.Driver;
import com.youber.cmput301f16t15.youber.cmput301f16t15.youber.user.Rider;

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

        //driver1.getRequest(request1.getUUID()).driverAccept();
        RequestController.confirmRequest(request1,driver1);
        assertEquals("is Accepted", driver1.getStatus(request1.getUUID()));
    }

}
