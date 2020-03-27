package com.roundlers.mytemplate.view.binders;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.base.DataBindAdapter;
import com.roundlers.mytemplate.base.DataBinder;

import java.util.List;



/**
 * Created by sanjeev on 12/11/17.
 */

public class EmptyDataBinder extends DataBinder<EmptyDataBinder.ViewHolder> {


    private Activity context;


    public EmptyDataBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
        this.context = context;
    }


    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View view = inflater.inflate(R.layout.single_line_layout, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height=1;
        view.setLayoutParams(layoutParams);
        return new EmptyDataBinder.ViewHolder(view);
    }


    @Override
    public void bindViewHolder(ViewHolder holder, int position, List<Object> payloads) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
