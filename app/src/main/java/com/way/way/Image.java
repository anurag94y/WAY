package com.way.way;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by anurag.yadav on 3/29/17.
 */

public class Image {

    public Bitmap getImageFromUrl(String imageUrl) throws IOException {
        final URL url = new URL(imageUrl);
        Bitmap bmp = null;
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bmp = null;
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    System.out.println("Image Load SuccessFully : " + bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Image Load UnsuccessFully");
                }
                return bmp;
            }
        }.execute();
        return null;
    }


}
