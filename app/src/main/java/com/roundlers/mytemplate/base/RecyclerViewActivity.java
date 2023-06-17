package com.roundlers.mytemplate.base;

import static com.roundlers.mytemplate.models.exceptions.ErrorConstants.NO_DATA;
import static com.roundlers.mytemplate.models.exceptions.ErrorConstants.NO_INTERNET;
import static com.roundlers.mytemplate.models.exceptions.ErrorConstants.OPERATION_FAILED;
import static com.roundlers.mytemplate.models.exceptions.ErrorConstants.SERVER_ERROR;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.constants.Constants;
import com.roundlers.mytemplate.helper.AppHelper;
import com.roundlers.mytemplate.helper.LogHelper;
import com.roundlers.mytemplate.models.exceptions.NoConnectionException;
import com.roundlers.mytemplate.models.exceptions.NoDataException;
import com.roundlers.mytemplate.models.exceptions.OperationFailedException;
import com.roundlers.mytemplate.models.exceptions.ServerError;
import com.roundlers.mytemplate.view.binders.HighlightBinder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by abhayalekal on 30/11/17.
 */
public abstract class RecyclerViewActivity<T, A extends DataBindAdapter> extends BaseActivity {
    public List<T> data;
    public RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;
    protected A adapter;
    T highlightObject;
    ProgressBar progressBar;
    View errorLayout;
    TextView retryBtn;
    private boolean requestInProgressUP, requestInProgressDOWN, noMoreDataUP, noMoreDataDOWN;

    protected abstract A getAdapter();

    private A getAdapterInstance() {
        if (adapter == null) {
            adapter = getAdapter();
        }

        AppHelper.dieIfNull(adapter);
        return adapter;
    }

    protected abstract void onScroll(int dx, int dy, boolean hasScrolledToBottom);

    protected abstract void onErrorLayoutClickListener();

