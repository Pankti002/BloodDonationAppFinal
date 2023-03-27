//package com.blooddonationapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//
//public class LoginListAdapter extends BaseAdapter {
//    Context context;
//    ArrayList<LoginLangModel> loginLangModelArrayList;
//
//    public LoginListAdapter(Context context, ArrayList<LoginLangModel> loginLangModelArrayList)
//    {
//        this.context=context;
//        this.loginLangModelArrayList=loginLangModelArrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return loginLangModelArrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return loginLangModelArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        return null;
//    }
//}
