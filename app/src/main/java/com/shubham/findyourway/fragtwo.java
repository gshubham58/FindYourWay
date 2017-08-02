package com.shubham.findyourway;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.shubham.findyourway.Start.lat;
import static com.shubham.findyourway.Start.lng;

public class fragtwo extends Fragment {

    View v;
    String geourl,address;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.row,container,false);
        geourl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";

        final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Loading Current Address...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(v.getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.GET, geourl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("main", "135" + geourl);
                        address = LatAddressParser.parseResult(response);
                        progressDialog.dismiss();
                        Intent intent = new Intent(v.getContext(), nearby.class);
                        intent.putExtra("lat", "" + lat);
                        intent.putExtra("lng", "" + lng);
                        intent.putExtra("address", address);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "volley");
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(v.getContext(), "No records Found", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);


    return v;
    }
}
