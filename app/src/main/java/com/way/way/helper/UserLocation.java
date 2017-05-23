package com.way.way.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.way.way.Activity.MainActivity;
import com.way.way.http.HttpCalls;
import com.way.way.pojo.Location;

import static com.way.way.Constants.HOST_IP;
import static com.way.way.Constants.HOST_PORT;

/**
 * Created by anurag.yadav on 4/16/17.
 */

public class UserLocation {

    final AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
    final AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);



    public void addUserLocation(final String username, final GoogleMap mMap, final Context context, final LatLng latLng) {
        new AsyncTask<Void, Void, String>() {
            Location location = null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    location = new Location();
                    location.setUsername(username);
                    System.out.println("Latlng -> " + latLng);
                    location.setLatitude(latLng.latitude);
                    location.setLongitude(latLng.longitude);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //inAnimation.setDuration(200);
                //progressBarHolder.setAnimation(inAnimation);
                //progressBarHolder.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {
                if (location != null) {
                    return HttpCalls.putHttp("http://" + HOST_IP + "/location", new Gson().toJson(location));
                } else {
                    return "";
                }
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                //outAnimation.setDuration(200);
                //progressBarHolder.setAnimation(outAnimation);
                //progressBarHolder.setVisibility(View.GONE);
                System.out.println("Http Response-> " + response.toString());
                try {
                    Location location = new Gson().fromJson(response, Location.class);
                    if (location != null) {
                        Toast.makeText(context, "Congrats: User Location Added", Toast.LENGTH_LONG).show();
                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(username));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12 ));
                        MainActivity.updateMarker(marker);
                    } else {
                        Toast.makeText(context, "User Name or Password does not match in Location", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(response);
                    Toast.makeText(context, "User Location Network Error Occurred", Toast.LENGTH_LONG).show();
                }
            }

        }.execute();
    }

}
