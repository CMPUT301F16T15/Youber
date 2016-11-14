package com.youber.cmput301f16t15.youber;

import android.view.View;

import com.youber.cmput301f16t15.youber.exceptions.SameRequestException;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.users.Rider;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Created by Reem on 2016-10-13.
 */
public class RequestsTest
{
    @Test (expected = RuntimeException.class)
    public void testDifferentLocations()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(90.0, 90.0);
        // we expect this line to throw since the locations are the same
        Request request = new Request(geoLocation1, geoLocation2);
    }

    @Test
    public void testStartLocation()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, geoLocation2);
        assertEquals(geoLocation1, request.getStartLocation());
    }

    @Test
    public void testEndLocation()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, geoLocation2);
        assertEquals(geoLocation2, request.getEndLocation());
    }

    @Test
    public void testUniqueRequestUUIDs()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);

        boolean test = request1.equals(request2);
        assertFalse(test);
    }

    @Test
    public void testOpenRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.opened);
    }

    @Test
    public void testCloseRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        RequestController.closeRequest(request1);
        assertTrue("Expected to fail until Project Part 5", request1.isClosed());
    }

    @Test
    public void testAcceptRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        RequestController.acceptRequest(request1);

        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.acceptedByDrivers);
    }

    @Test
    public void testDistanceBetweenLocations()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Double actualDistance = 0.9358; // using http://www.movable-type.co.uk/scripts/latlong.html
        assertEquals(actualDistance, RequestController.getDistanceOfRequest(request1));

    }

    @Test
    public void testFairFare()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Double actualFair = 9.87;
        // $2 per km _ $8 base fee
        assertEquals(actualFair, RequestController.getEstimatedFare(request1));
    }

    @Test
    public void testCompletedRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        RequestController.completeRequest(request1);
        assertTrue(request1.isComplete());
    }


    @Test
    public void testLocalRequestsToBeSent() //project part 5
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);

        Driver driver = new Driver();
        RequestController.addDriver(request1,driver);
    }


    @Test
    public void testRequestAcceptedByDrivers() // project part 5
    {
        //this should really be in the other file
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        RequestController.acceptRequest(request1);//would happen on other app

        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.acceptedByDrivers);
    }
}
