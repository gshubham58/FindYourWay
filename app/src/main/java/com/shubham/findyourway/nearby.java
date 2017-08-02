package com.shubham.findyourway;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class nearby extends AppCompatActivity {
    Spinner spnnr;
    Button btn;
    String item, url;
    String lat;
    String lng;
    ListView lst;
    ArrayList<model> arrayList = new ArrayList<>();
    String geourl, distanceurl, Roadurl;
    String address, distanceString, destinationaddress;
    dismodel a1;
    Directionjson ob1;
    static double a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        spnnr = (Spinner) findViewById(R.id.spinner2);
        btn = (Button) findViewById(R.id.button2);
        lst = (ListView) findViewById(R.id.listvw);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = spnnr.getSelectedItem().toString();
                Intent i = getIntent();
                lat = i.getStringExtra("lat");
                lng = i.getStringExtra("lng");
                address = i.getStringExtra("address");
                url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + lat + "," + lng + "&radius=500&type=" + item + "&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                final ProgressDialog progressDialog = new ProgressDialog(nearby.this);
                progressDialog.setMessage("loading data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                RequestQueue queue = Volley.newRequestQueue(nearby.this);


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                arrayList = nameJsonParse.parse(response);
                                progressDialog.dismiss();
                                Log.e("no of items", "" + arrayList.size());
                                if (arrayList.size() == 0) {
                                    Toast.makeText(nearby.this, "No Results", Toast.LENGTH_SHORT).show();
                                } else {
                                    customadapter cadapter = new customadapter(nearby.this, arrayList);
                                    lst.setAdapter(cadapter);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "volley");
                        progressDialog.dismiss();
                        Toast.makeText(nearby.this, "Internet connection Error", Toast.LENGTH_SHORT).show();
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                a= Double.parseDouble(arrayList.get(position).getLatit());
                b= Double.parseDouble(arrayList.get(position).getLongit());

                geourl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + arrayList.get(position).getLatit() + "," + arrayList.get(position).getLongit() + "&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";

                final ProgressDialog progressDialog = new ProgressDialog(nearby.this);
                progressDialog.setMessage("Loading Address...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                RequestQueue queue = Volley.newRequestQueue(nearby.this);


                StringRequest stringRequest = new StringRequest(Request.Method.GET, geourl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("main", "135" + geourl);
                                destinationaddress = LatAddressParser.parseResult(response);
                                progressDialog.dismiss();

                                distanceurl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + address + "&destination=" + destinationaddress + "&mode=walking&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                Log.e("abc", distanceurl);
                                final ProgressDialog progressDialog2 = new ProgressDialog(nearby.this);
                                progressDialog2.setMessage("Loading Direction...");
                                progressDialog2.setCancelable(false);
                                progressDialog2.show();

                                RequestQueue queue2 = Volley.newRequestQueue(nearby.this);

                                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, distanceurl,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                ob1 = new Directionjson();
                                                a1 = ob1.Dparse(response);
                                                distanceString = ob1.disparse(response);
                                                progressDialog2.dismiss();

                                                Roadurl = "https://roads.googleapis.com/v1/snapToRoads?path=" + distanceString + "&interpolate=true&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                final ProgressDialog progressDialog = new ProgressDialog(nearby.this);
                                                progressDialog.setMessage("Generating Path...");
                                                progressDialog.setCancelable(false);
                                                progressDialog.show();
                                                Log.e("road",Roadurl);
                                                RequestQueue queue2 = Volley.newRequestQueue(nearby.this);
                                                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, Roadurl,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {

                                                                RoadParse.Rparse(response);
                                                                progressDialog.dismiss();

                                                                Intent intent1 = new Intent(nearby.this, MapsActivity2.class);
                                                                intent1.putExtra("object", ob1);
                                                                PendingIntent pendingIntent = PendingIntent.getActivity(nearby.this, 876767877, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                                                                Notification notification = new Notification.Builder(nearby.this).setContentText(a1.getDistvalue()).setSmallIcon(R.drawable.icon).setContentTitle("The distance is:").setContentIntent(pendingIntent).setAutoCancel(true).build();
                                                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                                                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                                                                notificationManager.notify(0, notification);
                                                                Intent intent = new Intent(nearby.this, MapsActivity2.class);
                                                                intent.putExtra("object", ob1);
                                                                startActivity(intent);

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Log.e("error", "volley2");
                                                        error.printStackTrace();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(nearby.this, "Internet connection Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                queue2.add(stringRequest2);

                                            }

                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("error", "volley2");
                                        error.printStackTrace();
                                        progressDialog2.dismiss();
                                        Toast.makeText(nearby.this, "Internet connection Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                queue2.add(stringRequest2);


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "volley");
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(nearby.this, "Internet connection Error", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);

            }
        });
    }

}

