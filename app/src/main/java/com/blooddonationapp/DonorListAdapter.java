package com.blooddonationapp;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.blooddonationapp.utils.Utils;
import com.blooddonationapp.utils.VolleySingleton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DonorListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonorLangModel> donorLangModelArrayList;

    public DonorListAdapter(Context context, ArrayList<DonorLangModel> donorLangModelArrayList) {
        this.context = context;
        this.donorLangModelArrayList = donorLangModelArrayList;
    }

    @Override
    public int getCount() {
        return donorLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return donorLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.donor_table, null);

        TextView tv_Uname, tv_cntNo, tvBtype, tvEmail, tvPassword;
        tv_Uname = view.findViewById(R.id.uname);
        tv_cntNo = view.findViewById(R.id.tv_cntNo);
        tvBtype = view.findViewById(R.id.tv_btype);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPassword = view.findViewById(R.id.tv_pwd);

        tv_Uname.setText(donorLangModelArrayList.get(position).getUserName());
        tv_cntNo.setText(donorLangModelArrayList.get(position).getContactNo());
        tvBtype.setText(donorLangModelArrayList.get(position).getBloodType());
        tvEmail.setText(donorLangModelArrayList.get(position).getEmail());
        tvPassword.setText(donorLangModelArrayList.get(position).getPassword());

        String uname=donorLangModelArrayList.get(position).getUserName();
        String contactNo=donorLangModelArrayList.get(position).getContactNo();
        String bType=donorLangModelArrayList.get(position).getBloodType();
        String email=donorLangModelArrayList.get(position).getEmail();
        String password=donorLangModelArrayList.get(position).getPassword();
        ImageView
                imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        CheckBox cb_donation=view.findViewById(R.id.checkbox);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        System.out.println("Current time => " + formattedDate);


        cb_donation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    cb_donation.
                    addApi(uname,contactNo,bType,formattedDate,email,password);
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = donorLangModelArrayList.get(position).get_id();
                Log.e("id in edit-adapter: ", id);

                Intent intent = new Intent(context, DonorUpdateActivity.class);
                intent.putExtra("DONOR_ID", id);
                intent.putExtra("USERNAME", donorLangModelArrayList.get(position).getUserName());
                intent.putExtra("CONTACT_NO", donorLangModelArrayList.get(position).getContactNo());
                intent.putExtra("BLOOD_TYPE", donorLangModelArrayList.get(position).getBloodType());
                intent.putExtra("EMAIL", donorLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD", donorLangModelArrayList.get(position).getPassword());

                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = donorLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ", id);

                Intent intent = new Intent(context, DonorUpdateActivity.class);
                intent.putExtra("DONOR_ID", id);

                context.startActivity(intent);
            }
        });

        return view;
    }

    private void addApi(String uname,String contactNo,String bType,String date, String email,String password) {
        Log.e("api calling", "done" + uname + contactNo + bType + date + email + password);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.HISTORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("userName", uname);
                hashMap.put("contactNo", contactNo);
                hashMap.put("bloodType", bType);
                hashMap.put("email", email);
                hashMap.put("password", password);
                hashMap.put("date",date);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

}
