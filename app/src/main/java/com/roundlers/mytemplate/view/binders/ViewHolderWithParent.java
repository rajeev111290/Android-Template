package com.roundlers.mytemplate.view.binders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.roundlers.mytemplate.R;

/**
 * Created by abhayalekal on 16/02/18.
 */

class ViewHolderWithParent extends RecyclerView.ViewHolder {

    View parent;

    public ViewHolderWithParent(View itemView) {
        super(itemView);
        parent = itemView.findViewById(R.id.parent);
    }
}
