package com.example.helloapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import app.AppController;

public class SignupActivity extends AppCompatActivity {
    public TextView log;
    public EditText editpass, editemail, editphone;
    public Button sign;
    ProgressBar progressBar;
    SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        session = new SessionManagement(getApplicationContext());
        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        if (session.isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }


        log=findViewById(R.id.login);
        sign=findViewById(R.id.buttonContinue);
        editpass=findViewById(R.id.editTextPassword);
        editphone=findViewById(R.id.editTextPhone);
        editemail=findViewById(R.id.editTextEmail);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent si=new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(si);
            }
        });

    }


    private void registerUser() {
        final String email = editemail.getText().toString().trim();
        final String password = editpass.getText().toString().trim();
        final String phone = editphone.getText().toString().trim();




        //first we will do the validations


       /* if (editpass.getText() != editrepass.getText()){
            editpass.getText().clear();
            editrepass.getText().clear();
        }
*/

        if (TextUtils.isEmpty(email)) {
            editemail.setError("Please enter your email");
            editemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("Enter a valid email");
            editemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            editphone.setError("Please enter your phone number");
            editphone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editpass.setError("Enter a password");
            editpass.requestFocus();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("Done"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object

                                //storing the user in shared preferences
                                session.createLoginSession(email, phone,password);
                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override

                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);

    }



}
