package com.example.directstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    public static final String EXTRA_MESSAGE3 = "Description String";
    public static final String EXTRA_MESSAGE4 = "Latitude Array";
    public static final String EXTRA_MESSAGE5 =  "Longitude Array";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // Get the Intent that started this activity and extract the string

        Intent intent = getIntent();
        String keyword = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);
        String location = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE2);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.eventful.com/json/venues/search?app_key=62KpsJNvpqFdhZnr&keywords=" + keyword + "&location=" + location + "&within=10&units=mi&sort_order=popularity";

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
                        //searchResultView.setText(error.toString());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    //Function to parse json data
    public void displaySearchResults(JSONObject response) {
        String[] searchResults = new String[10];
        Double[] latArray = new Double[10];
        Double[] lonArray = new Double[10];
        String CRLF = "\n";
        int i = 0;

        try {
            JSONObject venues = response.getJSONObject("venues");
            JSONArray venueArray = venues.getJSONArray("venue");

            // FOR loop to iterate through each object in the array to display multiple results
            for (i = 0; i < venueArray.length(); i++) {
                JSONObject venuePart = venueArray.getJSONObject(i);
                String venueName = venuePart.getString("venue_name");
                String address = venuePart.getString("address");
                String cityName = venuePart.getString("city_name");
                String state = venuePart.getString("region_name");
                String venueType = venuePart.getString("venue_type");
                Double lat = venuePart.getDouble("latitude");
                Double lon = venuePart.getDouble("longitude");
                //latArray[i] = lat;
                //lonArray[i] = lon;

                searchResults[i] = "Venue Name: " + venueName + CRLF +
                        "Address: " + address + CRLF +
                        "City: " + cityName + CRLF +
                        "State: " + state + CRLF +
                        "Lat:\t" + lat +"Lon:\t" + lon + CRLF + CRLF;
                        //"Venue Type:\t" + venueType + CRLF + CRLF;

                //Toast.makeText(searchResultActivity.this,searchResults[i],Toast.LENGTH_LONG).show();

                //searchResultView.setText(searchResults);

                //sendCoordinates(latArray, lonArray);
//                Intent intent = new Intent(searchResultActivity.this, DescriptionActivity.class);
//                intent.putExtra(EXTRA_MESSAGE4, lat);
//                intent.putExtra(EXTRA_MESSAGE5, lon);
                //startActivity(intent);


            }
        } catch (JSONException e) {
            //e.printStackTrace();
            //searchResultView.setText(e.toString());
            // Handles error when JSONObject is NULL
            //Toast.makeText(searchResultActivity.this,"No Results Found. Try Again!",Toast.LENGTH_LONG).show();
        }

        populateSearchResultView(searchResults);
    }

    //Function takes in results from API as a String array, copies to a new array and feeds the adapter
    public void populateSearchResultView(String[] searchResults){
        int arrayLength = searchResults.length;
        final String[] displayArray = new String[arrayLength];

        for (int j = 0; j < arrayLength; j++) {
            displayArray[j] = searchResults[j];
        }

        ListView listView = (ListView) findViewById(R.id.searchResultView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(searchResultActivity.this,android.R.layout.simple_list_item_1,displayArray);
        listView.setAdapter(adapter);

        //To make ListView clickable
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int j, long id) {

                //Toast.makeText(searchResultActivity.this, displayArray[j], Toast.LENGTH_LONG).show();
                Intent intent = new Intent(searchResultActivity.this, DescriptionActivity.class);
                intent.putExtra(EXTRA_MESSAGE3, displayArray[j]); //Extra message needs to be different with
                startActivity(intent);

            }
        });
    }

//    public void sendCoordinates(Double[] latArray, Double[] lonArray){
//
//        Intent intent = new Intent(searchResultActivity.this, DescriptionActivity.class);
//        Bundle params = new Bundle();
//        params.putDoubleArray(EXTRA_MESSAGE4, latArray[]);
//        params.putDouble(EXTRA_MESSAGE5, lonArray);
//
//    }

}