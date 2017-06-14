package com.xingye.netzoo.xylib.utils.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingye.netzoo.xylib.R;

/**
 * Created by yx on 17/6/14.
 */

public class RemoveItemRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView delete;
    public LinearLayout layout;
    public RelativeLayout content;

    public RemoveItemRecyclerViewHolder(View itemView) {
        super(itemView);
        delete = (TextView) itemView.findViewById(R.id.item_delete);
        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
        content = (RelativeLayout) itemView.findViewById(R.id.item_content);
    }
}
