package com.way.way.Activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.way.way.R;
import com.way.way.helper.FriendLocation;
import com.way.way.helper.FriendLocationScheduler;
import com.way.way.helper.SessionManagement;
import com.way.way.helper.UserLocation;

import java.util.HashMap;
import java.util.Map;

import static com.way.way.helper.SessionManagement.KEY_USERNAME;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Context context;
    private static Marker marker = null;
    private static Map<String, Marker> friendsMarker = new HashMap<>();
    private UserLocation userLocation = new UserLocation();
    private FriendLocation friendLocation = new FriendLocation();
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private FriendLocationScheduler scheduler = null;
    private String username = "blade_21";
    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        context = this;
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> userDetails = session.getUserDetails();
        username = userDetails.get(KEY_USERNAME);
        Intent alarmIntent = new Intent(this, FriendLocationScheduler.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        System.out.println("This is Not get Called");
        scheduler = new FriendLocationScheduler(mMap, (HashMap<String, Marker>) friendsMarker, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            System.out.println("Permission Issue");
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                if (marker != null)
                    marker.remove();
                //LoadImageInMarker loadImageInMarker = new LoadImageInMarker(context, mMap, "http://www.dreamworks.com/kungfupanda/images/uploads/properties/KFP3_sq500_s75.3_f106_FIN_.jpg", mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude(), "Panda Bhai" , marker);
                //loadImageInMarker.execute();
                userLocation.addUserLocation(username, mMap, context, new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude())));
                return false;
            }
        });
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //marker = mMap.addMarker(new CustomMarker().getCustomMarker(-34, 151, "Panda Bhai", "http://www.dreamworks.com/kungfupanda/images/uploads/properties/KFP3_sq500_s75.3_f106_FIN_.jpg", context));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //userLocation.addUserLocation(username, mMap, this);
        friendLocation.addFriendLocation(username, mMap, (HashMap<String, Marker>) friendsMarker, this);
        startAlarm();

    }

    public static void updateMarker(Marker mark) {
        marker = mark;
    }

    public void startAlarm() {
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 5000; // 10 seconds
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarm() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAlarm();
    }
}
