package com.example.iamutkarshtiwari.pandora.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iamutkarshtiwari.pandora.R;

/**
 * Created by iamutkarshtiwari on 29/09/17.
 */


/**
 * Created by anupamchugh on 05/10/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TextItemViewHolder> {

    String[] items;

    public class TextItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;


        public TextItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.list_item);
        }

        public void bind(String text) {
            textView.setText(text);
        }

    }


    public RecyclerViewAdapter(String[] items) {
        this.items = items;
    }

    @Override
    public TextItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_list_item, parent, false);
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
}
