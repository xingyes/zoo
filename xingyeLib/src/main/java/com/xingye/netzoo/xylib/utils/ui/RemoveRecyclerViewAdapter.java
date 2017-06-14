package com.xingye.netzoo.xylib.utils.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.xingye.netzoo.xylib.R;

import java.util.ArrayList;

public class RemoveRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater mInflater;
    protected ArrayList<T> mList;

    public RemoveRecyclerViewAdapter(Context context, ArrayList<T> list) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RemoveItemRecyclerViewHolder(mInflater.inflate(R.layout.removeitem_recyclerview_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
