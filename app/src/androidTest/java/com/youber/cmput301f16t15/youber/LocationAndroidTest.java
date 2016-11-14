package com.youber.cmput301f16t15.youber;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;

import org.junit.Test;

/**
 * Created by Jess on 2016-11-13.
 */

public class LocationAndroidTest {

    @Test
    public void testStartLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//        Request request1 = new Request(Helper.getStartLocationOnMap(), Helper.getEndLocationOnMap());
//        assertEquals(request1.getStartLocation(),geoLocation1);
    }

    @Test
    public void testEndLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
//        Request request1 = new Request(Helper.getStartLocationOnMap(), Helper.getEndLocationOnMap());
//        assertEquals(request1.getEndLocation(),geoLocation2);
    }

    @Test
    public void testViewStartLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
//        assertEquals(Helper.viewStartLocationOnMap(request1.getStartLocation()),geoLocation1);
    }


    @Test
    public void testViewEndLocationOnMap()
    {
        GeoLocation geoLocation1 = new GeoLocation(90.0, 90.0);
        GeoLocation geoLocation2 = new GeoLocation(100.0, 100.0);
        Request request1 = new Request(geoLocation1, geoLocation2);
//        assertEquals(Helper.viewStartLocationOnMap(request1.getEndLocation()),geoLocation2);

    }
}
