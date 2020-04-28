package com.example.directstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class searchResultActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String keyword = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);

        final TextView searchResultView = (TextView) findViewById(R.id.searchResultView);

        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.eventful.com/json/venues/search?app_key=62KpsJNvpqFdhZnr&keywords=" + keyword + "&location=Boynton&within=10&units=mi&sort_order=popularity";

        // Request a json response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //searchResultView.setText(response.toString());
                        displaySearchResults(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        searchResultView.setText(error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    //Function to parse json data
    public void displaySearchResults(JSONObject response) {
        String searchResults;
        String CRLF = "\n";
        int i = 0;


        // Capture the layout's TextView and set the string as its text
        final TextView searchResultView = findViewById(R.id.searchResultView);

        try {
            JSONObject venues = response.getJSONObject("venues");
            JSONArray venueArray = venues.getJSONArray("venue");


                // TODO: ADD FOR loop to iterate through each object in the array to display multiple results
                for (i = 0; i < venueArray.length(); i++) {
                    JSONObject venuePart = venueArray.getJSONObject(i);
                    String venueName = venuePart.getString("venue_name");
                    String address = venuePart.getString("address");
                    String cityName = venuePart.getString("city_name");
                    String state = venuePart.getString("region_name");
                    String venueType = venuePart.getString("venue_type");
                    searchResults = "Venue Name: " + venueName + CRLF +
                            "Address: " + address + CRLF +
                            "City: " + cityName + CRLF +
                            "State: " + state + CRLF +
                            "Venue Type:\t" + venueType;

                    searchResultView.setText(searchResults);
                }
        } catch (JSONException e) {
            //e.printStackTrace();
            //searchResultView.setText(e.toString());
            // TODO: Add IF statements to let users know if search returned NO RESULTS
            searchResultView.setText("No Results Found!");
        }




    }

}


