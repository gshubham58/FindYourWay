package com.shubham.findyourway;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class fragone extends Fragment {
    EditText e3,e4;
    Button route;
    TextView txtv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.row,container,false);
        Intent intent=new Intent(view.getContext(),MainActivity.class);
        startActivity(intent);
        return view;
    }
}
