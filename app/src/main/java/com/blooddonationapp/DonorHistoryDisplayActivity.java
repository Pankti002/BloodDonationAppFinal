package com.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class DonorHistoryDisplayActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_history_display);
        listview = findViewById(R.id.ls_listview);

        GetDonorsApi();
    }

    private void GetDonorsApi() {
        ArrayList<DonorHistoryLangModel> arrayList = new ArrayList<DonorHistoryLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Utils.HISTORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                        String strDate=jsonObject1.getString("date");



                        DonorHistoryLangModel donorHistoryLangModel = new DonorHistoryLangModel();
                        donorHistoryLangModel.set_id(strDonorId);
                        donorHistoryLangModel.setUserName(strUserName);
                        donorHistoryLangModel.setContactNo(strContactNo);
                        donorHistoryLangModel.setBloodType(strBtype);
                        donorHistoryLangModel.setDate(strDate);
                        donorHistoryLangModel.setEmail(strEmail);
                        donorHistoryLangModel.setPassword(strPassword);
                        arrayList.add(donorHistoryLangModel);

                    }
                    DonorHistoryListAdapter donorHistoryListAdapter = new DonorHistoryListAdapter(DonorHistoryDisplayActivity.this, arrayList);
                    listview.setAdapter(donorHistoryListAdapter);
                    donorHistoryListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        VolleySingleton.getInstance(DonorHistoryDisplayActivity.this).addToRequestQueue(stringRequest);
    }
}