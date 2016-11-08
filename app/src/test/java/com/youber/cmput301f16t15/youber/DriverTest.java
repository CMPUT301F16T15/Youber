package com.youber.cmput301f16t15.youber;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import static org.junit.Assert.*;
public class  DriverTest {
    //Tests US 01.05.01
    @Test
    public void testCallDriver() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);

        Driver driver = RequestController.getDriver(request1);
        assertTrue(rider1.call(driver));
    }
    //Tests US 01.05.01
    @Test
    public void testEmailDriver() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);

        Driver driver = RequestController.getDriver(request1);
        assertTrue(rider1.email(driver));
    }
    //Tests US 05.01.01
    @Test
    public void testGetOfferPayment() {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Rider rider1 = new Rider();
        RequestController.addRequest(request1, rider1);

        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);

        rider1.makePayment(request1.getUUID());
        assertEquals(request1.getCost(), driver1.getOfferPayment(request1.getUUID()));
    }

    //Tests US 05.01.01
    @Test
    public void testMultipleAcceptance() {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);

        Driver driver1 = new Driver();
        Driver driver2 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request1, driver2);

        assertEquals(driver1.getAcceptedRequests().get(request1.getUUID()), request1);
        assertEquals(driver2.getAcceptedRequests().get(request1.getUUID()), request1);
    }

    //Tests US 05.02.01
    @Test
    public void testDriverGetRequests() {
        GeoLocation geoLocation1 = new GeoLocation(53.623236, -113.569712);
        GeoLocation geoLocation2 = new GeoLocation(53.614820, -113.569697);

        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);

        Driver driver1 = new Driver();

        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request2, driver1);

        RequestCollection driverRequests = Helper.getTotalRequestsByDriver(driver1);
        assertTrue(driverRequests.containsKey(request1.getUUID()));
    }

    //Tests US 04.01.01
    @Test
    public void testDriverSearchOpenRequestsByLocation() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        RequestCollection totalRequests = Helper.getTotalRequests();
        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);
        assertTrue(totalRequests.getByGeolocation(new GeoLocation(90.0, 90.0)).
                contains(driver1.getRequest(request1.getUUID())));
    }
    
    //Tests US 04.02.01
    @Test
    public void testDriverSearchOpenRequestsByKeyword() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2, "Search Term");
        RequestCollection totalRequests = Helper.getTotalRequests();
        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);
        assertTrue(totalRequests.getByKeyword("Search Term").
                contains(driver1.getRequest(request1.getUUID())));
    }

    //Tests US 05.04.01 
    @Test
    public void testDriverConfirmation() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        Driver driver1 = new Driver();
        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.confirmRequest(request1, driver1);
        assertTrue(request1.isConfirmed());
    }
    
    //Tests US 05.02.01
    @Test
    public void testDriverGetPendingRequests() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
        Request request2 = new Request(geoLocation1, geoLocation2);

        Driver driver1 = new Driver();

        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request2, driver1);
        assertEquals(2, driver1.getPendingRequests().size());
    }
    
    //Tests US 05.02.01
    @Test
    public void testDriverGetPendingRequestsDesciption() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2, "Search Term 1");
        Request request2 = new Request(geoLocation1, geoLocation2, "Search Term 2");

        Driver driver1 = new Driver();

        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request2, driver1);
        assertEquals(driver1.getPendingRequest(request1.getUUID()).getDescription(), "Search Term 1");
    }
    
    //Tests US 05.02.01
    @Test
    public void testDriverGetPendingRequestsStartLocation() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2, "Search Term 1");
        Request request2 = new Request(geoLocation1, geoLocation2, "Search Term 2");

        Driver driver1 = new Driver();

        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request2, driver1);

        assertEquals(driver1.getPendingRequest(request1.getUUID()).getStartLocation(), geoLocation1);

    }

    //Tests US 05.02.01
    @Test
    public void testDriverGetPendingRequestsEndLocation() {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2, "Search Term 1");
        Request request2 = new Request(geoLocation1, geoLocation2, "Search Term 2");

        Driver driver1 = new Driver();

        RequestController.linkDriverWithRequest(request1, driver1);
        RequestController.linkDriverWithRequest(request2, driver1);

        assertEquals(driver1.getPendingRequest(request1.getUUID()).getEndLocation(), geoLocation2);

    }
    //Tests US 05.03.01
    @Test
    public void testDriverCheckRiderAcceptance() {

            GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
            GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
            Request request1 = new Request(geoLocation1, geoLocation2, "Search Term 1");
            Driver driver1 = new Driver();

            RequestController.linkDriverWithRequest(request1, driver1);
            RequestCollection totalRequests = Helper.getTotalRequests();
            RequestCollection ridersAcceptedRequestsForDrivers = totalRequests.getAcceptedAcceptedRequests();

            assertEquals(ridersAcceptedRequestsForDrivers.get(request1.getUUID()), request1);
    }
    //Tests US 08.04.01
    @Test (expected = NotaDriverException.class)
    public void testGetDriverLocalAcceptedRequests()
    {
        RequestCollection savedLocal = Helper.getLocalRequests();
        Driver currentUser = Helper.getCurrentDriver();

        RequestCollection acceptedRequests = savedLocal.getAcceptedRequestsForDrivers(currentUser);
    }
}
