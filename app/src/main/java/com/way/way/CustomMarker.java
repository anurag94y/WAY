package com.way.way;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by anurag.yadav on 3/29/17.
 */

public class CustomMarker {

    public MarkerOptions getCustomMarker(double latitude, double longitude, String name, final String imageUrl, final Context context) {
        MarkerOptions customMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(name);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.unnamed), 80, 80, true);
        customMarker.icon(BitmapDescriptorFactory.fromBitmap(bmp));
        System.out.println(bmp);
        System.out.println("Custome Marker := " + customMarker);
        return customMarker;
    }
}
