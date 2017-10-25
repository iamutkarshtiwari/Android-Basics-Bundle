package com.example.iamutkarshtiwari.pandora.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.iamutkarshtiwari.pandora.activity.LibraryActivity;
import com.example.iamutkarshtiwari.pandora.R;
import com.example.iamutkarshtiwari.pandora.fragment.FragmentAlbums;
import com.example.iamutkarshtiwari.pandora.fragment.FragmentArtists;
import com.example.iamutkarshtiwari.pandora.fragment.FragmentSongs;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentArtists();
            Bundle args = new Bundle();
            args.putInt("string_array", R.array.artists);
            args.putInt("tab_index", 0);
            fragment.setArguments(args);
        }
        else if (position == 1)
        {
            fragment = new FragmentAlbums();
            Bundle args = new Bundle();
            args.putInt("string_array", R.array.albums);
            args.putInt("tab_index", 1);
            fragment.setArguments(args);
        }
        else if (position == 2)
        {
            fragment = new FragmentSongs();
            Bundle args = new Bundle();
            args.putInt("string_array", R.array.songs);
            args.putInt("tab_index", 2);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = LibraryActivity.getContext().getResources().getString(R.string.artists);
        }
        else if (position == 1)
        {
            title = LibraryActivity.getContext().getResources().getString(R.string.albums);
        }
        else if (position == 2)
        {
            title = LibraryActivity.getContext().getResources().getString(R.string.songs);
        }
        return title;
    }
}
