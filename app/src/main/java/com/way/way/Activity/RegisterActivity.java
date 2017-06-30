package com.way.way.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.way.way.R;
import com.way.way.http.HttpCalls;
import com.way.way.pojo.UserDetail;

import org.json.JSONObject;

import static com.way.way.Constants.HOST_IP;
import static com.way.way.Constants.HOST_PORT;

/**
 * Created by anurag.yadav on 4/13/17.
 */

public class RegisterActivity extends Activity{

    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private EditText mobileNumber;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button submit;
    private TextView error;


    boolean isPasswordEqualWithConfirmPassword(final EditText password, final EditText confirmPassword) {
        // TO-DO Need to check on basis of hash value not plain text and will store password hash value.
        return password.getText()
                       .toString()
                       .equals(
                               confirmPassword.getText()
                                              .toString()
                       );
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        emailId = (EditText) findViewById(R.id.emailId);
        mobileNumber = (EditText) findViewById(R.id.mobileNumber);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        submit = (Button) findViewById(R.id.registerSubmit);
        error = (TextView) findViewById(R.id.registerError);

        final AlphaAnimation inAnimation = new AlphaAnimation(0f, 1f);
        final AlphaAnimation outAnimation = new AlphaAnimation(1f, 0f);
        final FrameLayout progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!isPasswordEqualWithConfirmPassword(password, confirmPassword)) {
                    error.setText("Password and Confirm Password should be same !");
                }
                //Todo: Add condition for username Unique, password and emailId
                final UserDetail userDetail = new UserDetail();
                userDetail.setFirstName(firstName.getText().toString());
                userDetail.setLastName(lastName.getText().toString());
                userDetail.setEmailId(emailId.getText().toString());
                userDetail.setMobileNumber(mobileNumber.getText().toString());
                userDetail.setUsername(username.getText().toString());
                userDetail.setPassword(password.getText().toString());
                final String url = "http://" + HOST_IP + "/register";
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
                        return HttpCalls.putHttp(url, new Gson().toJson(userDetail));
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
                            if (jsonObject.getString("status").equals("registered")) {
                                Toast.makeText(RegisterActivity.this, "Congrats: Regitered Successfully", Toast.LENGTH_LONG).show();
                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(loginIntent);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Unable to Register", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(response);
                            Toast.makeText(RegisterActivity.this, "Network Error Occurred", Toast.LENGTH_LONG).show();
                        }
                    }

                }.execute();
            }
        });
    }
}
