package com.roundlers.mytemplate.base;

import android.app.Activity;
import android.os.Handler;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.roundlers.mytemplate.constants.Constants;
import com.roundlers.mytemplate.models.RecyclerSectionHeaderModel;
import com.roundlers.mytemplate.view.binders.EmptyDataBinder;
import com.roundlers.mytemplate.view.binders.GenericCardDividerBinder;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Adapter class for managing a set of data binders
 * <p/>
 * Created by yqritc on 2015/03/01.
 */
public class DataBindAdapter<M extends BaseModel> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Activity activity;
    public List<M> data;
    protected CompositeDisposable compositeDisposable;
    protected SparseArray<DataBinder> binders;
    int maxFixedCardPosition = 0;
    private int
            headerIndex = Constants.HEADERS_START_INDEX,
            footerIndex = Constants.FOOTERS_START_INDEX,
            headersCount, footersCount, fixedPositionCardsCount;
    private int upLoaderIndex = -1, downLoaderIndex;
    private String LOADER_BINDER_CLASS = "LoaderBinder";
    private boolean containsFixedPositionCards;
    private RecyclerSectionHeaderModel selectedGenericSectionHeaderModel;
    /**
     * Call this for data list binders.
     *
     * @param type
     * @param binder
     * @param <D>
     */
    private HashMap<Integer, Integer> supportedType = new HashMap<>();
    /**
     * Call this for data list binders.
     *
     * @param type
     * @param binder
     * @param <D>
     */
    private HashMap<Integer, Integer> fixedPositionType = new HashMap<>();

    public DataBindAdapter(Activity activity, List<M> data) {
        this.activity = activity;
        this.data = data;
        compositeDisposable = new CompositeDisposable();
        binders = new SparseArray<>();
        addBinder(Constants.ModelType.GENERIC_CARD_DIVIDER, new GenericCardDividerBinder(this));
        addBinder(-18233434, new EmptyDataBinder(this));
    }

    public M getDataForPosition(int position) {
        int index = position - headersCount - getFixedCardsCountForPosition(position);
        if (data.size() < index) {
            return null;
        }
        return data.get(index);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public int getPositionOfData(M data) {
        int dataIndex = this.data.indexOf(data);
        if (dataIndex == -1) {
            return -1;
        }
        return dataIndex + headersCount + getFixedCardsCountForPosition(dataIndex + headersCount);
    }

    public void addData(M data) {
        this.data.add(data);
    }

    public boolean removeDataAndNotify(M data) {
        int positionOfData = getPositionOfData(data);
        if (positionOfData == -1) {
            return false;
        }
        this.data.remove(data);
        notifyItemChanged(positionOfData);
        return true;
    }

    public void removeDataAtIndex(int index) {
        if (index < data.size()) {
            this.data.remove(index);
            notifyItemChanged(index + headersCount + getFixedCardsCountForPosition(index + headersCount));
        }
    }

    public void addDataAtIndexAndNotify(int index, M data) {
        this.data.add(index, data);
        int index2 = getPositionOfData(data);
        if (index2 > -1)
            notifyItemChanged(index2);
    }

    public void updateDataAtIndexAndNotify(int index, M data) {
        this.data.set(index, data);
        notifyAtData(data);
    }

    public int getHeadersCount() {
        return headersCount;
    }

    public void notifyAtData(M data) {
        int index = getPositionOfData(data);
        if (index > -1)
            notifyItemChanged(index);
    }

    public int getFootersCount() {
        return footersCount;
    }

    public int getFootersStartPosition() {
        return headersCount + data.size() + fixedPositionCardsCount;
    }

    /**
     * All binders at positions above data list are considered as headers
     *
     * @param headerBinder
     * @param <D>
     */
    protected <D extends DataBinder> int addHeader(D headerBinder) {
        int position = headersCount;
        if (headerBinder.getClass().getSimpleName().equals(LOADER_BINDER_CLASS))
            upLoaderIndex = position;

        headersCount++;
        binders.put(headerIndex++, headerBinder);
        supportedType.put(headerIndex - 1, headerIndex - 1);
        return position;
    }

    /**
     * All binders at positions below data list are considered as footers
     *
     * @param footerBinder
     * @param <D>
     */
    protected <D extends DataBinder> int addFooter(D footerBinder) {

        if (footerBinder.getClass().getSimpleName().equals(LOADER_BINDER_CLASS))
            downLoaderIndex = footersCount;

        footersCount++;

        binders.put(footerIndex++, footerBinder);
        supportedType.put(footerIndex - 1, footerIndex - 1);
        return getItemCount() - 1;
    }

    protected <D extends DataBinder> void addBinder(int type, D binder) {
        binders.put(type, binder);
        supportedType.put(type, type);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List<Object> payloads) {
        if (payloads == null || payloads.size() == 0)
            super.onBindViewHolder(viewHolder, position, payloads);
        if (viewHolder != null)
            getDataBinder(getItemViewType(position)).bindViewHolder(viewHolder, position, payloads);
    }

    @Override
    public int getItemCount() {
        int size = data == null ? 0 : data.size();
        return headersCount + size + footersCount + fixedPositionCardsCount;
    }

    protected <D extends DataBinder> void addFixedPositionCards(int position, int type, D binder) {
        containsFixedPositionCards = true;
        if (!fixedPositionType.containsKey(position)) {
            fixedPositionCardsCount++;
        }
        binders.put(type, binder);
        supportedType.put(type, position);
        fixedPositionType.put(position, type);
        if (maxFixedCardPosition < position) {
            maxFixedCardPosition = position;
        }
    }


    public int getFixedCardsCountForPosition(int position) {
        if (!containsFixedPositionCards) {
            return 0;
        }

        if (maxFixedCardPosition + headersCount < position) {
            return fixedPositionCardsCount;
        }

        int count = 0;
        for (int i = 0; i < position; i++) {
            if (fixedPositionType.containsKey(i - headersCount)) {
                count++;
            }
        }
        return count;
    }

    public void notifyFixedCardByType(int type) {
        if (supportedType.containsKey(type)) {
            notifyItemChanged(supportedType.get(type) + headersCount);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int itemViewType = 123424;
        if (fixedPositionType.containsKey(position - headersCount)) {
            // fixed position type
            itemViewType = fixedPositionType.get(position - headersCount);
//            fixedCardsCountAddedTillNow++;
        } else if (position < headersCount) {
            itemViewType = Constants.HEADERS_START_INDEX + position;
        } else if (position >= headersCount + data.size() + fixedPositionCardsCount) {
            itemViewType = Constants.FOOTERS_START_INDEX + (position -
                    (headersCount + data.size() + getFixedCardsCountForPosition(position)));
        } else {
            // - kitne fixed cards added already
            M m;
//            if (position - headersCount - getFixedCardsCountForPosition(position) + 1 < data.size()) {
//                m = data.get(position - headersCount - getFixedCardsCountForPosition(position) + 1);
//            } else if (position - headersCount - getFixedCardsCountForPosition(position) < data.size()) {
            if (data.size() > position - headersCount - getFixedCardsCountForPosition(position)) {
                m = data.get(position - headersCount - getFixedCardsCountForPosition(position));
                itemViewType = m.getModelType();
            } else {
                itemViewType = Constants.EMPTY_BINDER;
            }
//            } else {
//                m = data.get(position - headersCount);
//            }
        }

        if (supportedType.get(itemViewType) != null) {
            return itemViewType;
        } else {
            return Constants.EMPTY_BINDER;
        }
    }

    public <T extends DataBinder> T getDataBinder(int viewType) {

        return (T) binders.get(viewType);
    }

    public void disposeSubscriptionsIfAny() {
        compositeDisposable.dispose();
    }

    public void notifyHeaders() {
        notifyItemRangeChanged(0, headersCount);
    }

    public void notifyFooters() {
        notifyItemRangeChanged(headersCount + data.size()
                + getFixedCardsCountForPosition(headersCount + data.size()), footersCount);
    }

    public void refreshLoaderBinder(int direction) {

        new Handler().postDelayed(() -> {
            if (direction == Constants.Direction.DOWN)
                notifyItemChanged(headersCount + data.size() + downLoaderIndex);
            else
                notifyItemChanged(upLoaderIndex);
        }, 50);
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public int getUpLoaderIndex() {
        return upLoaderIndex;
    }


    private void removeLastSectionalElements() {
        if (selectedGenericSectionHeaderModel != null) {
            selectedGenericSectionHeaderModel.setSelected(false);
            int indexOf = getPositionOfData((M) selectedGenericSectionHeaderModel);
            data.removeAll(selectedGenericSectionHeaderModel.getSectionalData());
            notifyItemChanged(indexOf);
            notifyItemRangeRemoved(indexOf + 1, selectedGenericSectionHeaderModel.getSectionalData().size());
            //notifyDataSetChanged();
        }
    }

    public void addAndRemoveSectionalElements(RecyclerSectionHeaderModel genericSectionHeaderModel) {


        if (genericSectionHeaderModel == null) {
            return;
        }
        if (this.selectedGenericSectionHeaderModel != null && this.selectedGenericSectionHeaderModel.equals(genericSectionHeaderModel)) {
            removeLastSectionalElements();
            this.selectedGenericSectionHeaderModel = null;
            return;
        }
        genericSectionHeaderModel.setSelected(true);
        notifyItemChanged(getPositionOfData((M) genericSectionHeaderModel));
        data.addAll(data.indexOf(genericSectionHeaderModel) + 1, genericSectionHeaderModel.getSectionalData());
        //notifyDataSetChanged();
        notifyItemRangeInserted(getPositionOfData((M) genericSectionHeaderModel) + 1, genericSectionHeaderModel.getSectionalData().size());
        removeLastSectionalElements();
        this.selectedGenericSectionHeaderModel = genericSectionHeaderModel;
    }

}
