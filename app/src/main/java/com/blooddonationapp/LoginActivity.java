package com.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blooddonationapp.utils.Utils;
import com.blooddonationapp.utils.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

//     String strInputEmail;
    TextInputEditText inputEmail, inputPassword;
    private static String strInputEmail,strInputPassword;
    Button btnLogin;
    TextView tvSignup;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.loginButton);
        tvSignup = findViewById(R.id.tvSignup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, DonorRegistrationActivity.class);
                startActivity(i);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 strInputEmail = inputEmail.getText().toString();
                 strInputPassword = inputPassword.getText().toString();
                Log.e(strInputEmail,strInputPassword);
                String err = "";
                boolean isError = false;
                if (strInputEmail.trim().length() == 0) {
                    isError = true;
                    err = err + "Please Enter Email";
                }
                if (strInputPassword.trim().length() == 0) {
                    isError = true;
                    err = err + "Please Enter Password";
                }

                if (isError == true) {
                    Toast.makeText(LoginActivity.this, "Please Enter The Credentials.", Toast.LENGTH_SHORT).show();
                } else {

                    if (strInputEmail.equals("admin@gmail.com") || strInputPassword.equals("Admin@123")) {
                        Intent i = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        startActivity(i);
                    } else {

                        GetDonorsApi();
                    }
                }
            }
        });
    }

    private void GetDonorsApi() {
        ArrayList<DonorLangModel> arrayList = new ArrayList<DonorLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.SESSION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.e("Donors Response: ",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strUserName = jsonObject1.getString("userName");
                        String strEmail = jsonObject1.getString("email");
                        String strPassword = jsonObject1.getString("password");

                        Log.e("strPassword: ",strPassword);
                        Log.e("inputPassword",strInputPassword+"");
                        if (strEmail.equals(strInputEmail)){
                            Log.e("checking","email");
                            if (strPassword.equals(strInputPassword)) {
                                Log.e("checking","email");
                                loginApi(strInputEmail, strInputPassword);
                                Intent intent = new Intent(LoginActivity.this, DonorDashboardActivity.class);
                                startActivity(intent);
                                Toast.makeText(LoginActivity.this, "Login Done Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Please Enter Valid Credentials.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", String.valueOf(error));
            }
        });

        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);


    }


    private void loginApi(String strEmail, String strPassword) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Intent i = new Intent(LoginActivity.this, DonorDashboardActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("email", strEmail);
                map.put("password", strPassword);

                return map;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public static String[] getLoginCredentials()
    {
        String[] data={strInputEmail,strInputPassword};
//        Log.e(strInputEmail,strInputPassword);
       return data;
    }
}
