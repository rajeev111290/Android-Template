package com.roundlers.mytemplate.view.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.roundlers.mytemplate.MyApplication;
import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.helper.AppHelper;
import com.roundlers.mytemplate.helper.LogHelper;
import com.roundlers.mytemplate.helper.ViewHelper;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class SuperActionBar extends RelativeLayout {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private ImageView
            leftMostIconView,
            penultimateRightMostIconView,
            rightMostIconView,
            titleDropdownView,
            subTitleDropdownView;
    private TextView
            subtitleTextView,
            titleTextView;
    private RelativeLayout
            titleView,
            subtitleView;
    private ArrayList<View> visibleViews;
    private int
            DIMENSION,
            DROPDOWN_ICON_WIDTH,
            RM_DIM,
            PEN_RM_DIM,
            PRM_DIM;
    private int[] titleViewRules;
    private TouchListener touchListener;
    private boolean setTextPaddingZero;

    public SuperActionBar(Context context) {
        super(context);
        init();
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    private void init() {
        visibleViews = new ArrayList<>();
        //DIMENSION = getResources().getDimensionPixelSize(R.dimen.dim_48);
        DIMENSION = AppHelper.pxFromDp(getContext(), 48);
        RM_DIM = getResources().getDimensionPixelOffset(R.dimen.dim_48);
        PRM_DIM = getResources().getDimensionPixelOffset(R.dimen.dim_20);
        PEN_RM_DIM = getResources().getDimensionPixelOffset(R.dimen.dim_48);
        DROPDOWN_ICON_WIDTH = getResources().getDimensionPixelOffset(R.dimen.dim_48);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DIMENSION);
        setBackgroundColor(getResources().getColor(R.color.color_ffffff_feed_card));
        setUnderlineView(1);
        setLayoutParams(params);
        setOnClickListener(v -> {
            if (touchListener != null) {
                touchListener.onSuperActionBarClicked();
            }
        });
        invalidate();
        requestLayout();
    }

    public SuperActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperActionBar setTouchListener(TouchListener touchListener) {
        this.touchListener = touchListener;
        return this;
    }

    public SuperActionBar setLeftMostIconView(int drawable) {
        return setLeftMostIconView(drawable, 16);
    }

    public void nullifyRighticon() {
        rightMostIconView = null;
    }

    public SuperActionBar setLeftMostIconView(int drawable, int paddingInDp) {
        if (leftMostIconView == null) {
            leftMostIconView = new ImageView(getContext());
            addView(leftMostIconView);
            leftMostIconView.setId(ViewHelper.generateViewId());
            LayoutParams params = new LayoutParams(DIMENSION, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(ALIGN_PARENT_LEFT);
            leftMostIconView.setLayoutParams(params);
            int padding = AppHelper.pxFromDp(getContext(), paddingInDp);
            leftMostIconView.setPadding(padding, padding, padding, padding);
            leftMostIconView.setImageResource(drawable);
            leftMostIconView.setOnClickListener(v -> {
                if (touchListener != null) {
                    touchListener.onLeftMostIconClicked();
                }
            });
            if (visibleViews.contains(titleView)) {
                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
                titleParams.leftMargin = DIMENSION;
                titleView.setLayoutParams(titleParams);
            }
            if (visibleViews.contains(subtitleView)) {
                LayoutParams subtitleParams = (LayoutParams) subtitleView.getLayoutParams();
                subtitleParams.leftMargin = DIMENSION;
                subtitleView.setLayoutParams(subtitleParams);
            }
            //TODO : ripple
//            AppHelper.setBackground(leftMostIconView, R.drawable.btn_ripple_drawable, getContext(), R.drawable.alternate_card);
            visibleViews.add(leftMostIconView);
            if(titleView!=null) {
                titleView.setPadding(0, 0, 0, 0);
            }else{
                setTextPaddingZero=true;
            }


            invalidate();
            requestLayout();
        }
        return this;
    }

    public SuperActionBar setUnderlineView(int width) {
        View v = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                width
        );
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);

        v.setLayoutParams(layoutParams);
        v.setBackgroundColor(Color.parseColor("#b3b3b3"));
        visibleViews.add(v);
        addView(v);

        return this;
    }

    public SuperActionBar setTitle(String title) {
        return setTitle(title, R.color.color_333333, 16, null, false);
    }

    public SuperActionBar setTitle(String title, int color) {
        return setTitle(title, color, 16, null, false);
    }

    public SuperActionBar setTitle(String title, int color, int endPaddingInDP, int[] rules, boolean translate) {
        if (titleView == null) {
            titleView = new RelativeLayout(getContext());
            titleTextView = new TextView(getContext());
            addView(titleView);
            titleViewRules = rules;
            titleView.setId(ViewHelper.generateViewId());
            titleTextView.setId(ViewHelper.generateViewId());
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleTextView.setText(title);
            titleTextView.setMaxLines(1);
            titleTextView.setEllipsize(TextUtils.TruncateAt.END);
            // titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//            titleTextView.setTextAppearance(getContext(),R.style.TextH1);
            titleTextView.setTextColor(color);
            titleTextView.setGravity(Gravity.CENTER_VERTICAL);

            if (visibleViews.contains(leftMostIconView)) {
                params.leftMargin = DIMENSION;
            }
            if (visibleViews.contains(subtitleView)) {
                params.topMargin = getResources().getDimensionPixelSize(R.dimen.dim_8);
            } else {
                params.addRule(CENTER_VERTICAL);
                if (titleViewRules != null) {
                    for (Integer rule : titleViewRules) {
                        params.addRule(rule);
                    }
                }
                titleTextView.setTextAppearance(getContext(), R.style.TextH2ActionBar);

            }
            params.rightMargin = (visibleViews.contains(rightMostIconView) ? RM_DIM : 0) + (visibleViews.contains(penultimateRightMostIconView) ? PEN_RM_DIM : 0);
            int endPaddingInPixels = (setTextPaddingZero)?0:AppHelper.pxFromDp(getContext(), endPaddingInDP);
            titleView.setPadding(endPaddingInPixels, 0, 0, 0);


            titleView.setLayoutParams(params);

            titleDropdownView = new ImageView(getContext());
            titleDropdownView.setImageResource(R.drawable.arrow_down_white);
            LayoutParams dropdownParams = new LayoutParams(getResources().getDimensionPixelOffset(R.dimen.dim_36), getResources().getDimensionPixelOffset(R.dimen.dim_48));
            dropdownParams.addRule(RIGHT_OF, titleTextView.getId());
            dropdownParams.addRule(ALIGN_TOP, titleTextView.getId());
            dropdownParams.addRule(ALIGN_BOTTOM, titleTextView.getId());
            dropdownParams.leftMargin = -DROPDOWN_ICON_WIDTH;
            titleDropdownView.setId(ViewHelper.generateViewId());
            titleDropdownView.setPadding(getResources().getDimensionPixelSize(R.dimen.dim_8), getResources().getDimensionPixelSize(R.dimen.dim_8), getResources().getDimensionPixelSize(R.dimen.dim_8), getResources().getDimensionPixelSize(R.dimen.dim_8));


            titleDropdownView.setVisibility(GONE);
            DROPDOWN_ICON_WIDTH = AppHelper.measureCellWidth(getContext(), titleDropdownView);
            LayoutParams titleTextLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            titleTextLayoutParams.rightMargin = DROPDOWN_ICON_WIDTH;
            titleTextLayoutParams.addRule(CENTER_VERTICAL);
            titleTextView.setLayoutParams(titleTextLayoutParams);
            titleDropdownView.setLayoutParams(dropdownParams);
            titleTextView.setTypeface(MyApplication.poppinsBold);
            titleTextView.setSingleLine(true);


            titleView.addView(titleTextView);
            titleView.addView(titleDropdownView);
            titleView.setVisibility(View.VISIBLE);

            titleView.setOnClickListener(v -> {
                if (touchListener != null) {
                    touchListener.onTitleClicked();
                }
            });

            visibleViews.add(titleView);
        } else {
            titleTextView.setTypeface(MyApplication.poppinsBold);
            titleTextView.setText(title);
        }

        invalidate();
        requestLayout();
        return this;
    }

    public SuperActionBar setTitle(String title, boolean translate) {
        return setTitle(title, Color.WHITE, 16, null, translate);
    }

    public SuperActionBar setTitle(String title, int endPaddingInDP, boolean translate) {
        return setTitle(title, Color.WHITE, endPaddingInDP, null, translate);
    }

    public SuperActionBar setTitle(String title, int color, int endPaddingInDP, boolean translate) {
        return setTitle(title, color, endPaddingInDP, null, translate);
    }

    public SuperActionBar setTitle(String title, int endPaddingInDP, int[] rules) {
        return setTitle(title, Color.WHITE, endPaddingInDP, rules, false);
    }

    public SuperActionBar setTitle(String title, int color, int endPaddingInDP, int[] rules) {
        return setTitle(title, color, endPaddingInDP, rules, false);
    }

    public SuperActionBar setSubtitle(String subtitle) {
        return setSubtitle(subtitle, 0, false, 0);
    }

    public SuperActionBar setSubtitle(String subtitle, int paddingInDp, boolean translate, int color) {
        if (subtitleView == null) {
            subtitleView = new RelativeLayout(getContext());
            addView(subtitleView);
            subtitleView.setId(ViewHelper.generateViewId());
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = getResources().getDimensionPixelSize(R.dimen.dim_24);
            if (visibleViews.contains(leftMostIconView)) {
                params.leftMargin = DIMENSION;
            }
            if (visibleViews.contains(titleView)) {
                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
                titleParams.addRule(CENTER_VERTICAL, 0);
                titleParams.topMargin = getResources().getDimensionPixelSize(R.dimen.dim_8);
                titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                titleTextView.setTextAppearance(getContext(), R.style.TextH7ActionBar);
                titleView.setLayoutParams(titleParams);
            }
            params.rightMargin = (visibleViews.contains(rightMostIconView) ? RM_DIM : 0) + (visibleViews.contains(penultimateRightMostIconView) ? PEN_RM_DIM : 0);
            subtitleView.setLayoutParams(params);
            subtitleTextView = new TextView(getContext());
            titleTextView.setSingleLine(true);

            String str = subtitle;
            if (str.length() > 25) {
                str = str.substring(0, 25) + "...";
            }

            subtitleTextView.setText(str);
            subtitleTextView.setSingleLine(true);
            subtitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
            subtitleTextView.setMaxLines(1);
            subtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
            subtitleTextView.setId(ViewHelper.generateViewId());
            if (color == 0) {
                subtitleTextView.setTextColor(Color.WHITE);
//                subtitleTextView.setTextAppearance(getContext(),R.style.TextH5White);

            } else {
//                subtitleTextView.setTextAppearance(getContext(),R.style.TextH5);
                subtitleTextView.setTextColor(color);
            }
            LayoutParams subtitleTextLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            subTitleDropdownView = new ImageView(getContext());
            subTitleDropdownView.setImageResource(R.drawable.arrow_down_white);
            LayoutParams dropdownParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dropdownParams.addRule(RIGHT_OF, subtitleTextView.getId());
            dropdownParams.addRule(ALIGN_TOP, subtitleTextView.getId());
            dropdownParams.addRule(ALIGN_BOTTOM, subtitleTextView.getId());
            subTitleDropdownView.setId(ViewHelper.generateViewId());
            subTitleDropdownView.setPadding(getResources().getDimensionPixelSize(R.dimen.dim_4), 0, getResources().getDimensionPixelSize(R.dimen.dim_20), 0);
            subTitleDropdownView.setVisibility(GONE);
            DROPDOWN_ICON_WIDTH = AppHelper.measureCellWidth(getContext(), subTitleDropdownView);
            subtitleTextView.setLayoutParams(subtitleTextLayoutParams);
            subTitleDropdownView.setLayoutParams(dropdownParams);
            subtitleView.setPadding(AppHelper.pxFromDp(getContext(), paddingInDp), 0, 0, 0);
            subtitleTextView.setTextAppearance(getContext(), R.style.TextBodySmallSemiBoldActionBar);
            subtitleTextView.setTypeface(MyApplication.poppinsBold);
            subtitleView.addView(subtitleTextView);
            subtitleView.addView(subTitleDropdownView);
            subtitleView.setOnClickListener(v -> {
                if (touchListener != null) {
                    touchListener.onSubtitleClicked();
                }
            });
            subtitleView.setVisibility(View.VISIBLE);
            visibleViews.add(subtitleView);
        } else {
            // TODO
            /*subtitleTextView.setText(translate ? AppHelper.getTranslation(getContext(), subtitle, subtitleTextView, null) : subtitle);*/
            subtitleTextView.setTypeface(MyApplication.poppinsBold);
            String str = subtitle;
            if (str.length() > 25) {
                str = str.substring(0, 25) + "...";
            }

            subtitleTextView.setText(str);
        }
        invalidate();
        requestLayout();
        return this;
    }

