package com.example.pivot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {
private SongCollection songCollection  = new SongCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendDataToActivity(int index){
        Intent intent =new Intent(this, PlaySongActivity.class);
         intent.putExtra("index", index);
        startActivity(intent);
    }

    public void handleSelection(View myView){
        String resourceId = getResources().getResourceEntryName(myView.getId());
        int currentArrayIndex = songCollection.searchSongById(resourceId);
        Log.d("temasek", "The index in the array for this song is " + currentArrayIndex);
        sendDataToActivity(currentArrayIndex);
    }
}

