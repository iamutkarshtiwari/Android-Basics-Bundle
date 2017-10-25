package com.example.iamutkarshtiwari.pandora.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.iamutkarshtiwari.pandora.R;
import com.example.iamutkarshtiwari.pandora.activity.LibraryActivity;
import com.example.iamutkarshtiwari.pandora.activity.NowPlayingActivity;
import com.example.iamutkarshtiwari.pandora.adapters.RecyclerViewAdapter;

/**
 * Created by iamutkarshtiwari on 29/09/17.
 */

public class FragmentSongs extends Fragment {

    private static final int ARTISTS_TAB_INDEX = 0;
    private static final int ALBUMS_TAB_INDEX = 1;
    private static final int SONGS_TAB_INDEX = 2;
    private RecyclerView recyclerView;
    private ListView list;
    private int arrayID;
    private int tabIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_view, container, false);
        this.arrayID = getArguments().getInt("string_array");
        this.tabIndex = getArguments().getInt("tab_index");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String[] items = getResources().getStringArray(this.arrayID);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(items);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(LibraryActivity.getContext(), new GestureDetector.SimpleOnGestureListener() {
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

                    Intent intent = new Intent(getActivity(), NowPlayingActivity.class);
                    intent.putExtra("song_name", items[itemPosition]);
                    startActivity(intent);
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
}