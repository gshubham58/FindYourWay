package com.shubham.findyourway;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity implements LocationListener {
    public static double lat = 22.6928, lng = 75.8684;
    LocationManager locationManager;
    Button nearbybtn, path, geopath;
    EditText e3, e4;
    TextView txtv;
    static double arr3[];
    static double arr4[];
    String geourl, distanceurl, Roadurl;
    String address, distanceString, destinationaddress;
    dismodel a1, a2;
    Directionjson ob1, ob2;
    String response1, response2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (isonline()) {
            nearbybtn = (Button) findViewById(R.id.result);
            geopath = (Button) findViewById(R.id.geopath);
          //  path = (Button) findViewById(R.id.path);
            e3 = (EditText) findViewById(R.id.e3);
            e4 = (EditText) findViewById(R.id.e4);
            txtv = (TextView) findViewById(R.id.textView);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
        } else {
            Toast.makeText(this, "No internet connection, Try again...", Toast.LENGTH_LONG).show();
        }

        nearbybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geourl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("loading geocode...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


                StringRequest stringRequest = new StringRequest(Request.Method.GET, geourl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("main", "135" + geourl);
                                address = LatAddressParser.parseResult(response);
                                progressDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, nearby.class);
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
                        Toast.makeText(MainActivity.this, "No records Found", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(stringRequest);


            }
        });
     /*   path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("loading location...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String a = LatAddressParser.parseResult(response);
                                progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "volley");
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "No records Found", Toast.LENGTH_SHORT).show();
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);


            }
        });
     */
        geopath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e3.getText().length() > 0 && e4.getText().length() > 0) {
                    char arrr1[]=(e3.getText()+"+"+e4.getText()).toCharArray();
                    destinationaddress="";
                    for(char a:arrr1){
                        if(a==' '){
                            destinationaddress+="+";
                        }else{
                            destinationaddress+=a;
                        }
                    }
                    geourl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("loading geocode...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);


                    StringRequest stringRequest = new StringRequest(Request.Method.GET, geourl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("main", "135" + geourl);
                                    address = LatAddressParser.parseResult(response);
                                    progressDialog.dismiss();

                                    distanceurl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + address + "&destination=" + destinationaddress + "&mode=walking&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                    Log.e("abc", distanceurl);
                                    final ProgressDialog progressDialog1 = new ProgressDialog(MainActivity.this);
                                    progressDialog1.setMessage("loading direction...");
                                    progressDialog1.setCancelable(false);
                                    progressDialog1.show();

                                    RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this);
                                    Log.e("main", "157");

                                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, distanceurl,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    ob1 = new Directionjson();
                                                    a1 = ob1.Dparse(response);
                                                    response1 = response;
                                                    progressDialog1.dismiss();

                                                    distanceurl = "https://maps.googleapis.com/maps/api/directions/json?origin=" + destinationaddress + "&destination=" + address + "&mode=walking&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                    Log.e("abcd", distanceurl);
                                                    final ProgressDialog progressDialog2 = new ProgressDialog(MainActivity.this);
                                                    progressDialog2.setMessage("loading direction...");
                                                    progressDialog2.setCancelable(false);
                                                    progressDialog2.show();

                                                    RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this);
                                                    Log.e("main", "157");

                                                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, distanceurl,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    ob2 = new Directionjson();
                                                                    a2 = ob2.Dparse(response);
                                                                    response2 = response;
                                                                    progressDialog2.dismiss();


                                                                    if (a1.getDistance() < a2.getDistance()) {

                                                                        distanceString = ob1.disparse(response1);
                                                                        Log.e("dis", distanceString);
                                                                        Log.e("ln1", "" + a1.getDistance());

                                                                        Roadurl = "https://roads.googleapis.com/v1/snapToRoads?path=" + distanceString + "&interpolate=true&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                                        //String Roadurl = "https://roads.googleapis.com/v1/snapToRoads?path=22.6902136,75.83754170000002|22.6837843,75.8357721|22.6827764,75.8549299|22.6831425,75.8571004|22.6986401,75.877746|22.6929375,75.86830719999999&interpolate=true&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                                        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                                                                        progressDialog.setMessage("loading roadpath...");
                                                                        progressDialog.setCancelable(false);
                                                                        progressDialog.show();

                                                                        RequestQueue queue3 = Volley.newRequestQueue(MainActivity.this);
                                                                        Log.e("main", "157");

                                                                        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, Roadurl,
                                                                                new Response.Listener<String>() {
                                                                                    @Override
                                                                                    public void onResponse(String response) {

                                                                                        Log.e("main", "161");
                                                                                        RoadParse.Rparse(response);
                                                                                        progressDialog.dismiss();
                                                                                        Intent intent = new Intent(MainActivity.this, MapsActivity2.class);
                                                                                        intent.putExtra("object", ob1);
                                                                                        startActivity(intent);

                                                                                    }
                                                                                }, new Response.ErrorListener() {
                                                                            @Override
                                                                            public void onErrorResponse(VolleyError error) {
                                                                                Log.e("error", "volley2");
                                                                                error.printStackTrace();
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(MainActivity.this, "No records Found2", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                        queue3.add(stringRequest3);

                                                                    } else {
                                                                        distanceString = ob2.disparse(response2);
                                                                        Log.e("dis", distanceString);
                                                                        Log.e("ln2", "" + a2.getDistance());

                                                                        Roadurl = "https://roads.googleapis.com/v1/snapToRoads?path=" + distanceString + "&interpolate=true&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                                        //String Roadurl = "https://roads.googleapis.com/v1/snapToRoads?path=22.6902136,75.83754170000002|22.6837843,75.8357721|22.6827764,75.8549299|22.6831425,75.8571004|22.6986401,75.877746|22.6929375,75.86830719999999&interpolate=true&key=AIzaSyDRWyAwiJOnvaxbaz_qXdYODLDpjGj5IEk";
                                                                        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                                                                        progressDialog.setMessage("loading roadpath...");
                                                                        progressDialog.setCancelable(false);
                                                                        progressDialog.show();

                                                                        RequestQueue queue3 = Volley.newRequestQueue(MainActivity.this);
                                                                        Log.e("main", "157");

                                                                        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, Roadurl,
                                                                                new Response.Listener<String>() {
                                                                                    @Override
                                                                                    public void onResponse(String response) {

                                                                                        Log.e("main", "161");
                                                                                        RoadParse.Rparse(response);
                                                                                        progressDialog.dismiss();
                                                                                        Intent intent = new Intent(MainActivity.this, MapsActivity2.class);
                                                                                        intent.putExtra("object", ob2);
                                                                                        startActivity(intent);

                                                                                    }
                                                                                }, new Response.ErrorListener() {
                                                                            @Override
                                                                            public void onErrorResponse(VolleyError error) {
                                                                                Log.e("error", "volley2");
                                                                                error.printStackTrace();
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(MainActivity.this, "No records Found2", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                        queue3.add(stringRequest3);

                                                                    }


                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            Log.e("error", "volley2");
                                                            error.printStackTrace();
                                                            progressDialog2.dismiss();
                                                            Toast.makeText(MainActivity.this, "No records Found2", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    queue2.add(stringRequest2);


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("error", "volley2");
                                            error.printStackTrace();
                                            progressDialog1.dismiss();
                                            Toast.makeText(MainActivity.this, "No records Found2", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    queue1.add(stringRequest1);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("error", "volley");
                            error.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "No records Found", Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(stringRequest);


                } else {
                    e3.setError("Enter valid text");
                    e4.setError("Enter valid text");
                }
            }
        });


    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        Log.e("method", "latlng");
        Toast.makeText(this, "location changed........", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public boolean isonline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setTitle("Confirmation Message");
        alertBuilder.setMessage("Do you want to exit?");
        alertBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(MainActivity.this,"Positive Confirmation", Toast.LENGTH_LONG).show();
                System.exit(0);
                dialog.cancel();
            }
        });
        alertBuilder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(MainActivity.this,"Negative Confirmation",Toast.LENGTH_LONG).show();
                dialog.cancel();
            }
        });
        AlertDialog ad=alertBuilder.create();
        ad.show();

    }
}



