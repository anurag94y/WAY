package com.way.way.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.way.way.R;
import com.way.way.helper.SessionManagement;
import com.way.way.http.HttpCalls;

import org.json.JSONObject;

import static com.way.way.Constants.*;

/**
 * Created by anurag.yadav on 4/13/17.
 */

public class LoginActivity extends Activity {

    SessionManagement session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        session = new SessionManagement(getApplicationContext());
        final EditText editTextUserName = (EditText) findViewById(R.id.editTextUserNameToLogin);
        final EditText editTextPassword = (EditText) findViewById(R.id.editTextPasswordToLogin);
        final AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        final AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);

        final FrameLayout progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        Button btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        System.out.println("Frame Layout-> " + progressBarHolder);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                final String userName = editTextUserName.getText().toString();
                final String password = editTextPassword.getText().toString();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        inAnimation.setDuration(200);
                        progressBarHolder.setAnimation(inAnimation);
                        progressBarHolder.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected String doInBackground(Void... params) {
                        return HttpCalls.getHttp("http://" + HOST_IP + ":" + HOST_PORT + "/login?username=" + userName + "&password=" + password);
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        super.onPostExecute(response);
                        outAnimation.setDuration(200);
                        progressBarHolder.setAnimation(outAnimation);
                        progressBarHolder.setVisibility(View.GONE);
                        System.out.println("Http Response-> " + response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("Logged")) {
                                session.CreateLoginSession(userName, "8958807064");
                                Toast.makeText(LoginActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                                Intent mainActIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainActIntent);
                            } else {
                                Toast.makeText(LoginActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(response);
                            Toast.makeText(LoginActivity.this, "Network Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    }

                }.execute();
            }
        });
    }
}
