package com.youber.cmput301f16t15.youber;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.gui.LoginActivity;
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

public class ActivitiesAndroidTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    Context appContext;
    Request r;
    User user;
    User driver;

    /**
     * Instantiates a Rider Activity gui test
     */
    public ActivitiesAndroidTest() {
        super(com.youber.cmput301f16t15.youber.gui.LoginActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        cleanup();
    }

    public void init() {
        solo.assertCurrentActivity("Wrong Activity!", com.youber.cmput301f16t15.youber.gui.LoginActivity.class);

        Setup.run(appContext);

        user = new User("linda","Linda", "Belcher", "1/1/2013","7801110000", "alright@gmail.com");
        UserController.saveUser(user);

        driver = new User("bobby", "Bob", "Belcher", "2/3/2012", "7804452133", "omg@gmail.com");
        driver.setCurrentUserType(User.UserType.driver);

        ElasticSearchUser.add add = new ElasticSearchUser.add();
        add.execute(user, driver);

        GeoLocation g1 = new GeoLocation(53.53275790467148, -113.54782104492188);
        GeoLocation g2 = new GeoLocation(53.49723685987482, -113.50456237792969);
        r = new Request(g1, "River Valley Mayfair Edmonton, AB", g2, "6048 106 Street Northwest Edmonton, AB T6H 2T7");
        r.setDistance(10.0);
        r.setDescription("Bob's Burgers");
        r.setPayment(2000.33);

        RequestCollectionsController.addRequest(r);
    }

    public void cleanup() {
        ElasticSearchUser.delete delete = new ElasticSearchUser.delete();
        delete.execute(user, driver);

        ElasticSearchRequest.delete deleteRequest = new ElasticSearchRequest.delete();
        deleteRequest.execute(r);

        UserController.saveUser(new User());
        RequestCollectionsController.saveRequestCollections(new RequestCollection());
    }
    
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        appContext = InstrumentationRegistry.getTargetContext();
    }

    public void testProfile() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "linda");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("RIDER");

        solo.clickOnMenuItem("Profile");
        solo.waitForText("Linda");

        cleanup();
    }

    public void testNewRequestInvalid() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "linda");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("RIDER");

        solo.clickOnButton("New Request");
        solo.waitForText("Please select a start and end point");

        cleanup();
    }

    public void testRequestList() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "linda");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("RIDER");

        solo.clickOnMenuItem("View Requests");
        solo.waitForText("River Valley Mayfair"); // this is a request i added

        cleanup();
    }

    public void testRequestItem() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "linda");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("RIDER");

        solo.clickOnMenuItem("View Requests");
        solo.waitForText("River Valley");

        solo.clickInList(0);
        solo.waitForText("Bob's Burgers");
        solo.waitForText("2000.33");

        cleanup();
    }

    public void testDriverKeyword() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "bobby");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("DRIVER");

        solo.enterText((EditText)solo.getView(R.id.keyword_search), "Burgers");
        solo.clickOnView(solo.getView(R.id.search_spinner));
        solo.clickOnText("Keyword");

        solo.waitForText("River Valley Mayfair");

        cleanup();
    }

    public void testDriverAddress() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "bobby");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("DRIVER");

        solo.enterText((EditText)solo.getView(R.id.keyword_search), "River Valley Mayfair");
        solo.clickOnView(solo.getView(R.id.search_spinner));
        solo.clickOnText("Address");

        solo.waitForText("6048 106 Street Northwest");

        cleanup();
    }

    public void testDriverAccept() {
        init();

        solo.enterText((EditText)solo.getView(R.id.editText), "bobby");
        solo.clickOnButton("LOGIN");
        solo.clickOnButton("DRIVER");

        solo.enterText((EditText)solo.getView(R.id.keyword_search), "Burgers");
        solo.clickOnView(solo.getView(R.id.search_spinner));
        solo.clickOnText("Keyword");

        solo.waitForText("River");
        solo.clickInList(0);
        solo.waitForText("linda"); // shows rider
        solo.clickOnButton("OPTIONS");
        solo.clickOnButton("OFFER RIDE");
        solo.clickOnText("Yes");

        solo.waitForText("acceptedByDrivers");
        solo.clickOnMenuItem("View Requests");
        solo.waitForText("River Valley Mayfair");
        solo.waitForText("acceptedByDrivers");

        cleanup();
    }
}
