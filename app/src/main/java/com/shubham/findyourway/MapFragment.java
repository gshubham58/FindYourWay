package com.shubham.findyourway;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by hp on 16-Jul-17.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap gmap;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gmap.setMyLocationEnabled(true);
        gmap.setTrafficEnabled(true);
        gmap.setIndoorEnabled(true);
        gmap.setBuildingsEnabled(true);
        gmap.getUiSettings().setZoomControlsEnabled(true);
        LatLng latLng=new LatLng(MainActivity.lat,MainActivity.lng);
        gmap.addMarker(new MarkerOptions().position(new LatLng(MainActivity.lat,MainActivity.lng)));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        gmap.getMaxZoomLevel();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mapfrag,container,false);
        mapView=(MapView)view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());
        if(mapView !=null){
            mapView.getMapAsync(this);
        }

        return view;
    }
}
