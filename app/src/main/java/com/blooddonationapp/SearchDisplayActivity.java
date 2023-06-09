package com.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blooddonationapp.utils.Utils;
import com.blooddonationapp.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchDisplayActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        listview = findViewById(R.id.ls_User_listview);

        GetDonorsApi();

    }
    private void GetDonorsApi() {
        ArrayList<PersonalLangModel> arrayList = new ArrayList<PersonalLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.DONOR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "onResponse:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strDonorId = jsonObject1.getString("_id");
                        String strUserName = jsonObject1.getString("userName");
                        String strContactNo = jsonObject1.getString("contactNo");
                        String strEmail = jsonObject1.getString("email");
                        String strPassword = jsonObject1.getString("password");
                        String strBtype = jsonObject1.getString("bloodType");

                        String strBloodGrpSelected=SearchActivity.getBloodGroup();
                        Log.e("In Display Activity"+strBloodGrpSelected,strBtype);

                        if(strBtype.equals(strBloodGrpSelected)) {
                            Log.e("inside if",strBloodGrpSelected+" "+strBtype);
                            PersonalLangModel personalLangModel = new PersonalLangModel();
                            personalLangModel.set_id(strDonorId);
                            personalLangModel.setUserName(strUserName);
                            personalLangModel.setContactNo(strContactNo);
                            personalLangModel.setBloodType(strBtype);
                            personalLangModel.setEmail(strEmail);
                            personalLangModel.setPassword(strPassword);
                            arrayList.add(personalLangModel);
                        }
                    }
                    PersonalListAdapter personalListAdapter = new PersonalListAdapter(SearchDisplayActivity.this, arrayList);
                    listview.setAdapter(personalListAdapter);
                    personalListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        VolleySingleton.getInstance(SearchDisplayActivity.this).addToRequestQueue(stringRequest);


    }


}