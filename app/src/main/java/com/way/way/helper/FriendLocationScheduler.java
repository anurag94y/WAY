package com.way.way.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.test.mock.MockContext;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.way.way.Activity.MainActivity;

import java.util.HashMap;

/**
 * Created by anurag.yadav on 4/17/17.
 */

public class FriendLocationScheduler extends BroadcastReceiver {

    private FriendLocation friendLocation = new FriendLocation();
    private static GoogleMap mMap;
    private static HashMap<String, Marker> friendsMarker;
    private static Context context;

    public FriendLocationScheduler(GoogleMap mMap, HashMap<String, Marker> friendsMarker, Context context) {
        this.mMap = mMap;
        this.friendsMarker = friendsMarker;
        this.context = context;
    }

    public FriendLocationScheduler() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("$$$$$$$$ I am running $$$$$$$$");
        friendLocation.addFriendLocation("hellboy_86", mMap, (HashMap<String, Marker>) friendsMarker, context);
    }
}
