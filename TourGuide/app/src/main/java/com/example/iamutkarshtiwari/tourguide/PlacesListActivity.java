package com.example.iamutkarshtiwari.tourguide;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.iamutkarshtiwari.tourguide.adapters.PlacesAdapter;
import com.example.iamutkarshtiwari.tourguide.utils.Place;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PlacesListActivity extends AppCompatActivity {

    private ArrayList<Place> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String title = getIntent().getStringExtra("city_name");
        getSupportActionBar().setTitle(title);

        placeList = generatePlacesList(title);

        PlacesAdapter adapter = new PlacesAdapter(placeList, PlacesListActivity.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(PlacesListActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                View childView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    int itemPosition = Recyclerview.getChildAdapterPosition(childView);
                    String placeName = placeList.get(itemPosition).getName();
                    Uri uri = Uri.parse("http://maps.google.co.in/maps?q=" + Uri.encode(placeName));

                    // Open the location in google maps
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                    PackageManager packageManager = getPackageManager();
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent);
                    } else {
                        Log.e(getResources().getString(R.string.error), getResources().getString(R.string.maps_not_available));
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Place> generatePlacesList(String city) {
        ArrayList<Place> placeList = new ArrayList<Place>();
        city = city.toLowerCase();
        if (city.equalsIgnoreCase("Himachal Pradesh")) {
            city = "hp";
        }
        String[] nameArray = getResArrayFromID(city.toLowerCase() + "_places_names");
        String[] subtitleArray = getResArrayFromID(city.toLowerCase() + "_places_subtitle");
        String[] descArray = getResArrayFromID(city.toLowerCase() + "_places_desc");

        for (int i = 0; i < 4; i++) {
            int imageID = getResources().getIdentifier(city + "_" + (i + 1), "drawable", getPackageName());
            Place place = new Place(nameArray[i], subtitleArray[i], descArray[i], imageID);
            placeList.add(place);
        }
        return placeList;
    }

    /**
     * Return resource array from string name
     *
     * @param str resource string name
     * @return resource string array
     */
    public String[] getResArrayFromID(String str) {
        return getResources().getStringArray(getResources().getIdentifier(str, "array", getPackageName()));
    }
}
