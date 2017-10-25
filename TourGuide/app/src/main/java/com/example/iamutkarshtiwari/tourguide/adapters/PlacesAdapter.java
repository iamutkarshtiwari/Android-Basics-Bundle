package com.example.iamutkarshtiwari.tourguide.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iamutkarshtiwari.tourguide.R;
import com.example.iamutkarshtiwari.tourguide.utils.Place;

import java.util.ArrayList;

/**
 * Created by iamutkarshtiwari on 02/10/17.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ItemViewHolder> {

    ArrayList<Place> items;
    Context context;
    String city;

    public PlacesAdapter(ArrayList<Place> items, Context ctx) {
        this.items = items;
        this.context = ctx;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView subtitle;
        private TextView description;
        private ImageView image;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            description = (TextView) itemView.findViewById(R.id.desc);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public void bind(Place place) {
            name.setText(place.getName());
            subtitle.setText(place.getSubtitle());
            description.setText(place.getDescription());
            image.setImageResource(place.getImageID());
        }

    }
}