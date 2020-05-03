package com.example.directstar;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

public class DescriptionActivity extends FragmentActivity implements OnMapReadyCallback {
    //Create GoogleMap object
    GoogleMap map;
    public static final String EXTRA_MESSAGE6 = "Venue to Favorites"; //Extra message to pass location

    //Global Variables to store Lat and Lon
    Double globalLat;
    Double globalLon;
    String globalDescription;

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
        globalDescription = description;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(description);

        //Select Favorites button and set onclick listener to start new intent
        Button favoritesButton = (Button) findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, FavoritesActivity.class);
                intent.putExtra(EXTRA_MESSAGE6, globalDescription);
                //Toast.makeText(DescriptionActivity.this, globalDescription, Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        // Add a marker and move the camera to lat and lon given by API
        LatLng venueLocation = new LatLng(globalLat, globalLon);
        map.addMarker(new
                MarkerOptions().position(venueLocation));
        map.moveCamera(CameraUpdateFactory.newLatLng(venueLocation));
    }

}
