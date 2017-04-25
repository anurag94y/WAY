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
import com.way.way.http.HttpCalls;

import org.json.JSONObject;

import static com.way.way.Constants.HOST_IP;
import static com.way.way.Constants.HOST_PORT;

/**
 * Created by anurag.yadav on 4/13/17.
 */

public class FirstPageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_page);
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
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = null;
                try {
                    cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String contact_id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                        if (hasPhoneNumber > 0) {
                            Cursor phone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " - ? ", new String[]{name}, null);
                            while (phone.moveToNext()) {
                                String phoneNumber = phone.getString(phone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                System.out.println(contact_id + " " + name + " " + phoneNumber);
                            }
                            phone.close();
                        }


                    }
                    cursor.close();

                }
                //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                //startActivityForResult(intent, 1);
//                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(loginIntent);
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
