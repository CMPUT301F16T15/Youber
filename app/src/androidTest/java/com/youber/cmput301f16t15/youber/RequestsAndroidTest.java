package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.users.Rider;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jess on 2016-11-13.
 */

public class RequestsAndroidTest { // mainly using the controller

    @Test
    public void testAddingOneRequest() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.saveRequestCollections(new RequestCollection());

        GeoLocation start = new GeoLocation(1, 1);
        GeoLocation end = new GeoLocation(2, 2);
        Request request = new Request(start, end);

        RequestCollectionsController.addRequest(request);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertTrue("Wrong request collection size", requests.size() == 1);
        assertEquals(request, requests.getRequestByUUID(request.getUUID()));
    }

    @Test
    public void testAddingMultipleRequests() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.saveRequestCollections(new RequestCollection());

        GeoLocation start = new GeoLocation(1, 1);
        GeoLocation end = new GeoLocation(2, 2);
        Request request = new Request(start, end);
        Request request2 = new Request(start, end);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertEquals("Wrong request collection size", 2, requests.size());
        assertEquals(request, requests.getRequestByUUID(request.getUUID()));
        assertEquals(request2, requests.getRequestByUUID(request2.getUUID()));
    }

    @Test
    public void testCancelRequest() // cancel deletes this
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        //TODO
//        RequestController.addRequest(request1,rider1);
//        RequestController.deleteRequest(request1, rider1);
        assertEquals(0, rider1.getRequestUUIDs().size());

    }

    @Test
    public void testCancelWithMultipleRequests() // cancel deletes this
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        //TODO
//        RequestController.addRequest(request1,rider1);
//        RequestController.deleteRequest(request1, rider1);
        assertEquals(0, rider1.getRequestUUIDs().size());

    }

    @Test
    public void testGetOpenRequest()
    {
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//
//        Request request1 = new Request(geoLocation1, geoLocation2);
//        assertEquals(request1.getCurrentStatus(), Request.RequestStatus.opened);
    }


    @Test
    public void testGetClosedRequests() // AARON HERE AND DOWN, project part 5
    {
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//
//        Request request1 = new Request(geoLocation1, geoLocation2);
//        Rider rider1 = new Rider();
//        rider1.addRequest(request1);
//        rider1.getRequest(request1.getUUID()).close();
//
//        assertEquals("Expected to fail until Project Part 5", 1, rider1.getClosedRequests().size());
    }

    @Test
    public void testGetAcceptRequest() // project part 5
    {
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//
//        Request request1 = new Request(geoLocation1, geoLocation2);
//        RequestController.acceptRequest(request1);
//
//        assertTrue(request1.isAccepted());
    }

    @Test
    public void testGetCompletedRequests() // project part 5
    {
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//
//        Request request1 = new Request(geoLocation1, geoLocation2);
//        Request request2 = new Request(geoLocation1, geoLocation2);
//        Rider rider1 = new Rider();
//
//        RequestController.completeRequest(request1);
//        assertEquals(1, rider1.getClosedRequests().size());

    }

    @Test
    public void testRequestAcceptedByDrivers() // project part 5
    {
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//        Request request1 = new Request(geoLocation1, geoLocation2);
//        Driver driver1 = new Driver();
//        Driver driver2 = new Driver();
//
//        driver2 = request1.addDriver(driver2);
//        driver1 = request1.addDriver(driver1);
//        RequestController.addDriver(request1,driver1);
//        RequestController.addDriver(request1,driver2);
//        assertTrue(request1.isAccepted());
    }

    @Test
    public void testNotifyAcceptedRequest() // project part 5
    {
////        View view = Helper.getView();
//        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
//        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//
//        Request request1 = new Request(geoLocation1, geoLocation2);
//
//        Rider rider1 = new Rider();
//
//        //TODO
////        RequestController.addRequest(request1,rider1);
//        RequestController.acceptRequest(request1);
////        View view2 = Helper.getView();
////        assertFalse(view.equals(view2));
    }
}
