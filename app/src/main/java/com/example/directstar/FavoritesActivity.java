package com.example.directstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {

    int i = 0;
    String[] favListArray = new String[]{""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Intent intent = getIntent();
        String description = intent.getStringExtra(DescriptionActivity.EXTRA_MESSAGE6);

        favListArray[i] = description;
        i++;

        ListView listView = (ListView) findViewById(R.id.favoritesList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FavoritesActivity.this,android.R.layout.simple_list_item_1,favListArray);
        listView.setAdapter(adapter);
    }
}
