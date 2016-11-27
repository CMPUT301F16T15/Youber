package com.youber.cmput301f16t15.youber;

import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.youber.cmput301f16t15.youber.exceptions.SameRequestException;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestController;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        Request request = new Request(geoLocation1, "", geoLocation2, "");
    }

    @Test
    public void testStartLocation()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, "", geoLocation2, "");
        assertEquals(geoLocation1, request.getStartLocation());
    }

    @Test
    public void testEndLocation()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, "", geoLocation2, "");
        assertEquals(geoLocation2, request.getEndLocation());
    }

    @Test
    public void testUniqueRequestUUIDs()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");

        boolean test = request1.equals(request2);
        assertFalse(test);
    }

    @Test
    public void testOpenRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.opened);
    }

    @Test
    public void testCloseRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        RequestController.closeRequest(request1);
        assertEquals(Request.RequestStatus.completed, request1.getCurrentStatus());
    }

    @Test
    public void testAcceptRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        RequestController.acceptRequest(request1);

        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.acceptedByDrivers);
    }

    @Test
    public void testDistanceBetweenLocations()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Double expected = 10.231134;

        request1.setDistance(expected);
        assertEquals(expected, RequestController.getDistanceOfRequest(request1));
    }

    @Test
    public void testFairFare()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        request1.setDistance(10.25);

        Double actualFair = 9.92;
        // $0.48 per km _ $5 base fee
        assertEquals(actualFair, RequestController.getEstimatedFare(request1));
    }

    @Test
    public void testCompletedRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        RequestController.completeRequest(request1);
        assertEquals(Request.RequestStatus.completed, request1.getCurrentStatus());
    }

    @Test
    public void testRequestAcceptedByDrivers() // project part 5
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        RequestController.acceptRequest(request1);//would happen on other app

        assertEquals(Request.RequestStatus.acceptedByDrivers, request1.getCurrentStatus());
    }

    @Test
    public void testRequestHashCode() // project part 5
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, geoLocation2,request1.getUUID());

        assertTrue(request1.hashCode()==request2.hashCode());
        request2.setPaid();
        assertFalse(request1.hashCode()==request2.hashCode());
    }
    @Test
    public void testRequestEquals(){
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1,geoLocation2,request1.getUUID());

        assertTrue(request1.equals(request2));
        request2.setPaid();
        assertFalse(request1.equals(request2));
    }
    @Test
    public void testRequestCollectionHashCode() // project part 5
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1,geoLocation2,request1.getUUID());
        Request request4 = new Request(geoLocation1,geoLocation2,request2.getUUID());

        RequestCollection requestCollection1 = new RequestCollection();
        requestCollection1.add(request1);
        requestCollection1.add(request2);
        RequestCollection requestCollection2 = new RequestCollection();
        requestCollection2.add(request3);
        requestCollection2.add(request4);

        assertTrue(requestCollection1.hashCode()==requestCollection2.hashCode());
        request3.setPaid();
        assertFalse(requestCollection1.hashCode()==requestCollection2.hashCode());
    }
    @Test
    public void testRequestCollectionEquals() // project part 5
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1,geoLocation2,request1.getUUID());
        Request request4 = new Request(geoLocation1,geoLocation2,request2.getUUID());

        RequestCollection requestCollection1=new RequestCollection();
        requestCollection1.add(request1);
        requestCollection1.add(request2);
        RequestCollection requestCollection2=new RequestCollection();
        requestCollection2.add(request3);
        requestCollection2.add(request4);

        assertTrue(requestCollection1.equals(requestCollection2));
        request3.setPaid();
        assertFalse(requestCollection1.equals(requestCollection2));
    }

    @Test
    public void testSetDescription() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        request1.setDescription("hello how are you?");

        assertEquals("hello how are you?", request1.getDescription());
    }

    @Test
    public void testGetDriver() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, "", geoLocation2, "");
        request1.setDriverUsernameID("bemo");

        assertEquals("bemo", request1.getDriverUsernameID());
    }
}