    protected int getScrolledToBottomOffset() {
        return Constants.RECYCLER_VIEW_SCROLLED_TO_BOTTOM_OFFSET;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            highlightObject = getIntent().getParcelableExtra("highlightObject");
        } catch (Exception e) {

        }
        setRecyclerView();
    }

    protected LinearLayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(this);
        }
        return layoutManager;
    }

    protected void setCustomErrorLayout(Throwable e, int errorDrawable, int errorMessage, int errorMessageTxt, int visibility) {
        errorLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        if (errorMessageTxt != 0) {
            ((TextView) errorLayout.findViewById(R.id.erroMsgTxt)).setText(errorMessageTxt);
            ((TextView) errorLayout.findViewById(R.id.erroMsgTxt)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) errorLayout.findViewById(R.id.erroMsgTxt)).setVisibility(View.GONE);
        }
        ((TextView) errorLayout.findViewById(R.id.errorTxt)).setText(errorMessage);
        errorLayout.findViewById(R.id.retryBtn).setVisibility(visibility);
        ((ImageView) errorLayout.findViewById(R.id.tryAgainImgView)).setImageResource(errorDrawable);

        errorLayout.findViewById(R.id.retryBtn).setOnClickListener(getErrorLayoutClickListener());
    }

    protected void setCustomErrorLayout(Throwable e, int errorDrawable, String errorMessage, int errorMessageTxt, int visibility) {
        errorLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        if (errorMessageTxt != 0) {
            ((TextView) findViewById(R.id.erroMsgTxt)).setText(errorMessageTxt);
            ((TextView) findViewById(R.id.erroMsgTxt)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.erroMsgTxt)).setVisibility(View.GONE);
        }
        ((TextView) findViewById(R.id.errorTxt)).setText(errorMessage);
        errorLayout.findViewById(R.id.retryBtn).setVisibility(visibility);
        ((ImageView) findViewById(R.id.tryAgainImgView)).setImageResource(errorDrawable);

        errorLayout.findViewById(R.id.retryBtn).setOnClickListener(getErrorLayoutClickListener());
    }

    protected void setErrorLayout(Throwable e, int errorCode) {

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        int errorMessage = 0;
        int errorIcon = 0;
        int errorTxt = 0;
        boolean shouldFetchImageFromServer = false;
        String imageUrl = "";

        if (e instanceof NoConnectionException)
            errorCode = NO_INTERNET;
        else if (e instanceof NoDataException)
            errorCode = NO_DATA;
        else if (e instanceof ServerError)
            errorCode = SERVER_ERROR;
        else if (e instanceof OperationFailedException)
            errorCode = OPERATION_FAILED;

        switch (errorCode) {
            case SERVER_ERROR:
                errorTxt = R.string.Could_not_connect_to_server;
                errorMessage = R.string.Please_try_again_after_some_time;
                errorIcon = R.drawable.server_error;
                break;

            case Constants.NO_DATA:
//                if (this instanceof CommentsActivity) {
//                    errorTxt = R.string.Be_The_First_One_To_Comment;
//                    errorMessage = R.string.we_learn_best_when_we_learn_together;
//                    imageUrl = Constants.NoDataErrorImageUrls.NO_COMMENT_URL;
//                    shouldFetchImageFromServer = true;
//                } else if (this instanceof RepliesActivity) {
//                    errorTxt = R.string.no_discussions_yet;
//                    errorMessage = R.string.having_a_good_discussion_will_help_you_learn_more;
//                    imageUrl = Constants.NoDataErrorImageUrls.NO_REPLY_URL;
//                    shouldFetchImageFromServer = true;
//                } else if (this instanceof SearchActivity) {
//                    errorTxt = R.string.sorry_no_result;
//                    errorMessage = R.string.please_chk_Spelling;
//                    imageUrl = Constants.NoDataErrorImageUrls.NO_RESULT_FOUND_URL;
//                    shouldFetchImageFromServer = true;
//                } else if (this instanceof PracticeQuestionsActivity) {
//                    errorTxt = R.string.whoo_hoo_topic_completed;
//                    errorMessage = R.string.great_work_finished_practice;
//                    imageUrl = Constants.NoDataErrorImageUrls.COMPLETED_TOPIC_URL;
//                    shouldFetchImageFromServer = true;
//                } else if (this instanceof CoinLogActivity) {
//                    errorTxt = R.string.no_coins_earned;
//                    errorMessage = 0;
//                    errorIcon = R.drawable.icon_no_search;
//                } else {
                shouldFetchImageFromServer = false;
                errorTxt = R.string.Nothing_to_show;
                errorMessage = 0;
                errorIcon = R.drawable.icon_no_search;
//                }
                break;

            case Constants.NO_INTERNET:
                errorTxt = R.string.No_Internet_Connection;
                errorMessage = R.string.check_network_settings;
                errorIcon = R.drawable.no_connection;
                break;

            case Constants.OPERATION_FAILED:
                errorTxt = R.string.Could_not_connect_to_server;
                errorMessage = R.string.Please_try_again_after_some_time;
                errorIcon = R.drawable.server_error;
                break;

        }


        errorLayout.setVisibility(View.VISIBLE);

        if (errorMessage != 0) {
            ((TextView) findViewById(R.id.erroMsgTxt)).setText(errorMessage);
        }
        ((TextView) findViewById(R.id.errorTxt)).setText(errorTxt);

        if (shouldFetchImageFromServer) {
            Glide.with(this)
                    .load(imageUrl)
                    .error(R.drawable.icon_no_search)
                    .placeholder(R.drawable.icon_no_search)
                    .fitCenter()
                    .dontAnimate()
                    .into(((ImageView) findViewById(R.id.tryAgainImgView)));
        } else {
            ((ImageView) findViewById(R.id.tryAgainImgView)).setImageResource(errorIcon);
        }
        retryBtn = errorLayout.findViewById(R.id.retryBtn);

        if (errorCode == NO_DATA)
            retryBtn.setVisibility(View.GONE);
        else {
            retryBtn.setVisibility(View.VISIBLE);
            retryBtn.setOnClickListener(getErrorLayoutClickListener());
        }
    }

    protected void setRecyclerView() {

        recyclerView = findViewById(R.id.recycler_view);
        errorLayout = findViewById(R.id.errorParent);
        if (recyclerView != null) {
            data = new ArrayList<>();
            progressBar = findViewById(R.id.progress_bar);
            recyclerView.setItemAnimator(null);
            recyclerView.setLayoutManager(null);
            recyclerView.setLayoutManager(getLayoutManager());

            recyclerView.setAdapter(getAdapterInstance());
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    onScroll(dx, dy, data.size() > 0 && getLayoutManager().findLastVisibleItemPosition
                            () > data.size() - getScrolledToBottomOffset());
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAdapter().disposeSubscriptionsIfAny();
    }

    public void setRequestInProgress(int direction, boolean requestInProgress) {


        if (direction == Constants.Direction.UP) {
            requestInProgressUP = requestInProgress;
        } else {
            requestInProgressDOWN = requestInProgress;
        }
        if (data.size() == 0 && requestInProgress && adapter.getUpLoaderIndex() == -1) {
            progressBar.setVisibility(View.VISIBLE);

        }
    }

    public void setNoMoreData(int direction, boolean noMoreData) {
        if (direction == Constants.Direction.UP) {
            this.noMoreDataUP = noMoreData;
        } else {
            this.noMoreDataDOWN = noMoreData;
        }
    }

    protected boolean canRequest(int direction) {
        boolean canMakeRequest = direction == Constants.Direction.UP ? !requestInProgressUP && !noMoreDataUP : !requestInProgressDOWN && !noMoreDataDOWN;
        if (canMakeRequest) {
            setRequestInProgress(direction, true);
        }
        adapter.refreshLoaderBinder(direction);
        return canMakeRequest;
    }

    protected void responseReceived(int direction, boolean noMoreData) {

        setRequestInProgress(direction, false);
        if (noMoreData) {
            setNoMoreData(direction, true);
        }
    }

    protected void hideErrorLayout() {
        errorLayout.setVisibility(View.GONE);
    }

    protected void dataLoadSuccess(ArrayList<T> d, int direction, boolean notifyAll) {
        if (recyclerView.getVisibility() == View.GONE) recyclerView.setVisibility(View.VISIBLE);

        boolean highlight = data.size() == 0 && highlightObject != null;
        progressBar.setVisibility(View.GONE);
        hideErrorLayout();
        int initialSize = data.size();
        int index = direction == Constants.Direction.UP ? 0 : data.size();
        for (T t : d) {
            if (!data.contains(t)) {
                data.add(index++, t);
            }
        }

        responseReceived(direction, false);

        if (notifyAll) {
            adapter.notifyDataSetChanged();

        } else {
            if (data.size() > initialSize) {
                adapter.notifyItemRangeInserted(direction == Constants.Direction.UP
                        ? adapter.getHeadersCount() : initialSize + adapter.getHeadersCount() + adapter.getFixedCardsCountForPosition(initialSize + adapter.getHeadersCount()), data.size() - initialSize);


            }


            adapter.refreshLoaderBinder(direction);
        }
        if (highlight) {

            new Handler().postDelayed(() -> {
                int i = data.indexOf(highlightObject);


                if (i > -1) {
                    layoutManager.scrollToPosition(adapter.getHeadersCount() + i);
                }
                highlightBinder(adapter.getHeadersCount() + i);
            }, getHighlightDelayInMillis());
        }

    }

    protected long getHighlightDelayInMillis() {
        return 0;
    }

    public void highlightBinder(int i) {
        DataBinder dataBinder = adapter.getDataBinder(adapter.getItemViewType(i));
        if (dataBinder instanceof HighlightBinder) {
            HighlightBinder highlightBinder = (HighlightBinder) dataBinder;
            PublishSubject<Boolean> publishSubject = highlightBinder.setHighlightObject(i, highlightObject);
            publishSubject.subscribe(aBoolean -> {
                if (aBoolean) highlightObject = null;
            });
        }
    }

    protected void dataLoadFailure(int direction, Throwable e, int code, boolean showErrorLayout) {


        progressBar.setVisibility(View.GONE);

        responseReceived(direction, e instanceof NoDataException || code == Constants.NO_DATA);
        if (showErrorLayout && data.size() == 0) {
            setErrorLayout(e, code);
        } else {
            hideErrorLayout();
        }

        adapter.refreshLoaderBinder(direction);
    }

    protected void dataLoadFailure(int direction, Throwable e, boolean showErrorLayout) {


        progressBar.setVisibility(View.GONE);

        responseReceived(direction, e instanceof NoDataException);
        if (showErrorLayout && data.size() == 0) {
            if (!AppHelper.isConnected(activity)) {
                setErrorLayout(e, Constants.NO_INTERNET);
                return;

            }
            if (e instanceof NetworkErrorException) {
                setErrorLayout(e, Constants.SERVER_ERROR);

            } else if (e instanceof NoDataException) {
                setErrorLayout(e, Constants.NO_DATA);
            } else {
                setErrorLayout(e, Constants.SERVER_ERROR);
            }

        } else {
            hideErrorLayout();
        }

        adapter.refreshLoaderBinder(direction);
    }

    public abstract void loaderClicked(int direction);

    public View.OnClickListener getErrorLayoutClickListener() {
        return view -> {
            if (AppHelper.isConnected(RecyclerViewActivity.this)) {
                progressBar.setVisibility(View.VISIBLE);
                errorLayout.setVisibility(View.GONE);
                onErrorLayoutClickListener();
            } else {
                LogHelper.showBottomToast(RecyclerViewActivity.this,
                        R.string.please_connect_to_internet);
            }
        };
    }

    public boolean noMoreData(int direction) {
        return direction == Constants.Direction.UP ? noMoreDataUP : noMoreDataDOWN;
    }

    public boolean requestInProgress(int direction) {
        return direction == Constants.Direction.UP ? requestInProgressUP : requestInProgressDOWN;
    }

    public List<T> getDataList() {
        return data;
    }
}
