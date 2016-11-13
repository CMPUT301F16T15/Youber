package com.youber.cmput301f16t15.youber;

/**
 * Created by Reem on 2016-11-11.
 */

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class NeedToDelete {

    @Test
    public void elasticSearchTest() throws Exception {

        ElasticSearchUser.getObjects getter = new ElasticSearchUser.getObjects();
        getter.execute();
        ArrayList<User> users = null;
        try {
            users = getter.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals(users.size(),3);
    }


}
