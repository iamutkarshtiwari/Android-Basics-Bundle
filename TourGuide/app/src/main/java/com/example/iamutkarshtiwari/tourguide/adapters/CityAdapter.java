package com.example.iamutkarshtiwari.tourguide.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iamutkarshtiwari.tourguide.R;

/**
 * Created by iamutkarshtiwari on 02/10/17.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.TextItemViewHolder> {

    String[] items;

    public CityAdapter(String[] items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false);
        return new TextItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextItemViewHolder holder, int position) {
        holder.bind(items[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class TextItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TextItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.city_item);
        }

        public void bind(String text) {
            textView.setText(text);
        }

    }
}