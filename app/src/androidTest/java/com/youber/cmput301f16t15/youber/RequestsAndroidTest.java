package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.primitives.Booleans;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;
import com.youber.cmput301f16t15.youber.users.Driver;
import com.youber.cmput301f16t15.youber.users.Rider;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jess on 2016-11-13.
 */

public class RequestsAndroidTest { // mainly using the controller

    private void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.saveRequestCollections(new RequestCollection());
    }

    private void init2() {
        User user = new User("louise","Louise", "Belcher", "2013","7801110000", "ughh2@gmail.com");
        user.setCurrentUserType(User.UserType.driver);
        UserController.saveUser(user);
        RequestCollectionsController.saveRequestCollections(new RequestCollection());
    }

    @Test
    public void testAddingOneRequest() {
        init();

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
        init();

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
    public void testCancelRequest() { // cancel deletes this
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.deleteRequest(request);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertEquals("Wrong request collection size", 0, requests.size());
    }

    @Test
    public void testCancelWithMultipleRequests() // cancel deletes this
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.deleteRequest(request);

        RequestCollection requests = RequestCollectionsController.getRequestCollection();
        assertEquals("Wrong request collection size", 1, requests.size());
        assertEquals("Wrong request", request2, requests.getRequestByUUID(request2.getUUID()));
    }

    @Test
    public void testGetOpenRequest()
    {
        //right now the structure of the getRequests, gets all but we should split this up
        // getOpen, getAcceptedByDrivers, getRiderSelectedDriver, getPayed/getCompleted
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Request request3 = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);

        RequestCollection openRequests = RequestCollectionsController.getOpenRequests();

        assertEquals(Request.RequestStatus.opened, openRequests.get(request.getUUID()).getCurrentStatus());
        assertEquals(Request.RequestStatus.opened, openRequests.get(request2.getUUID()).getCurrentStatus());
        assertEquals(Request.RequestStatus.opened, openRequests.get(request3.getUUID()).getCurrentStatus());
    }


    @Test
    public void testGetClosedRequests() // AARON HERE AND DOWN, project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Request request3 = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);



        RequestController.closeRequest(request);
        RequestController.closeRequest(request2);// could happen on other app
        RequestController.closeRequest(request3);

        init2();

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);

        RequestCollection closedRequests = RequestCollectionsController.getClosedRequests();

//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, closedRequests.get(request.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, closedRequests.get(request2.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, closedRequests.get(request3.getUUID()).getCurrentStatus());

        assertFalse(true);
    }

    @Test
    public void testGetAcceptedRequest() // project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Request request3 = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);

        RequestController.acceptRequest(request);//
        RequestController.acceptRequest(request2);// these same function calls would happen on another users app
        RequestController.acceptRequest(request3);//

        init2();

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);



        RequestCollection acceptedRequests = RequestCollectionsController.getAcceptedRequests();

//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, acceptedRequests.get(request.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, acceptedRequests.get(request2.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, acceptedRequests.get(request3.getUUID()).getCurrentStatus());

        assertFalse(true);
    }

    @Test
    public void testGetPaidRequests() // project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);
        Request request3 = new Request(geoLocation1, geoLocation2);

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);

        RequestController.payRequest(request);//
        RequestController.payRequest(request2);// these same function calls would happen on another users app
        RequestController.payRequest(request3);//

        init2();

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);



        RequestCollection paidRequests = RequestCollectionsController.getPaidRequests();

//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, paidRequests.get(request.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, paidRequests.get(request2.getUUID()).getCurrentStatus());
//        assertEquals("Expected to fail until Project Part 5",Request.RequestStatus.closed, paidRequests.get(request3.getUUID()).getCurrentStatus());

        assertFalse(true);
    }

    @Test
    public void testNotifyAcceptedRequest() // project part 5
    {
        //okay don't know how to test notifications yet, will happen
        Boolean notificationCheck =false;// replace with code that actually checks if notification activity is run
        assertTrue("expected to fail",notificationCheck);
    }
}
