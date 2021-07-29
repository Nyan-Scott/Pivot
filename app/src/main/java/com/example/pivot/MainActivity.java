package com.example.pivot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public SongCollection songCollection = new SongCollection();




    ArrayList<Integer> favList = new ArrayList<Integer>();
    private Object listener;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_,new home_fragment()).commit();

    }

    private BottomNavigationView.OnItemSelectedListener navigationItemSelectedListener =new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId())

            {
                case R.id.search_bar:
                    selectedFragment = new searchFragment();
                break;

                case R.id.playlist:
                    selectedFragment = new playlistsFragment();
                    break;

                case R.id.home:
                    selectedFragment = new home_fragment();
                    break;

                case R.id.settings:
                    selectedFragment = new settingsFragment();
                    break;



            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_,selectedFragment).commit();

            return true;

        }
    };






    public void sendDataToActivity(int index){
        Intent intent =new Intent(this, PlaySongActivity.class);
         intent.putExtra("index", index);

        startActivity(intent);
    }




    public void handleSelection(View myView){
        String resourceId = getResources().getResourceEntryName(myView.getId());
        Log.d("temasek", "The ID for the selected song is " + resourceId);
        int currentArrayIndex = songCollection.searchSongById(resourceId);
       Log.d("temasek", "The index in the array for this song is " + currentArrayIndex);
        sendDataToActivity(currentArrayIndex);
    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut(); // gonna log out of the app
        startActivity(new Intent(getApplicationContext(),login.class));
        finish();
    }





    public class MainAcitivity extends Fragment{

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_main, container, false);
        }
    }
}





