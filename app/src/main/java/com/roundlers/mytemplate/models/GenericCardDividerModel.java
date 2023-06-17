package com.roundlers.mytemplate.models;

import com.roundlers.mytemplate.base.BaseModel;
import com.roundlers.mytemplate.constants.Constants;

/**
 * Created by sanjeevkumar on 17/12/18.
 */

public class GenericCardDividerModel implements BaseModel {

    private int topMargin;
    private int height;

    @Override
    public int getModelType() {
        return Constants.ModelType.GENERIC_CARD_DIVIDER;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
