package com.way.way.http;

import com.google.gson.Gson;
import com.way.way.pojo.UserDetail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;



/**
 * Created by anurag.yadav on 4/13/17.
 */

public class HttpCalls {

    public static String getHttp(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            //Log.d("InputStream", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static String putHttp(String url, String content) {
        DefaultHttpClient client = new DefaultHttpClient();
        InputStream inputStream = null;
        String result = "";
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new StringEntity(content));
            post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(post);
            inputStream = response.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return result;
    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static void test() {

    }
}

