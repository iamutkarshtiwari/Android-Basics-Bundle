package iamutkarshtiwari.github.io.habtrac.activity.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import iamutkarshtiwari.github.io.habtrac.R;

/**
 * Created by utkarshtiwari on 17/10/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView habitSrNo, habitName, habitDate, habitFrequency;

    public ItemViewHolder(View itemView) {
        super(itemView);

        habitSrNo = (TextView) itemView.findViewById(R.id.sr_no);
        habitName = (TextView) itemView.findViewById(R.id.name);
        habitDate = (TextView) itemView.findViewById(R.id.date);
        habitFrequency = (TextView) itemView.findViewById(R.id.frequency);
    }
}