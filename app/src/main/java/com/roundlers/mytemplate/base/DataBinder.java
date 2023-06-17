package com.roundlers.mytemplate.base;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Class for binding view and data
 * <p/>
 * Created by yqritc on 2015/03/01.
 */
abstract public class DataBinder<T extends RecyclerView.ViewHolder> {

    protected Activity activity;
    protected DataBindAdapter adapter;

    public DataBinder(DataBindAdapter dataBindAdapter) {
        adapter = dataBindAdapter;
        activity = dataBindAdapter.activity;
    }

    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position, List<Object> payloads);

    public final void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

}
