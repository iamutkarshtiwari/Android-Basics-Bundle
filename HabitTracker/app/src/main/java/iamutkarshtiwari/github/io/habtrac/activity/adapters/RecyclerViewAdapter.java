package iamutkarshtiwari.github.io.habtrac.activity.adapters;

/**
 * Created by utkarshtiwari on 17/10/17.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import iamutkarshtiwari.github.io.habtrac.activity.models.Habit;
import iamutkarshtiwari.github.io.habtrac.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<Habit> items;
    private Activity activity;
    private View parentView;

    public RecyclerViewAdapter(Activity activity, ArrayList<Habit> habitList, View parentView) {
        this.activity = activity;
        this.items = habitList;
        this.parentView = parentView;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {

        Habit habit = items.get(position);
        holder.habitSrNo.setText(habit.getId());
        holder.habitName.setText(habit.getName());
        holder.habitDate.setText(habit.getDate());
        holder.habitFrequency.setText(habit.getFrequency());
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
    public void updateAdapterData(ArrayList<Habit> newData) {
        this.items = newData;
        notifyDataSetChanged();

        View emptyView = parentView.findViewById(R.id.empty_view);
        if (this.items.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * Returns adapter list data
     *
     * @return
     */
    public ArrayList<Habit> getAdapterData() {
        return this.items;
    }

}