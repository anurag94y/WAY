package com.way.way.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.way.way.http.HttpCalls;
import com.way.way.pojo.Friend;
import com.way.way.pojo.Location;

import java.util.HashMap;

import static com.way.way.Constants.HOST_IP;
import static com.way.way.Constants.HOST_PORT;

/**
 * Created by anurag.yadav on 4/16/17.
 */

public class FriendLocation {

    public void addFriendLocation(final String username, final GoogleMap mMap, final HashMap<String, Marker> markers, final Context context) {
        final HashMap<String, String> friendsLocationMap = new HashMap<>();
        final HashMap<String, Bitmap> friendsImageMap = new HashMap<>();
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                String response = HttpCalls.getHttp("http://" + HOST_IP + "/friend?username=" + username);
                //System.out.println("Http Response-> " + response.toString());
                try {
                    Friend friend = new Gson().fromJson(response, Friend.class);
                    if (friend != null && friend.getFriendList() != null) {
                        for (String friendUsername : friend.getFriendList()) {
                            try {
                                String friendsLocationResponse = HttpCalls.getHttp("http://" + HOST_IP + "/location?username=" + friendUsername);
                                //System.out.println("Friend's Username : Response -> " + username + " " + friendsLocationResponse);
                                friendsLocationMap.put(friendUsername, friendsLocationResponse);
                                Bitmap bitmap = Picasso.with(context).load("https://image.flaticon.com/teams/new/1-freepik.jpg").get();
                                Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                                friendsImageMap.put(friendUsername, bmp);
                                System.out.println("Username : bmp -> " + friendUsername + " " +  bmp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Response -> " + response);
                }
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                System.out.println("Http Response-> " + response.toString());
                try {
                    Friend friend = new Gson().fromJson(response, Friend.class);
                    if (friend != null && friend.getFriendList() != null) {
                        for (String friendUsername : friend.getFriendList()) {
                            try {
                                String friendLocationResponse = friendsLocationMap.get(friendUsername);
                                System.out.println("Friend's Username : Response -> " + username + " " + friendLocationResponse);
                                if (friendLocationResponse != null) {
                                    if (markers.containsKey(friendUsername))
                                        markers.get(friendUsername).remove();
                                    Location friendLocation = new Gson().fromJson(friendLocationResponse, Location.class);
                                    System.out.println("Username : bmp " + friendUsername + " " +  friendsImageMap.get(friendUsername));
                                    MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(friendLocation.getLatitude(), friendLocation.getLongitude())).title(friendUsername).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                                    if (friendsImageMap.get(friendUsername) != null)
                                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(friendsImageMap.get(friendUsername)));
                                    Marker marker = mMap.addMarker(markerOptions);
                                    marker.showInfoWindow();
                                    marker.setVisible(true);
                                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(friendLocation.getLatitude(), friendLocation.getLongitude()), 0));
                                    markers.put(friendUsername, marker);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText(context, "Congrats: User Friend's Location Added", Toast.LENGTH_LONG).show();
                    } else {
                        //Toast.makeText(context, "User Name or Password does not match in Friend", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(response);
                    //Toast.makeText(context, "Friend Network Error Occurred", Toast.LENGTH_LONG).show();
                }
            }

        }.execute();
    }
}
