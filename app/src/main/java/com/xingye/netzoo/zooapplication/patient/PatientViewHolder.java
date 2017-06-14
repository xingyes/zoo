package com.xingye.netzoo.zooapplication.patient;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.RemoveItemRecyclerViewHolder;
import com.xingye.netzoo.zooapplication.R;

/**
 * Created by yx on 17/6/14.
 */

public class PatientViewHolder extends RemoveItemRecyclerViewHolder{
    public TextView infov;
    public CheckBox checkv;

    public PatientViewHolder(RemoveItemRecyclerViewHolder viewHolder, View view)
    {
        super(viewHolder.itemView);
        content.addView(view);
        infov = (TextView) view.findViewById(R.id.info_key);
        checkv = (CheckBox) view.findViewById(R.id.info_check);

    }
}
