package com.shubham.findyourway;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class Start extends AppCompatActivity implements LocationListener{
    Button b1,b2;
    public static double lat = 22.6928, lng = 75.8684;

    LocationManager locationManager;
    Location location=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Enable GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
/** Here it's leading to GPS setting options*/
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Start.this.finish();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
        if(isonline()) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(Start.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Start.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Start.this);
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }
            if(location!=null) {
               lat=location.getLatitude();
               lng=location.getLongitude();
                Log.e("lat", ""+location.getLatitude());
                Log.e("lng",""+ location.getLongitude());
            }
        }
    else {
        Toast.makeText(this, "No internet connection..", Toast.LENGTH_LONG).show();
            AlertDialog.Builder alertBuilder= new AlertDialog.Builder(Start.this);
            alertBuilder.setTitle("Kindly turn on internet..");
            alertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    Intent callNetSettingIntent = new Intent(
                            Settings.ACTION_WIFI_SETTINGS);
                    startActivity(callNetSettingIntent);
                }
            });
            alertBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){

                    dialog.cancel();
                    System.exit(1);
                }
            });
            AlertDialog ad=alertBuilder.create();
            ad.show();

            System.exit(1);
    }

    if(lat== 22.6928 && lng == 75.8684){
        Toast.makeText(this, "Unable to fetch your location", Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Maybe due to slow internet connection", Toast.LENGTH_SHORT).show();
    }

            b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.FragmentLayout,new fragone());
                tx.commit();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.FragmentLayout,new fragtwo());
                tx.commit();
            }
        });

    }
    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
        Log.e("method", "latlng");

        Toast.makeText(this, "location avaliable........", Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(Start.this);
        alertBuilder.setTitle("Confirmation Message");
        alertBuilder.setMessage("Do you want to exit?");
        alertBuilder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(Start.this,"Positive Confirmation", Toast.LENGTH_LONG).show();
                System.exit(1);
                dialog.cancel();
            }
        });
        alertBuilder.setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        AlertDialog ad=alertBuilder.create();
        ad.show();

    }
}
