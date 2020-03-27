package com.roundlers.mytemplate.view.binders;

import android.view.View;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.base.DataBindAdapter;
import com.roundlers.mytemplate.base.DataBinder;

import java.util.List;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by abhayalekal on 16/02/18.
 */

public abstract class HighlightBinder<H, T extends ViewHolderWithParent> extends DataBinder<T> {

    private H highlightObject;

    abstract void bindHolder(T holder, int position);

    private PublishSubject<Boolean> animationCompletePublisher;

    HighlightBinder(DataBindAdapter dataBindAdapter) {
        super(dataBindAdapter);
    }

    @Override
    public void bindViewHolder(T holder, int position, List<Object> payloads) {
        bindHolder(holder, position);


        if (highlightObject != null) {
            if (position >= adapter.getHeadersCount()) {
                Object o = adapter.data.get(position - adapter.getHeadersCount() - adapter.getFixedCardsCountForPosition(position));
                if (highlightObject.equals(o)) {
                    holder.parent.setBackgroundColor(activity.getResources().getColor(R.color.color_d8f0e3));
                    reset(holder.parent);
                    return;
                }
            } else {
                holder.parent.setBackgroundColor(activity.getResources().getColor(R.color.color_d8f0e3));
                reset(holder.parent);
                return;
            }
        }

/*        if (position % 2 == 0)
            holder.parent.setBackgroundColor(activity.getResources().getColor(co.gradeup.android.R.color.color_45b97c));
        else*/
            holder.parent.setBackgroundColor(activity.getResources().getColor(R.color.color_ffffff_feed_card));
    }

    private void reset(View parent) {
        new android.os.Handler().postDelayed(() -> {
            animationCompletePublisher.onNext(true);
            highlightObject = null;
            parent.setBackgroundColor(activity.getResources().getColor(R.color.color_ffffff));
        }, 1000);
    }

    public PublishSubject<Boolean> setHighlightObject(int pos, H highlightObject) {

        setPublishSubject();

        this.highlightObject = highlightObject;

        adapter.notifyItemChanged(pos);
        return animationCompletePublisher;
    }

    private void setPublishSubject() {
        animationCompletePublisher = PublishSubject.create();
    }
}
