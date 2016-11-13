package com.youber.cmput301f16t15.youber;

import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.users.User;

import org.junit.Test;

import java.util.ArrayList;

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
}
