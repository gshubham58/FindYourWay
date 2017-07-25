package com.shubham.findyourway;

import android.app.ListActivity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hp on 18-Jul-17.
 */

public class customadapter extends BaseAdapter{
    LayoutInflater inflater;
    ArrayList<model>list;
    public customadapter(Context context, ArrayList<model> arrayList){
        list=arrayList;
        inflater=( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    public class Holder{
        TextView tv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View view;
        view=inflater.inflate(R.layout.row,null);
        holder.tv= (TextView)view.findViewById(R.id.textView2);
        holder.tv.setText(list.get(position).getName());
        return view;


    }

}
