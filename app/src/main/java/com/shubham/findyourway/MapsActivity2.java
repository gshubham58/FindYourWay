package com.shubham.findyourway;

import android.Manifest;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import static com.shubham.findyourway.MainActivity.arr3;
import static com.shubham.findyourway.MainActivity.arr4;
import static com.shubham.findyourway.Start.lat;
import static com.shubham.findyourway.Start.lng;
import static com.shubham.findyourway.nearby.a;
import static com.shubham.findyourway.nearby.b;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Directionjson obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        obj = (Directionjson) getIntent().getSerializableExtra("object");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(a, b);
        float zoomlevel = 18;
        mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[0], arr4[0])).title("Start"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[arr3.length-1], arr4[arr4.length-1])).title("Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomlevel));
/*
       */
/*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
       *//*
*/
/* mMap.addPolyline(new PolylineOptions().add(
                latLng1,
                new LatLng(22.6977386,75.8824149),
                new LatLng(22.698838,75.8780404),
                new LatLng(22.7036063,75.8734144),
                new LatLng(22.6979909,75.86480329999999),
                new LatLng(22.6963917,75.8656436)

                ).width(10).color(Color.RED));
       } *//*

       mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.obj.arr1[0],obj.obj.arr2[0]),
                new LatLng(arr3[0],arr4[0])
        ).width(10).color(Color.BLUE).jointType(0));
        for(int i=0;i<arr3.length-1;i++)
        {
            mMap.addPolyline(new PolylineOptions().add(
                    // new LatLng(obj.arr1[i],obj.arr2[i]),
                    //new LatLng(obj.arr1[i+1],obj.arr2[i+1])
                    //new LatLng(22.6974531,75.87670899999999)
                    new LatLng(arr3[i],arr4[i]),

                    new LatLng(arr3[i+1],arr4[i+1])

            ).width(10).color(Color.BLUE).jointType(0));
        }


        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.obj.arr1[obj.obj.arr1.length-1],obj.obj.arr2[obj.obj.arr1.length-1]),
                new LatLng(arr3[arr3.length-1],arr4[arr3.length-1])
        ).width(10).color(Color.BLUE).jointType(2));






*/


int i=0;
        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.arr1[0], obj.arr2[0]),
                new LatLng(arr3[0], arr4[0])
        ).width(10).color(Color.RED));
        mMap.addMarker(new MarkerOptions().position(new LatLng(obj.arr1[0], obj.arr2[0])).title("ssss11" + obj.arr1[0] + "," + obj.arr2[0]));
        mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[0], arr4[0])).title("ssss22" + arr3[0] + "," + arr4[0]));
        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(lat, lng),
                new LatLng(obj.arr1[0], obj.arr2[0])
        ).width(10).color(Color.RED));
        mMap.addMarker(new MarkerOptions().position(new LatLng(obj.arr1[0], obj.arr2[0])).title("ssss11" + obj.arr1[0] + "," + obj.arr2[0]));
        mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[0], arr4[0])).title("ssss22" + arr3[0] + "," + arr4[0]));
        for (i = 0; i < arr3.length - 1; i++) {
            double t = arr3[i] - a;
            double t1 = arr4[i] - b;
            double t3 = t1 + t;
           /*if(t<0)
           {
               t=t*-1;
           }if(t1<0)
           {
               t1=t1*-1;
           }*/
            //if(t>0.0001&&t1>0.0001){
            if (t3 < 0) {
                t3 = t3 * -1;
            }
            if (t3 > 0.0001) {
                mMap.addPolyline(new PolylineOptions().add(
                        new LatLng(arr3[i], arr4[i]),
                        new LatLng(arr3[i + 1], arr4[i + 1])
                ).width(10).color(Color.RED));
                mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[i], arr4[i])).title("" + arr3[i] + "," + arr4[i]));
                mMap.addMarker(new MarkerOptions().position(new LatLng(arr3[i + 1], arr4[i + 1])).title("" + arr3[i + 1] + "," + arr4[i + 1]));
            } else {
                mMap.addPolyline(new PolylineOptions().add(new LatLng(arr3[i], arr4[i]), latLng).width(10).color(Color.RED));
                break;
            }

        }
        if (i == arr3.length - 1) {
            mMap.addPolyline(new PolylineOptions().add(new LatLng(arr3[i], arr4[i]), latLng).width(10).color(Color.RED));
        }
/*

        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.arr1[obj.arr1.length-1],obj.arr2[obj.arr1.length-1]),
                new LatLng(arr3[arr3.length-1],arr4[arr3.length-1])
        ).width(10).color(Color.RED));
        mMap.addMarker(new MarkerOptions().position(new LatLng(obj.arr1[obj.arr1.length-1],obj.arr2[obj.arr1.length-1])).title("dddd11"+obj.arr1[obj.arr1.length-1]+","+obj.arr2[obj.arr1.length-1]));
        mMap.addMarker(new MarkerOptions().position(new LatLng(obj.arr1[obj.arr1.length-1],obj.arr2[obj.arr1.length-1])).title("dddd22"+arr3[arr3.length-1]+","+arr4[arr3.length-1]));
*/

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        this.finish();
    }
}

