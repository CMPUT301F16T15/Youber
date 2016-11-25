package com.youber.cmput301f16t15.youber.misc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;

import com.youber.cmput301f16t15.youber.R;
import com.youber.cmput301f16t15.youber.commands.AddUserCommand;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearch;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchController;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchRequest;
import com.youber.cmput301f16t15.youber.elasticsearch.ElasticSearchUser;
import com.youber.cmput301f16t15.youber.exceptions.UserNotFoundException;
import com.youber.cmput301f16t15.youber.gui.DriverMainActivity;
import com.youber.cmput301f16t15.youber.gui.MainActivity;
import com.youber.cmput301f16t15.youber.gui.RequestViewActivity;
import com.youber.cmput301f16t15.youber.gui.UserTypeActivity;
import com.youber.cmput301f16t15.youber.requests.RequestCollection;
import com.youber.cmput301f16t15.youber.requests.RequestCollectionsController;
import com.youber.cmput301f16t15.youber.users.User;
import com.youber.cmput301f16t15.youber.users.UserController;

import java.util.HashSet;
import java.util.UUID;

/**
 * Created by Aaron Philips on 11/18/2016.
 */

public class Setup {





    public static void run(Context context){

        ElasticSearchController.setupPutmap();
        UserController.setContext(context);
        RequestCollectionsController.setContext(context);
        User user=null;
        String username= UserController.getUser().getUsername();
        try{
            user = ElasticSearchUser.getUser(username);
        }
        catch (UserNotFoundException e){}

        if(hasUpdated()){
            sendRequestUpdateNotification(context);
        }

        if(checkUserUpdated()){
            UserController.observable.addListener(new Updater());

            AddUserCommand addUser = new AddUserCommand(user);
            UserController.observable.notifyListeners(addUser);

            UserController.clearAcceptedWhenConfirmed();
            UserController.saveUser(user);
        }
        if(checkRequestsUpdated()){
            RequestCollection requestCollection = ElasticSearchRequest.getRequestCollection(user.getRequestUUIDs());
            RequestCollectionsController.saveRequestCollections(requestCollection);
        }
    }
    //https://developer.android.com/guide/topics/ui/notifiers/notifications.html
    public static void sendRequestUpdateNotification(Context context){

        Notification.Builder mBuilder =
                new Notification.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Youber")
                        .setContentText("Your Requests have updated")
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setPriority(Notification.PRIORITY_HIGH);
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, RequestViewActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(RequestViewActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        Notification notification=mBuilder.build();
        notification.flags=Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(1, notification);
    }
    private static boolean hasUpdated(){
       return checkUserUpdated()||checkRequestsUpdated();
    }

    private static boolean checkUserUpdated(){
        //Wondering if this should use an overriden equals method for user

        User latestUser=null;
        try{latestUser =ElasticSearchUser.getUser(UserController.getUser().getUsername());}
        catch (UserNotFoundException e){    //save user?
            return true;
        }

        HashSet<UUID> latestAcceptedUUIDS =UserController.getUser().getCurrentUserType()== User.UserType.rider ?
                latestUser.getRequestUUIDs():latestUser.getDriverUUIDs();

        HashSet<UUID> currentAcceptedUUIDS = UserController.getUser().getCurrentUserType()== User.UserType.rider ?
                UserController.getUser().getRequestUUIDs():UserController.getUser().getDriverUUIDs();

        if(!currentAcceptedUUIDS.equals(latestAcceptedUUIDS)) return true;


        if(UserController.getUser().getCurrentUserType()== User.UserType.driver) {
            HashSet<UUID> latestConfirmedUUIDS = latestUser.getConfirmedDriverUUIDs();
            HashSet<UUID> currentConfirmedUUIDS = UserController.getUser().getConfirmedDriverUUIDs();

            if(!currentConfirmedUUIDS.equals(latestConfirmedUUIDS)) return true;

        }

        return false;
    }

    private static boolean checkRequestsUpdated(){
        //at this point userUUIDS have to be up to date
        HashSet<UUID> uuids= UserController.getUser().getCurrentUserType()== User.UserType.rider ?
                UserController.getUser().getRequestUUIDs():UserController.getUser().getDriverUUIDs();
        RequestCollection latestRequests= ElasticSearchRequest.getRequestCollection(uuids);


        return !latestRequests.equals(RequestCollectionsController.getRequestCollection());
    }

}
