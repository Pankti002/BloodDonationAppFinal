package com.blooddonationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorHistoryListAdapter extends BaseAdapter {

    Context context;
    ArrayList<DonorHistoryLangModel> donorHistoryLangModelArrayList;

    public DonorHistoryListAdapter(Context context,ArrayList<DonorHistoryLangModel> donorHistoryLangModelArrayList){
            this.context=context;
            this.donorHistoryLangModelArrayList=donorHistoryLangModelArrayList;
    }



    @Override
    public int getCount() {
        return donorHistoryLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return donorHistoryLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.donor_history_table, null);

        TextView tv_Uname, tv_cntNo, tvBtype, tvDate, tvEmail, tvPassword;
        tv_Uname = view.findViewById(R.id.uname);
        tv_cntNo = view.findViewById(R.id.tv_cntNo);
        tvBtype = view.findViewById(R.id.tv_btype);
        tvDate=view.findViewById(R.id.tv_donationDate);
        tvEmail = view.findViewById(R.id.tv_email);
        tvPassword = view.findViewById(R.id.tv_pwd);

        tv_Uname.setText(donorHistoryLangModelArrayList.get(position).getUserName());
        tv_cntNo.setText(donorHistoryLangModelArrayList.get(position).getContactNo());
        tvBtype.setText(donorHistoryLangModelArrayList.get(position).getBloodType());
       tvDate.setText(donorHistoryLangModelArrayList.get(position).getDate());
        tvEmail.setText(donorHistoryLangModelArrayList.get(position).getEmail());
        tvPassword.setText(donorHistoryLangModelArrayList.get(position).getPassword());

        return view;
    }
}
