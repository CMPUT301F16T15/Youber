package com.youber.cmput301f16t15.youber;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.gui.RiderMainActivity;
import com.youber.cmput301f16t15.youber.misc.GeoLocation;
import com.youber.cmput301f16t15.youber.misc.Setup;
import com.youber.cmput301f16t15.youber.requests.Request;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Jess on 2016-11-13.
 */

public class RiderActivityAndroidTest extends ActivityInstrumentationTestCase2<RiderMainActivity> {

    private Solo solo;
    Context appContext;

    /**
     * Instantiates a Rider Activity gui test
     */
    public RiderActivityAndroidTest() {
        super(com.youber.cmput301f16t15.youber.gui.RiderMainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();

        ElasticSearchUser.delete delete = new ElasticSearchUser.delete();
        delete.execute(UserController.getUser());

        UserController.saveUser(new User());
        RequestCollectionsController.saveRequestCollections(new RequestCollection());
    }

    @BeforeClass
    public void init() {
        solo.assertCurrentActivity("Wrong Activity!", com.youber.cmput301f16t15.youber.gui.RiderMainActivity.class);

        Setup.run(appContext);

        User user = new User("tina","Tina", "Belcher", "2013","7801110000", "ughh@gmail.com");
        UserController.saveUser(user);
        ElasticSearchUser.add add = new ElasticSearchUser.add();
        add.execute(user);
    }

    /**
     * Starting test
     * @ Throws Exception
     */
    public void testStart() throws Exception {
        Log.d("Start", "testStart()");
        Activity activity = getActivity();
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        appContext = InstrumentationRegistry.getTargetContext();
    }

    public void testNewRequestInvalid() {
        solo.clickOnButton("New Request");
    }
}
