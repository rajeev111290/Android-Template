package com.roundlers.mytemplate.models;

import android.os.Parcelable;

import com.roundlers.mytemplate.base.BaseModel;

import java.util.ArrayList;


/**
 * Created by sanjeevkumar on 04/12/18.
 */

public interface RecyclerSectionHeaderModel<M extends BaseModel> extends BaseModel, Parcelable{
    public ArrayList<M> getSectionalData();
    public boolean isSelected();
    public void setSelected(boolean selected);
}
