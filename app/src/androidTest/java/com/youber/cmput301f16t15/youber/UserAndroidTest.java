package com.youber.cmput301f16t15.youber;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.youber.cmput301f16t15.youber.commands.MacroCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jess on 2016-11-13.
 * @author Jessica Huynh, Aaron Philips, Calvin Ho, Tyler Mathieu, Reem Maarouf
 */

public class UserAndroidTest {
    @Test
    public void testUniqueUsername() throws Exception {
        String username = "blahsajdlsfhdkjalg"; // chance of this in elastic serach is low

        User user = ElasticSearchController.getUser(username);
        assertEquals("User is not unique", null, user);
    }



    @Test
    public void testGetUserByUsername()
    {
        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");
        ElasticSearchUser.add add = new ElasticSearchUser.add();
        add.execute(user);

        User esUser = ElasticSearchController.getUser("tina");
        assertEquals(user.getUsername(), esUser.getUsername());
        ElasticSearchUser.delete delete = new ElasticSearchUser.delete();
        delete.execute(user);
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
    public void testSetVechicleModel(){

        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setVehicleModel("Taurus");
        assertEquals(UserController.getUser().getModel(),"Taurus");
    }


    @Test
    public void testSetVechicleMake(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setVehicleMake("Ford");
        assertEquals(UserController.getUser().getMake(),"Ford");

    }

    @Test
    public void testSetVechicleYear(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setVehicleYear("2003");
        assertEquals(UserController.getUser().getYear(),"2003");
    }

    @Test
    public void testSetVechicleColor(){
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");

        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setVehicleColour("Beige");
        assertEquals(UserController.getUser().getColour(),"Beige");
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
        MacroCommand.setContext(appContext);
        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setUserType(User.UserType.rider);

        GeoLocation g1 = new GeoLocation(10,10);
        GeoLocation g2 = new GeoLocation(10.1, 10.1);

        Request request =new Request(g1, "", g2, "");
        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.addRequest(request);

        assertTrue(UserController.getUser().getRequestUUIDs().contains(request.getUUID()));
    }

    @Test
    public void testGetDriverRequests()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");
        MacroCommand.setContext(appContext);
        UserController.setContext(appContext);
        UserController.saveUser(user);

        UserController.setUserType(User.UserType.driver);

        GeoLocation g1 = new GeoLocation(10,10);
        GeoLocation g2 = new GeoLocation(10.1, 10.1);

        Request request =new Request(g1, "", g2, "");
        RequestCollectionsController.setContext(appContext);
        RequestCollectionsController.addRequest(request);

        assertTrue(UserController.getUser().getRequestUUIDs().contains(request.getUUID()));
    }
}
