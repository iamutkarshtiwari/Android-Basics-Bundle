package iamutkarshtiwari.github.io.noos.adapters;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import iamutkarshtiwari.github.io.noos.R;
import iamutkarshtiwari.github.io.noos.models.News;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    public TextView newsCategory, newsTitle, newsType, newsDate;

    public NewsViewHolder(View itemView) {
        super(itemView);

        newsCategory = (TextView) itemView.findViewById(R.id.category);
        newsTitle = (TextView) itemView.findViewById(R.id.news_title);
        newsType = (TextView) itemView.findViewById(R.id.type);
        newsDate = (TextView) itemView.findViewById(R.id.date);
    }

    public void bind(final News news, final RecyclerViewAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(news);
            }
        });
    }
}
