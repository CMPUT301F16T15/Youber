package com.youber.cmput301f16t15.youber;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import static org.junit.Assert.*;
public class RiderTest
{
    @Test
    public void testGetPayment()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        rider1.makePayment(request1.getUUID());
        // actual cost will be 9.87 ($2 per km and $8 base fee)
        Double actualCost = 9.87;
        assertEquals(actualCost, rider1.getRequest(request1.getUUID()).getCost());

    }


    @Test (expected = NotaRiderException.class)
    public void testGetRiderLocalRequests()
    {
        RequestCollection savedLocal = Helper.getLocalRequests();
        Rider currentUser = Helper.getCurrentRider();
        RequestCollection driverRequests = savedLocal.getRequestsForRiders(currentUser);
    }

    // RequestController stores request in file when addRequest is called.
    // US 08.02.01
    public void testGetRequestsMadeOffline()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        assertTrue(FileManager.loadFromfile());

    }


    // US 08.03.01
    public void testGetSentRequestsFileOffile()
    {
        assertTrue(FileManager.loadFromfile());
    }




}
