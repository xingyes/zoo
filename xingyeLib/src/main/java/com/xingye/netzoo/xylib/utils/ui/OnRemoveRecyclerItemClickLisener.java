package com.xingye.netzoo.xylib.utils.ui;

import android.view.View;

/**
 * Created by yx on 17/6/14.
 */

public interface OnRemoveRecyclerItemClickLisener {

    /**
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     *
     * @param position
     */
    void onDeleteClick(int position);
}
