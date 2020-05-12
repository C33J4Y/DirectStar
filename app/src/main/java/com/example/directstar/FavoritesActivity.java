package com.example.directstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity  {

    private ListView favoritesList;
    private ArrayList<String> favorites;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Intent intent = getIntent();
        String description = intent.getStringExtra(DescriptionActivity.EXTRA_MESSAGE6);

        favoritesList = findViewById(R.id.favoritesList);

        favorites = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, favorites);
        adapter.add(description);
        FileHelper.writeData(favorites, this);
        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
        favoritesList.setAdapter(adapter);

        //To make ListView clickable
        favoritesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int j, long id) {
                favorites.remove(j);
                adapter.notifyDataSetChanged();
                 FileHelper.writeData(favorites, FavoritesActivity.this);
                Toast.makeText(FavoritesActivity.this, "Removed From Favorites.", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

