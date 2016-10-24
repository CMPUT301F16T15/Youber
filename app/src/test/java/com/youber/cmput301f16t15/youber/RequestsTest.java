package com.youber.cmput301f16t15.youber;

import android.view.View;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import static org.junit.Assert.*;
public class RequestsTest
{
    @Test (expected = InvalidRequestException.class)
    public void testDifferentLocations()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(90.0, 90.0);

        Request request = new Request(geoLocation1, geoLocation2);
    }

    @Test
    public void testStartLocation()
    {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, geoLocation2);
        Rider rider = new Rider();
        RequestController.addRequest(request,rider);
        rider = request.addRider(rider);

        assertEquals(geoLocation1, rider.getRequest(request.getUUID()).getStartLocation());

    }

    @Test
    public void testEndLocation()
    {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request = new Request(geoLocation1, geoLocation2);
        Rider rider = new Rider();
        RequestController.addRequest(request,rider);

        assertEquals(geoLocation2, rider.getRequest(request.getUUID()).getEndLocation());

    }

    @Test
    public void testGetDifferentRequestsDifferentRiders() {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();
        RequestController.addRequest(request1,rider1);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Rider rider2 = new Rider();
        RequestController.addRequest(request2,rider2);

        boolean test = rider1.getRequest(request1.getUUID()).equals(rider2.getRequest(request2.getUUID()));
        assertFalse(test);

    }


    @Test
    public void testGetRequest()
    {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);

        boolean test = request1.equals(request2);
        assertFalse(test);

    }

    @Test (expected = SameRequestException.class)
    public void testSameRequestDifferentRiders()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);


        Rider rider1 = new Rider();

        RequestController.addRequest(request1,rider1);

        Rider rider2 = new Rider();

        RequestController.addRequest(request1,rider2);
        rider2 = request1.addRider(rider2);
    }

    @Test
    public void testMultipleRequestSameRider()
    {

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();

        RequestController.addRequest(request1,rider1);
        RequestController.addRequest(request2,rider1);

        boolean test = rider1.getRequest(request1.getUUID()).equals(rider1.getRequest(request2.getUUID()));
        assertFalse(test);

    }

    @Test
    public void testGetOpenRequests()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();

        RequestController.addRequest(request1,rider1);
        RequestController.addRequest(request2,rider1);



        assertEquals(2, rider1.getOpenRequests().size());

    }

    @Test
    public void testCloseRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        request1.close();
        assertTrue(request1.isClosed());
    }

    @Test
    public void testClosedRequests()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();
        rider1 = request1.addRider(rider1);
        rider1 = request2.addRider(rider1);

        rider1.getRequest(request1.getUUID()).close();

        assertEquals(1, rider1.getClosedRequests().size());
    }


    @Test
    public void testAcceptRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        request1.accept();
        assertTrue(request1.isAccepted());
    }


    @Test
    public void testNotifyAcceptedRequest()
    {
        View view = Helper.getView();
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        rider1 = request1.addRider(rider1);

        rider1.getRequest(request1.getUUID()).accept();
        View view2 = Helper.getView();
        assertFalse(view.equals(view2));
    }

    @Test
    public void testCancelRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        rider1 = request1.addRider(rider1);
        rider1.getRequest(request1.getUUID()).cancel();
        assertEquals(0, rider1.getRequests().size());

    }

    @Test
    public void testDistanceBetweenLocations()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Double actualDistance = 0.933;
        // distance = 0.933
        assertEquals(actualDistance, request1.getDistance());

    }

    @Test
    public void testFairFare()
    {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Double actualFair = 9.87;
        // $2 per km _ $8 base fee
        assertEquals(actualFair,request1.getFare());
    }

    @Test
    public void testCompletedRequest()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        rider1 = request1.addRider(rider1);
        rider1.getRequest(request1.getUUID()).complete();
        assertTrue(rider1.getRequest(request1.getUUID()).isComplete());
    }


    @Test
    public void testCompletedRequests()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Rider rider1 = new Rider();
        rider1 = request1.addRider(rider1);
        rider1 = request2.addRider(rider1);
        rider1.getRequest(request1.getUUID()).complete();
        assertEquals(1, rider1.getClosedRequests().size());

    }

    @Test
    public void testRequestAddAcceptance()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        Driver driver1 = new Driver();
        Driver driver2 = new Driver();

        driver2 = request1.addDriver(driver2);
        driver1 = request1.addDriver(driver1);

        assertTrue(request1.accept(driver1));

    }

    @Test
    public void testLocalRequestsToBeSent()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        Rider currentRider = Helper.getCurrentRider();
        Driver driver = new Driver();
        currentRider = request1.addRider(currentRider);
        driver = request1.addDriver(driver);
        RequestCollection localRequests = new RequestCollection();
        localRequests.add(request1);
        assertTrue(Helper.saveLocalRequests(localRequests));


    }


    @Test
    public void testStartLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(Helper.getStartLocationOnMap(), Helper.getEndLocationOnMap());
        assertEquals(request1.getStartLocation(),geoLocation1);
    }

    @Test
    public void testEndLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(Helper.getStartLocationOnMap(), Helper.getEndLocationOnMap());
        assertEquals(request1.getEndLocation(),geoLocation2);
    }

    @Test
    public void testViewStartLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        assertEquals(Helper.viewStartLocationOnMap(request1.getStartLocation()),geoLocation1);

    }


    @Test
    public void testViewEndLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        assertEquals(Helper.viewStartLocationOnMap(request1.getEndLocation()),geoLocation2);

    }




}