//    public SuperActionBar hideSubtitle() {
//        if (subtitleView != null) {
//            visibleViews.remove(subtitleView);
//            subtitleView.setVisibility(GONE);
//            if (visibleViews.contains(titleView)) {
//                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
//                titleParams.addRule(CENTER_VERTICAL);
//                if (titleViewRules != null) {
//                    for (int rule : titleViewRules) {
//                        titleParams.addRule(rule);
//                    }
//                }
//                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//                titleView.setLayoutParams(titleParams);
//            }
//            invalidate();
//            requestLayout();
//        }
//        return this;
//    }

    public SuperActionBar showSubtitle() {
        if (subtitleView != null) {
            visibleViews.add(subtitleView);
            subtitleView.setVisibility(VISIBLE);
            if (visibleViews.contains(titleView)) {
                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
                titleParams.addRule(CENTER_VERTICAL, 0);
                titleParams.topMargin = getResources().getDimensionPixelSize(R.dimen.dim_8);
                titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 0);
                titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, 0);
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                titleView.setLayoutParams(titleParams);
            }
            invalidate();
            requestLayout();
        }
        return this;
    }

    public void setTitleIcon(int icon) {
        ViewGroup.LayoutParams params = titleDropdownView.getLayoutParams();
        titleDropdownView.setImageResource(icon);
        titleDropdownView.setLayoutParams(params);
        titleView.removeAllViews();
        titleView.addView(titleTextView);
        titleView.addView(titleDropdownView);

    }

    public void setSubTitleIcon(int icon) {

        ViewGroup.LayoutParams params = subTitleDropdownView.getLayoutParams();
        subTitleDropdownView.setImageResource(icon);
        subTitleDropdownView.setLayoutParams(params);
        subtitleView.removeAllViews();
        subtitleView.addView(subtitleTextView);
        subtitleView.addView(subTitleDropdownView);

    }

    public SuperActionBar showSubtitleDropdown() {
        if (subTitleDropdownView != null) {
            titleDropdownView.setVisibility(GONE);
            subTitleDropdownView.setVisibility(VISIBLE);
            subTitleDropdownView.setOnClickListener(v -> {
                if (touchListener != null) {
                    touchListener.onDropdownClicked();
                }
            });
        } else {
            throw new IllegalStateException("SuperActionBar: cannot set dropdown before setting " +
                    "subtitle");
        }
        return this;
    }

    public SuperActionBar hideTitleDropdown() {
        if (titleDropdownView != null) {
            titleDropdownView.setVisibility(GONE);
        }
        return this;
    }

    public SuperActionBar showTitleDropdown() {
        if (titleDropdownView != null) {
            if (subTitleDropdownView != null) {
                subTitleDropdownView.setVisibility(GONE);
            }
            titleDropdownView.setVisibility(VISIBLE);
            titleDropdownView.setOnClickListener(v -> {
                if (touchListener != null) {
                    touchListener.onDropdownClicked();
                }
            });
        } else {
            throw new IllegalStateException("SuperActionBar: cannot set dropdown before setting " + "title");
        }
        return this;
    }

    public SuperActionBar setPenultimateRightMostIconView(int drawable) {
        return setPenultimateRightMostIconView(drawable, 12);
    }

    public SuperActionBar setPenultimateRightMostIconView(int drawable, int paddindInDp) {
        return setPenultimateRightMostIconView(drawable, paddindInDp, false);
    }

    public SuperActionBar setPenultimateRightMostIconView(int drawable, int paddingInDp, boolean useSmallIcon) {
        if (penultimateRightMostIconView == null) {
            penultimateRightMostIconView = new ImageView(getContext());
            addView(penultimateRightMostIconView);
            visibleViews.add(penultimateRightMostIconView);
            penultimateRightMostIconView.setId(ViewHelper.generateViewId());
            if (useSmallIcon) {
                PEN_RM_DIM = getResources().getDimensionPixelSize(R.dimen.dim_36);
            }
            LayoutParams params = new LayoutParams(PEN_RM_DIM, ViewGroup.LayoutParams.MATCH_PARENT);
            int rightMargin = PEN_RM_DIM;
            if (visibleViews.contains(rightMostIconView)) {
                rightMargin += RM_DIM;
                params.addRule(LEFT_OF, rightMostIconView.getId());
            } else {
                params.addRule(ALIGN_PARENT_RIGHT);
            }
            if (visibleViews.contains(titleView)) {
                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
                titleParams.rightMargin = rightMargin;
                titleView.setLayoutParams(titleParams);
            }
            if (visibleViews.contains(subtitleView)) {
                LayoutParams subtitleParams = (LayoutParams) subtitleView.getLayoutParams();
                subtitleParams.rightMargin = rightMargin;
                subtitleTextView.getLayoutParams().width = getAdjustedSubtitleWidth();
                subtitleView.setLayoutParams(subtitleParams);
            }
            penultimateRightMostIconView.setLayoutParams(params);
            int padding = AppHelper.pxFromDp(getContext(), paddingInDp);
            penultimateRightMostIconView.setPadding(padding, padding, padding, padding);
            penultimateRightMostIconView.setImageResource(drawable);
            penultimateRightMostIconView.setOnClickListener(v -> {
                if (touchListener != null) {
                    {
                        if (drawable == R.drawable.icon_search || drawable == R.drawable.icon_grey_search) {
//                            FirebaseAnalyticsHelper.sendEvent(getContext(), "Tap Search Box", new HashMap<>());
                            LogHelper.showBottomToast(getContext(),"Tap Search Box");
//                            getContext().startActivity(SearchActivity.getLaunchIntent(getContext(), "", null, null, "", true));
                        } else {
                            touchListener.onPenultimateRightMostIconClicked();
                        }

                    }
                }
            });

            //TODO : ripple
//            AppHelper.setBackground(penultimateRightMostIconView, R.drawable.btn_ripple_drawable, getContext(), R.drawable.alternate_card);
            penultimateRightMostIconView.setVisibility(View.VISIBLE);
        } else if (penultimateRightMostIconView.getVisibility() == GONE) {
            visibleViews.add(penultimateRightMostIconView);
            penultimateRightMostIconView.setVisibility(VISIBLE);
            if (rightMostIconView != null) {
                visibleViews.add(rightMostIconView);
                rightMostIconView.setVisibility(VISIBLE);
            }
        }
        if (drawable == R.drawable.ic_search_bar && !AppHelper.isLoggedIn(getContext())) {
            penultimateRightMostIconView.setVisibility(View.GONE);
        }
        invalidate();
        requestLayout();
        return this;
    }

    private int getAdjustedSubtitleWidth() {
        int rightMostViewsWidth = (visibleViews.contains(penultimateRightMostIconView) ? PEN_RM_DIM : 0) + (visibleViews.contains(rightMostIconView) ? RM_DIM : 0);
        int subtitleTextViewWidth = AppHelper.measureCellWidth(getContext(), subtitleTextView);
        int deviceWidth = AppHelper.getDeviceDimensions((Activity) getContext()).x;
        int leftIconWidth = visibleViews.contains(leftMostIconView) ? DIMENSION : 0;
        if (leftIconWidth + subtitleTextViewWidth + DROPDOWN_ICON_WIDTH > deviceWidth - rightMostViewsWidth) {
            return deviceWidth - leftIconWidth - rightMostViewsWidth - DROPDOWN_ICON_WIDTH;
        }
        return subtitleTextViewWidth;
    }

    public SuperActionBar setRightMostIconView(int drawable) {
        return setRightMostIconView(drawable, 16);
    }

    public SuperActionBar setRightMostIconView(int drawable, int paddingInDp) {
        return setRightMostIconView(drawable, paddingInDp, false);
    }

    public SuperActionBar setRightMostIconView(int drawable, int paddingInDp, boolean useSmallIcon) {
        if (rightMostIconView == null) {
            rightMostIconView = new ImageView(getContext());
            addView(rightMostIconView);
            visibleViews.add(rightMostIconView);
            rightMostIconView.setId(ViewHelper.generateViewId());
            if (useSmallIcon) {
                RM_DIM = getResources().getDimensionPixelSize(R.dimen.dim_36);
            }
            LayoutParams params = new LayoutParams(RM_DIM, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(ALIGN_PARENT_RIGHT);
            rightMostIconView.setLayoutParams(params);
            int padding = AppHelper.pxFromDp(getContext(), paddingInDp);
            rightMostIconView.setPadding(padding, padding, padding, padding);
            rightMostIconView.setImageResource(drawable);
            rightMostIconView.setOnClickListener(v -> {
                if (touchListener != null) {
                    if (drawable == R.drawable.icon_search || drawable == R.drawable.icon_grey_search) {
//                        FirebaseAnalyticsHelper.sendEvent(getContext(), "Tap Search Box", new HashMap<>());
                        LogHelper.showBottomToast(getContext(),"Tap Search Box");
//                        getContext().startActivity(SearchActivity.getLaunchIntent(getContext(), "", null, null, "", true));
                    } else {
                        touchListener.onRightMostIconClicked();
                    }
                }
            });
            //TODO : ripple
//            AppHelper.setBackground(rightMostIconView, R.drawable.btn_ripple_drawable, getContext(), R.drawable.alternate_card);
            rightMostIconView.setVisibility(View.VISIBLE);
            int rightMargin = RM_DIM;
            if (visibleViews.contains(penultimateRightMostIconView)) {
                rightMargin += RM_DIM;
                if (titleView != null) {
                    LayoutParams immediateLeftIconParams = (LayoutParams) titleView.getLayoutParams();
                    immediateLeftIconParams.rightMargin = RM_DIM;
                    immediateLeftIconParams.addRule(ALIGN_PARENT_RIGHT, 0);
                    titleView.setLayoutParams(immediateLeftIconParams);
                }
            }
            if (visibleViews.contains(titleView)) {
                LayoutParams titleParams = (LayoutParams) titleView.getLayoutParams();
                titleParams.rightMargin = rightMargin;
                titleView.setLayoutParams(titleParams);
            }
            if (visibleViews.contains(subtitleView)) {
                LayoutParams subtitleParams = (LayoutParams) subtitleView.getLayoutParams();
                subtitleParams.rightMargin = rightMargin;
                subtitleTextView.getLayoutParams().width = getAdjustedSubtitleWidth();
                subtitleView.setLayoutParams(subtitleParams);
            }
        } else if (rightMostIconView.getVisibility() == GONE) {
            visibleViews.add(rightMostIconView);
            rightMostIconView.setVisibility(VISIBLE);
            if (penultimateRightMostIconView != null) {
                visibleViews.add(penultimateRightMostIconView);
                penultimateRightMostIconView.setVisibility(VISIBLE);
            }
        }
        invalidate();
        requestLayout();
        return this;
    }

    public void hideRightMostIconView() {
        if (visibleViews.contains(rightMostIconView)) {
            ViewGroup.LayoutParams layoutParams = rightMostIconView.getLayoutParams();
            layoutParams.width = 0;
        }
    }

    public void hidePenultimateIconView() {
        if (visibleViews.contains(penultimateRightMostIconView)) {
            ViewGroup.LayoutParams layoutParams = penultimateRightMostIconView.getLayoutParams();
            layoutParams.width = 0;
        }
    }

    public interface TouchListener {
        void onSuperActionBarClicked();

        void onLeftMostIconClicked();

        void onTitleClicked();

        void onSubtitleClicked();

        void onDropdownClicked();

        void onPenultimateRightMostIconClicked();

        void onRightMostIconClicked();
    }
}
