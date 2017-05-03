package com.way.way.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.way.way.R;
import com.way.way.helper.SessionManagement;
import com.way.way.http.HttpCalls;

import org.json.JSONObject;

import static com.way.way.Constants.HOST_IP;
import static com.way.way.Constants.HOST_PORT;

/**
 * Created by anurag.yadav on 4/13/17.
 */

public class FirstPageActivity extends Activity {

    SessionManagement session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
        session = new SessionManagement(getApplicationContext());
        System.out.println("Logged in -> " + session.isLoggedIn());
        System.out.println("Logged in -> " + session.getUserDetails().get(SessionManagement.KEY_USERNAME));
        /*if (session.isLoggedIn()) {
            Intent mainActIntent = new Intent(getApplicationContext(), MainActivity.class);
            mainActIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainActIntent);
        }*/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int height15 = (int) (height * 0.12);
        int width50 = (int) (width * 0.5);
        ImageView imageView = (ImageView) findViewById(R.id.first_page_image);
        imageView.setImageResource(R.drawable.friends_logo);
        imageView.getLayoutParams().height = height - height15;
        imageView.getLayoutParams().width = width;

        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);
        login.getLayoutParams().height = height15;
        login.getLayoutParams().width = width50;
        register.getLayoutParams().height = height15;
        register.getLayoutParams().width = width50;

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                //startActivityForResult(intent, 1);
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactdata = data.getData();
                Cursor c = getContentResolver().query(contactdata, null, null, null, null);
                if (c.moveToFirst()) {
                    String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                    System.out.println("$$$$ name " + name);
                }
            }
        }
    }
}
