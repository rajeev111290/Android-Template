package com.roundlers.mytemplate.view.binders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.base.DataBindAdapter;
import com.roundlers.mytemplate.base.DataBinder;
import com.roundlers.mytemplate.helper.AppHelper;
import com.roundlers.mytemplate.models.GenericCardDividerModel;

import java.util.List;


public class GenericCardDividerBinder extends DataBinder<GenericCardDividerBinder.ViewHolder> {

    public GenericCardDividerBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.card_divider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        GenericCardDividerModel genericCardDividerModel= (GenericCardDividerModel) adapter.getDataForPosition(position);
        int margin=genericCardDividerModel.getTopMargin();
        if(margin>0){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.divider.getLayoutParams();
            layoutParams.topMargin= AppHelper.pxFromDp(activity,margin);
        }
        int height=genericCardDividerModel.getHeight();
        if(height>0){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.divider.getLayoutParams();
            layoutParams.height=AppHelper.pxFromDp(activity,height);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View divider;
        public ViewHolder(View itemView) {
            super(itemView);
            this.divider=itemView.findViewById(R.id.divider);
        }
    }
}
