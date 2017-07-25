package com.shubham.findyourway;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;


import static com.shubham.findyourway.MainActivity.arr3;
import static com.shubham.findyourway.MainActivity.arr4;
import static com.shubham.findyourway.MainActivity.lat;
import static com.shubham.findyourway.MainActivity.lng;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Directionjson obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        obj=(Directionjson)getIntent().getSerializableExtra("object");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
     public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(lat,lng);
        float zoomlevel=18;
      //  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("First Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoomlevel));
       /* mMap.addPolyline(new PolylineOptions().add(
                latLng1,
                new LatLng(22.6977386,75.8824149),
                new LatLng(22.698838,75.8780404),
                new LatLng(22.7036063,75.8734144),
                new LatLng(22.6979909,75.86480329999999),
                new LatLng(22.6963917,75.8656436)

                ).width(10).color(Color.RED));
       } */
        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.arr1[0],obj.arr2[0]),
                new LatLng(arr3[0],arr4[0])
        ).width(10).color(Color.BLUE));
        for(int i=0;i<arr3.length-1;i++)
        {
            mMap.addPolyline(new PolylineOptions().add(
                    // new LatLng(arr1[i],arr2[i]),
                    //new LatLng(arr1[i+1],arr2[i+1])
                    //new LatLng(22.6974531,75.87670899999999)
                    new LatLng(arr3[i],arr4[i]),

                    new LatLng(arr3[i+1],arr4[i+1])

            ).width(10).color(Color.BLUE));
        }


        mMap.addPolyline(new PolylineOptions().add(
                new LatLng(obj.arr1[obj.arr1.length-1],obj.arr2[obj.arr1.length-1]),
                new LatLng(arr3[arr3.length-1],arr4[arr3.length-1])
        ).width(10).color(Color.BLUE));


    }


}

