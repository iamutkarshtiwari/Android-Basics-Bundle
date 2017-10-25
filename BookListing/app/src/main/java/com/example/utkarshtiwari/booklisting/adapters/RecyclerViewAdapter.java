package com.example.utkarshtiwari.booklisting.adapters;

/**
 * Created by utkarshtiwari on 11/10/17.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.utkarshtiwari.booklisting.R;
import com.example.utkarshtiwari.booklisting.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private ArrayList<Book> items;
    private Activity activity;
    private OnItemClickListener mListener;

    public static interface OnItemClickListener {
        void onItemClick(Book book);
    }


    public RecyclerViewAdapter(Activity activity, ArrayList<Book> items, OnItemClickListener listener) {
        this.activity = activity;
        this.items = items;
        this.mListener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {

        String imageURL = items.get(position).getImageURL();
        if (imageURL == null || imageURL.length() == 0) {
            holder.bookImage.setImageResource(R.drawable.image_not_found);
        } else {
            Picasso.with(activity)
                    .load(items.get(position).getImageURL())
                    .fit()
                    .error(activity.getResources().getDrawable(R.drawable.image_not_found))
                    .into(holder.bookImage);
        }

        holder.bookName.setText(items.get(position).getName());
        String authList = "";
        if (items.get(position).getAuthors().size() > 0) {
            for (String auth : items.get(position).getAuthors()) {
                authList += auth + ", ";
            }
            holder.bookAuthor.setText(authList.substring(0, authList.length() - 2));
        } else {
            holder.bookAuthor.setText(activity.getResources().getString(R.string.not_mentioned));
        }

        String lang = items.get(position).getLanguage();
        holder.bookLang.setText(String.format("%s", lang.substring(0, 1).toUpperCase() + lang.substring(1)));
        String currency = items.get(position).getCurrency();
        if (currency.length() > 0) {
            double price = items.get(position).getPrice();
            holder.bookPrice.setText(this.activity.getResources().getString(R.string.price, currency, price));
        }

        holder.bind(items.get(position), mListener);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Updates the adapter item list with new list
     *
     * @param newData list
     */
    public void updateAdapterData(ArrayList<Book> newData) {
        this.items = newData;
        notifyDataSetChanged();
    }

    /**
     * Returns adapter list data
     * @return
     */
    public ArrayList<Book> getAdapterData() {
        return this.items;
    }
}