package iamutkarshtiwari.github.io.noos.adapters;

/**
 * Created by utkarshtiwari on 14/10/17.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import iamutkarshtiwari.github.io.noos.R;
import iamutkarshtiwari.github.io.noos.models.News;

public class RecyclerViewAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private ArrayList<News> items;
    private Activity activity;
    private OnItemClickListener mListener;

    public RecyclerViewAdapter(Activity activity, ArrayList<News> newsList, OnItemClickListener listener) {
        this.activity = activity;
        this.items = newsList;
        this.mListener = listener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {

        News news = items.get(position);
        holder.newsCategory.setText(news.getCategory());
        holder.newsTitle.setText(news.getTitle());
        holder.newsType.setText(activity.getResources().getString(R.string.type, news.getType()));
        holder.newsDate.setText(activity.getResources().getString(R.string.date, news.getDate()));
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
    public void updateAdapterData(ArrayList<News> newData) {
        this.items = newData;
        notifyDataSetChanged();
    }

    /**
     * Returns adapter list data
     *
     * @return
     */
    public ArrayList<News> getAdapterData() {
        return this.items;
    }

    public interface OnItemClickListener {
        void onItemClick(News news);
    }
}