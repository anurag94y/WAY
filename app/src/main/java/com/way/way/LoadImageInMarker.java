package com.way.way;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.squareup.picasso.Picasso;
import com.way.way.Activity.MainActivity;

import java.io.IOException;

/**
 * Created by anurag.yadav on 4/11/17.
 */

public class LoadImageInMarker extends AsyncTask<Void, Void, Bitmap> {

    private Context context;
    private GoogleMap mMap;
    private String imageUrl;
    private double latitude;
    private double longitude;
    private String name;
    private Marker marker;

    public LoadImageInMarker(Context context, GoogleMap mMap, String imageUrl, double latitude, double longitude, String name, Marker marker) {
        this.context = context;
        this.mMap = mMap;
        this.imageUrl = imageUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.marker = marker;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            Bitmap bmp = Picasso.with(context).load(imageUrl).get();
            System.out.println("Image :-> " + bmp);
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("UnsuccessFully");
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        try {
            MarkerOptions customMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);
            Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
            customMarker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
            marker = mMap.addMarker(customMarker);
            System.out.println(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
