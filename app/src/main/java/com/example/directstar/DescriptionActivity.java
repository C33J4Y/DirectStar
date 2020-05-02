package com.example.directstar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

public class DescriptionActivity extends FragmentActivity implements OnMapReadyCallback {
    //Create GoogleMap object
    GoogleMap map;

    //Global Variables to hold Lat and Lon
    Double globalLat;
    Double globalLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        String description = intent.getStringExtra(searchResultActivity.EXTRA_MESSAGE3);
        Double lat = intent.getExtras().getDouble(searchResultActivity.EXTRA_MESSAGE4);
        Double lon = intent.getExtras().getDouble(searchResultActivity.EXTRA_MESSAGE5);

        globalLat = lat;
        globalLon = lon;


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng TutorialsPoint = new LatLng(globalLat, globalLon);
        map.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Tutorialspoint.com"));
        map.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }

}
