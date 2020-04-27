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
                        try {
                            JSONObject venues = response.getJSONObject("venues");
                            JSONArray venueArray = venues.getJSONArray("venue");
                            JSONObject venuePart = venueArray.getJSONObject(0);
                            String venueName = venuePart.getString("venue_name");
                            String address = venuePart.getString("address");
                            String cityName = venuePart.getString("city_name");
                            String state = venuePart.getString("region_name");
                            String venueType = venuePart.getString("venue_type");

                            // TODO: Add IF statements to let users know if search returned NO RESULTS

                                //searchResultView.setText(address);
                            Toast.makeText(searchResultActivity.this,venueName,Toast.LENGTH_LONG).show();
                            Toast.makeText(searchResultActivity.this,address,Toast.LENGTH_LONG).show();
                            Toast.makeText(searchResultActivity.this,cityName,Toast.LENGTH_LONG).show();
                            Toast.makeText(searchResultActivity.this,state,Toast.LENGTH_LONG).show();
                            Toast.makeText(searchResultActivity.this,venueType,Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            //e.printStackTrace();
                            searchResultView.setText(e.toString());
                        }


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
//    public void displaySearchResults(JSONObject response) {
//        String searchResults;
//        String CRLF = "\n";
//
//
//        // Capture the layout's TextView and set the string as its text
//        final TextView searchResultView = findViewById(R.id.searchResultView);
//
//
//
//
//            try {

           // JSONObject venues = response.getJSONObject("venues");
//            String address = venue.get("address").toString();
//            String city = venue.get("city_name").toString();
            //JSONArray venue = response.getJSONArray("venue");
            //JSONObject venuePart = venue.getJSONObject(0);
           // String venueName = venuePart.getString("venue_name");
            //String address = venuePart.getString("address");
            //String city = venuePart.getString("city_name");
//            String cityName = response.getString("name");
//            JSONObject mainJSON = response.getJSONObject("main");
//            String temperature = mainJSON.getString("temp");
//            searchResults = cityName + " Weather" + CRLF + CRLF +
//                    "Longitude:\t\t\t" + lon + CRLF +
//                    "Latitude:\t\t\t" + lat + CRLF +
//                    "Weather:\t\t\t" + main.toUpperCase() + CRLF +
//                    "Description:\t\t" + description.toUpperCase() + CRLF +
//                    "Temperature:\t" + temperature;
//        searchResults = //"Name:\t\t\t" + venueName + CRLF +
//                        "address:\t\t\t" + address + CRLF +
//                        "City:\t\t\t" + city;
//        searchResultView.setText(searchResults);
//
//
//          } catch (JSONException e) {
//        e.printStackTrace();
//         searchResultView.setText(e.toString());
//        }
//
//          }

    }

