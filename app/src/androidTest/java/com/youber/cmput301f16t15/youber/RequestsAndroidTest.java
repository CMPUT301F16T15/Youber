package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.primitives.Booleans;

import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.requests.RequestController;

import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Jess on 2016-11-13.
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class RequestsAndroidTest { // mainly using the controller

    private void init() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        MacroCommand.setContext(appContext);

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

    private void cleanup() {
        UserController.saveUser(new User());
        RequestCollectionsController.saveRequestCollections(new RequestCollection());
    }

    @Test
    public void testAddingOneRequest() {
        init();

        GeoLocation start = new GeoLocation(1, 1);
        GeoLocation end = new GeoLocation(2, 2);
        Request request = new Request(start, "", end, "");

        RequestCollectionsController.addRequest(request);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertTrue("Wrong request collection size", requests.size() == 1);
        assertEquals(request, requests.getRequestByUUID(request.getUUID()));

        cleanup();
    }

    @Test
    public void testAddingMultipleRequests() {
        init();

        GeoLocation start = new GeoLocation(1, 1);
        GeoLocation end = new GeoLocation(2, 2);
        Request request = new Request(start, "", end, "");
        Request request2 = new Request(start, "", end, "");

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertEquals("Wrong request collection size", 2, requests.size());
        assertEquals(request, requests.getRequestByUUID(request.getUUID()));
        assertEquals(request2, requests.getRequestByUUID(request2.getUUID()));

        cleanup();
    }

    @Test
    public void testCancelRequest() { // cancel deletes this
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.deleteRequest(request);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertEquals("Wrong request collection size", 0, requests.size());

        cleanup();
    }

    @Test
    public void testCancelWithMultipleRequests() // cancel deletes this
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.deleteRequest(request);

        RequestCollection requests = RequestCollectionsController.getRequestCollection();
        assertEquals("Wrong request collection size", 1, requests.size());
        assertEquals("Wrong request", request2, requests.getRequestByUUID(request2.getUUID()));

        cleanup();
    }

    @Test
    public void testGetOpenRequest()
    {
        //right now the structure of the getRequests, gets all but we should split this up
        // getOpen, getAcceptedByDrivers, getRiderSelectedDriver, getPayed/getCompleted
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1, "", geoLocation2, "");

        RequestCollectionsController.addRequest(request);
        RequestCollectionsController.addRequest(request2);
        RequestCollectionsController.addRequest(request3);

        RequestCollection openRequests = RequestCollectionsController.getOpenRequests();

        assertEquals(Request.RequestStatus.opened, openRequests.get(request.getUUID()).getCurrentStatus());
        assertEquals(Request.RequestStatus.opened, openRequests.get(request2.getUUID()).getCurrentStatus());
        assertEquals(Request.RequestStatus.opened, openRequests.get(request3.getUUID()).getCurrentStatus());

        cleanup();
    }

    @Test
    public void testGetAcceptedRequest() // project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1, "", geoLocation2, "");

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

        assertEquals("Wrong status",Request.RequestStatus.acceptedByDrivers, acceptedRequests.get(request.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.acceptedByDrivers, acceptedRequests.get(request2.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.acceptedByDrivers, acceptedRequests.get(request3.getUUID()).getCurrentStatus());

        cleanup();
    }

    @Test
    public void testGetClosedRequests() // AARON HERE AND DOWN, project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1, "", geoLocation2, "");

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

        assertEquals("Wrong status",Request.RequestStatus.completed, closedRequests.get(request.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.completed, closedRequests.get(request2.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.completed, closedRequests.get(request3.getUUID()).getCurrentStatus());

        cleanup();
    }

    @Test
    public void testGetPaidRequests() // project part 5
    {
        init();

        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request = new Request(geoLocation1, "", geoLocation2, "");
        Request request2 = new Request(geoLocation1, "", geoLocation2, "");
        Request request3 = new Request(geoLocation1, "", geoLocation2, "");

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

        assertEquals("Wrong status",Request.RequestStatus.paid, paidRequests.get(request.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.paid, paidRequests.get(request2.getUUID()).getCurrentStatus());
        assertEquals("Wrong status",Request.RequestStatus.paid, paidRequests.get(request3.getUUID()).getCurrentStatus());

        cleanup();
    }
}
