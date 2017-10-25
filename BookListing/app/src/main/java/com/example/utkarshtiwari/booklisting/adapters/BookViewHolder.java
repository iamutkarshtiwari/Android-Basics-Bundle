package com.example.utkarshtiwari.booklisting.adapters;

/**
 * Created by utkarshtiwari on 11/10/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.utkarshtiwari.booklisting.R;
import com.example.utkarshtiwari.booklisting.models.Book;


public class BookViewHolder extends RecyclerView.ViewHolder {

    public ImageView bookImage;
    public TextView bookName, bookAuthor, bookPrice, bookLang;

    public BookViewHolder(View itemView) {
        super(itemView);

        bookImage = (ImageView) itemView.findViewById(R.id.imageView);
        bookName = (TextView) itemView.findViewById(R.id.name);
        bookAuthor = (TextView) itemView.findViewById(R.id.author);
        bookPrice = (TextView) itemView.findViewById(R.id.price);
        bookLang = (TextView) itemView.findViewById(R.id.language);
    }

    public void bind(final Book book, final RecyclerViewAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(book);
            }
        });
    }
}
