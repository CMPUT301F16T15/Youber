package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
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

        GeoLocation start = new GeoLocation(1, 1);
        GeoLocation end = new GeoLocation(2, 2);
        Request request = new Request(start, end);

        RequestCollectionsController.addRequest(request);
        RequestCollection requests = RequestCollectionsController.getRequestCollection();

        assertTrue("Wrong request collection size", requests.size() == 1);
        assertEquals(request, requests.getRequestByUUID(request.getUUID()));
    }

}
