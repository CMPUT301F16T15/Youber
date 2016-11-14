package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.gui.UserTypeActivity;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.Rider;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jess on 2016-11-13.
 */

public class UserAndroidTest {
    @Test
    public void testUniqueUsername() throws Exception {
        String username = "blahsajdlsfhdkjalg"; // chance of this in elastic serach is low
        ElasticSearchUser.getObjects searchUser = new ElasticSearchUser.getObjects();
        searchUser.execute(username);

        try {

            ArrayList<User> users = searchUser.get();

            if(users.size() == 0)
                assertTrue(true);
            else
                assertFalse("Wrong number of users found", false);

        } catch (Exception e) {
            assertFalse("Elastic search get failed", false);
        }
    }



    @Test
    public void testGetUserByUsername() // not sure about this anymore
    {
        // note this is not always the most reliant test since grabs from elastic search
        // if postman is cleared, the user will not exist

        String username = "b"; // right now this user exists
        ElasticSearchUser.getObjects searchUser = new ElasticSearchUser.getObjects();
        searchUser.execute(username);

        try {

            ArrayList<User> users = searchUser.get();

            if(users.size() == 1)
                assertTrue(true);
            else
                assertFalse("Wrong number of users found", false);

        } catch (Exception e) {
            assertFalse("Elastic search get failed", false);
        }
    }


    @Test
    public void testChangeUserType()
    {

        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setUserType(User.UserType.rider);
        assertEquals(UserController.getUser().getCurrentUserType(), User.UserType.rider);

    }


    @Test
    public void testSetEmail()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setEmail("notReal@noEmail.com");
        assertEquals(UserController.getUser().getEmail(), "notReal@noEmail.com");
    }

    @Test
    public void testSetPhoneNumber()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setPhoneNumber("000");
        assertEquals(UserController.getUser().getPhoneNumber(), "000");
    }



    @Test
    public void testSetBirthOfDate()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setDateOfBirth("000");
        assertEquals(UserController.getUser().getDateOfBirth(), "000");
    }



    @Test
    public void testSetFirstName()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setFirstName("NewName");
        assertEquals(UserController.getUser().getFirstName(), "NewName");
    }



    @Test
    public void testSetLastName()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setLastName("NewName");
        assertEquals(UserController.getUser().getLastName(), "NewName");
    }



    // get requests for rider
    @Test
    public void testGetRiderRequests()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setUserType(User.UserType.rider);

        GeoLocation g1 = new GeoLocation(10,10);
        GeoLocation g2 = new GeoLocation(10.1, 10.1);

        Request request =new Request(g1,g2);
        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.addRequest(request);

        assertTrue(UserController.getUser().getRequestUUIDs().contains(request.getUUID()));
    }

    @Test
    public void testGetDriverRequests()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setUserType(User.UserType.driver);

        GeoLocation g1 = new GeoLocation(10,10);
        GeoLocation g2 = new GeoLocation(10.1, 10.1);

        Request request =new Request(g1,g2);
        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.addRequest(request);

        assertTrue(UserController.getUser().getRequestUUIDs().contains(request.getUUID()));
    }



}
