package com.roundlers.mytemplate.helper;


import android.app.Activity;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.models.ImageMeta;

import java.util.HashMap;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by abhayalekal on 06/12/17.
 */

public class TextViewHelper {


    public static String trim(String text) {
        if (text == null || text.length() == 0) {
            return "";
        }

        text = text.trim().replaceFirst("^((\\s*)||(&nbsp;)*)?(<(\\s)*[bB][rR](\\s)*/>(\\s)*)?", "");
        text = text.replaceAll("(<span[^>]*color:[^>]*black[^>]*>)", "<span>");
        return text;
    }

    public static void setText(
            Activity activity,
            TextView target,
            String text,

            // true if links in the text are needed to be clickable
            boolean linksClickable,

            // maxLines supported by the target. pass 0 if no constraint is to be applied.
            int maxLines,

            HashMap<String, ImageMeta> imageMetaMap,

            // If maxLines set, this will remove it and vice versa.
            boolean canExpand, boolean shouldOpenYoutubeLink, boolean showReadMore,
            boolean isImageClickable, boolean calledFromComment, boolean setDefaultColorForHtml,
            boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon, boolean shouldDownloadImageForTextView) {

        setText(activity, target, text, linksClickable, maxLines, imageMetaMap, canExpand, shouldOpenYoutubeLink,
                showReadMore, isImageClickable, calledFromComment, setDefaultColorForHtml, null,
                shouldShowImageDownloadIcon, shouldShowImageRotateIcon, shouldDownloadImageForTextView);
    }


    public static void setText(
            Activity activity,
            TextView target,
            String text,

            // true if links in the text are needed to be clickable
            boolean linksClickable,

            // maxLines supported by the target. pass 0 if no constraint is to be applied.
            int maxLines,

            HashMap<String, ImageMeta> imageMetaMap,

            // If maxLines set, this will remove it and vice versa.
            boolean canExpand, boolean shouldOpenYoutubeLink, boolean showReadMore,
            boolean isImageClickable, boolean calledFromComment, boolean setDefaultColorForHtml, PublishSubject<Boolean> isImageDownloaded,
            boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon, boolean shouldDownloadImageForTextView) {


        if (imageMetaMap != null && shouldDownloadImageForTextView) {
            DownloadImagesHelper downloadImagesHelper = new DownloadImagesHelper(activity);
            downloadImagesHelper.downloadImagesFromTVH(imageMetaMap, isImageDownloaded);
        }


        if (text == null || text.length() <= 0) {
            return;
        }

        text = text.trim();
        target.setLinksClickable(false);
        if (maxLines > 0) {
            target.setMaxLines(maxLines);
            target.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            target.setMaxLines(Integer.MAX_VALUE);
            target.setEllipsize(null);
        }
        Spannable spanned = Spannable.Factory.getInstance().newSpannable(text);
        Spannable spannedText = null;
        try {
//            spannedText = getSpannedText(activity, spanned.toString(), imageMetaMap);
            spannedText = trimSpannable(spannedText);
        } catch (Exception e) {
            e.printStackTrace();
            //spannedText = getSpannedText(activity, spanned.toString(), imageMetaMap);
        }

//        try {
//            if (setDefaultColorForHtml) {
//                if (spannedText != null) {
//                    if (SharedPreferencesHelper.getNightModeStatus()) {
//                        spannedText.setSpan(
//                                new ForegroundColorSpan(Color.WHITE),
//                                0, spannedText.toString().length(),
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    } else {
//                        spannedText.setSpan(
//                                new ForegroundColorSpan(Color.BLACK),
//                                0, spannedText.toString().length(),
//                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        setImageActivity(activity, spannedText, imageMetaMap, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);
//        spannedText = stripUnderlines(target, spannedText, activity, shouldOpenYoutubeLink, calledFromComment, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);
        SpannableString s = SpannableString.valueOf(spannedText);

//        boolean isLinkPresent = addLinks(text, s, new Pattern[]{Constants.WEB_URL, Constants.TAGGED_USER}, new String[]{"http://", "https://", "rtsp://", "ftp://"}, activity,
//                shouldOpenYoutubeLink, calledFromComment, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);

        target.setText(s);

//        if (isLinkPresent) {
//            target.setAutoLinkMask(0);
//        }

        if (linksClickable || isImageClickable) {
            target.setLinksClickable(true);
            target.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (showReadMore) {
            setLabelAfterEllipsis(target, activity, maxLines, canExpand);
        }
    }


    public static void setTextForFeeds(
            Activity activity,
            TextView target,
            Spannable spannedText,

            // true if links in the text are needed to be clickable
            boolean linksClickable,

            // maxLines supported by the target. pass 0 if no constraint is to be applied.
            int maxLines,

            HashMap<String, ImageMeta> imageMetaMap,

            // If maxLines set, this will remove it and vice versa.
            boolean canExpand, boolean shouldOpenYoutubeLink, boolean showReadMore, boolean isImageClickable,
            boolean calledFromComment, boolean setDefaultColorForHtml, boolean isLinkPresent) {

        if (spannedText == null || spannedText.length() <= 0) {
            return;
        }

        target.setLinksClickable(false);
        if (maxLines > 0) {
            target.setMaxLines(maxLines);
            target.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            target.setMaxLines(Integer.MAX_VALUE);
            target.setEllipsize(null);
        }

//        try {
//            if (setDefaultColorForHtml) {
//
//                if (SharedPreferencesHelper.getNightModeStatus()) {
//                    spannedText.setSpan(new ForegroundColorSpan(Color.WHITE),
//                            0, spannedText.toString().length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                } else {
//                    spannedText.setSpan(new ForegroundColorSpan(Color.BLACK),
//                            0, spannedText.toString().length(),
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        setImageActivity(activity, spannedText, imageMetaMap, false, false);
//        stripUnderlines(target, spannedText, activity, shouldOpenYoutubeLink, calledFromComment, false, false);

        if (isImageClickable) {
            target.setMovementMethod(LinkMovementMethod.getInstance());
        }

        target.setText(spannedText, TextView.BufferType.SPANNABLE);

        if (isLinkPresent) {
            target.setAutoLinkMask(0);
        }

        if (linksClickable) {
            target.setLinksClickable(true);
            target.setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (showReadMore && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setLabelAfterEllipsis(target, activity, maxLines, canExpand);
        }

    }

    private static SpannableStringBuilder trimSpannable(Spannable spannable) {
//        checkNotNull(spannable);
        SpannableStringBuilder preview = new SpannableStringBuilder();
        preview.append(spannable);

//        SpannableStringBuilder spannableStringBuilder=SpannableStringBuilder.valueOf(spannable);
        if (spannable == null) {
            return null;
        }

        int trimStart = 0;
        int trimEnd = 0;

        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }

        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }

        SpannableStringBuilder returnSpan;
        try {
            returnSpan = preview.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
        } catch (Exception e) {
            e.printStackTrace();
            returnSpan = preview;
        }

        return returnSpan;
    }

    private static void setImageActivity(Activity context, Spannable spannedText, HashMap<String, ImageMeta> imageMetaMap, boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon) {


        for (final ImageSpan span : spannedText.getSpans(0, spannedText.length(), ImageSpan.class)) {

            int flags = spannedText.getSpanFlags(span);
            final int start = spannedText.getSpanStart(span);
            final int end = spannedText.getSpanEnd(span);
            spannedText.setSpan(new URLSpan(span.getSource()) {
                @Override
                public void onClick(View v) {
//
//                     if ((imageMetaMap.get(span.getSource()).getWidth() * context.getResources().getDisplayMetrics().density) > context.getResources().getDisplayMetrics().widthPixels * 0.4) {
//                        context.startActivity(ImageActivity.getIntent(context, span.getSource(), !shouldShowImageDownloadIcon, 0, 0, false,
//                                false, shouldShowImageRotateIcon));
//                    }
                }
            }, start, end, flags);
        }
    }


    private static void setLabelAfterEllipsis(TextView textView, Activity activity, int maxLines, boolean canExpand) {
        String label = activity.getString(R.string.read_more);
        CharSequence mainText = textView.getText();
        textView.post(() -> {
            Layout l = textView.getLayout();
            if (l != null) {
                if (maxLines > 0) {
                    textView.setMaxLines(maxLines);
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                }
                int lines = l.getLineCount();
                if (lines > 1)
                    if ((l.getEllipsisCount(lines - 1) > 0 || lines > maxLines)) {
                        int start = l.getLineStart(0);
                        int end = l.getLineEnd((lines > maxLines ? maxLines : lines) - 2);
                        String displayed = textView.getText().toString().substring(start, end);
                        int displayedWidth = getTextWidth(displayed, textView.getTextSize());
                        int textWidth;
                        String newText = displayed;
                        textWidth = getTextWidth(newText + label, textView.getTextSize());

                        int widthDevice = textView.getResources().getDisplayMetrics().widthPixels;

                        while (textWidth > displayedWidth && newText.length() > 0 && textWidth > widthDevice / 2) {
                            newText = newText.substring(0, newText.length() - 1).trim();
                            textWidth = getTextWidth(newText + label, textView.getTextSize());
                        }

                        SpannableString mySpannable;
                        String finalNewText = newText;
                        ClickableSpan profileClickableSpan = new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                if (canExpand) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                        if (maxLines > 0 && textView.getMaxLines() != Integer.MAX_VALUE) {
                                            textView.setEllipsize(null);
                                            textView.setMaxLines(Integer.MAX_VALUE);
                                            SpannableString mySpannable = new SpannableString(mainText);
                                            mySpannable.setSpan(this, 0, mySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            textView.setText(mySpannable);
                                        } else {
                                            textView.setEllipsize(TextUtils.TruncateAt.END);
                                            textView.setMaxLines(maxLines);
                                            SpannableString mySpannable = new SpannableString(finalNewText + label);
                                            mySpannable.setSpan(this, 0, mySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            mySpannable.setSpan(new StyleSpan(Typeface.BOLD), mySpannable.length() - label.length(), mySpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                            textView.setText(mySpannable);
                                        }
                                    } else if (maxLines > 0) {
                                        textView.setEllipsize(null);
                                        textView.setMaxLines(Integer.MAX_VALUE);
                                        SpannableString mySpannable = new SpannableString(mainText);
                                        mySpannable.setSpan(this, 0, mySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        textView.setText(mySpannable);
                                    }
                                }
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setColor(activity.getResources().getColor(R.color.color_000000));
                                ds.setUnderlineText(false);
                                ds.setFakeBoldText(false);
                            }
                        };

                        mySpannable = new SpannableString(newText + label);
                        mySpannable.setSpan(profileClickableSpan, 0, mySpannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        mySpannable.setSpan(new StyleSpan(Typeface.BOLD), mySpannable.length() - label.length(), mySpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textView.setText(mySpannable);
                        textView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
            }
        });
    }

    private static int getTextWidth(String text, float textSize) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = (int) Math.ceil(bounds.width());
        return width;
    }

//    public static Spannable stripUnderlines(TextView textView, Spannable spannedText, Activity activity, boolean shouldOpenYoutubeLink, boolean calledFromComment, boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon) {
//        URLSpan[] spans = spannedText.getSpans(0, spannedText.length(), URLSpan.class);
//        for (URLSpan span : spans) {
//            int start = spannedText.getSpanStart(span);
//            int end = spannedText.getSpanEnd(span);
//            spannedText.removeSpan(span);
//            span = new URLSpanNoUnderline(span.getURL(), activity, shouldOpenYoutubeLink, calledFromComment, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);
//            spannedText.setSpan(span, start, end, 0);
//        }
//        return spannedText;
//    }

//    private static class URLSpanNoUnderline extends URLSpan {
//        private final Activity activity;
//        private final boolean shouldOpenYoutubeLink;
//        private final boolean calledFromComment;
//        private final boolean shouldShowImageDownloadIcon;
//        private final boolean shouldShowImageRotateIcon;
//
//
//        public URLSpanNoUnderline(String url, Activity activity, boolean shouldOpenYoutubeLink, boolean calledFromComment, boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon) {
//            super(url);
//            this.activity = activity;
//            this.shouldOpenYoutubeLink = shouldOpenYoutubeLink;
//            this.calledFromComment = calledFromComment;
//            this.shouldShowImageDownloadIcon = shouldShowImageDownloadIcon;
//            this.shouldShowImageRotateIcon = shouldShowImageRotateIcon;
//        }
//
//        @Override
//        public void updateDrawState(TextPaint ds) {
//            super.updateDrawState(ds);
//            ds.setUnderlineText(false);
//            ds.setFakeBoldText(true);
//            ds.setColor(activity.getResources().getColor(R.color.color_000000));
//        }
//
//        @Override
//        public void onClick(View widget) {
//            String url = getURL();
//
//            final Context context = widget.getContext();
//            if (shouldOpenYoutubeLink && (url.contains("//youtu.be/") || url.contains("youtube.com/"))) {
//                Pattern compiledYoutubePattern = Pattern.compile(Constants.YOUTUBE_PATTERN, Pattern.CASE_INSENSITIVE);
//                Matcher youtubeMatcher = compiledYoutubePattern.matcher(url);
//
//                boolean b = youtubeMatcher.find();
//                if (b) {
//                    int startTime = AppHelper.getStartTime(youtubeMatcher);
//                    final String videoId = youtubeMatcher.group(1);
//                    try {
//                        Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(activity, AuthConstants.YOUTUBE_DEV_KEY, videoId, startTime, true, false);
//                        activity.startActivity(videoIntent);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        handleDeepLink(url, context);
//                    }
//                } else {
//                    handleDeepLink(url, context);
//                }
//                return;
//            }
//
//            if (url.endsWith(".png") || url.endsWith(".jpg")) {
//                context.startActivity(ImageActivity.getIntent(context, url, !shouldShowImageDownloadIcon, 0, 0, false,
//                        false, shouldShowImageRotateIcon));
//            } else {
//                handleDeepLink(url, context);
//            }
//        }
//
//        private void handleDeepLink(String url, Context context) {
//            Intent intent = new Intent();
//            intent.putExtra("deepLinkData", url);
//            try {
//                new DeepLinkHelper(activity).handleDeeplink(context, url, false, null);
//            } catch (Exception e) {
//
//            }
//        }
//    }

//    public static boolean addLinks(String text, SpannableString spannableString, Pattern[]
//            patterns, String[] scheme, Activity activity, boolean shouldOpenYoutubeLink, boolean calledFromComment,
//                                   boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon) {
//        boolean hasLinks = false;
//
//        for (Pattern p : patterns) {
//            boolean tagMatch = p.equals(Constants.TAGGED_USER);
//
//            Matcher m = tagMatch ? p.matcher(text) : p.matcher(spannableString);
//            while (m.find()) {
//                int start = m.start();
//                int end = m.end();
//                String url = makeUrl(m.group(0), scheme, tagMatch);
//                if (!tagMatch)
//                    applyLink(url, start, end, spannableString, activity, shouldOpenYoutubeLink, calledFromComment, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);
//                hasLinks = true;
//            }
//        }
//        return hasLinks;
//    }

    private static String makeUrl(String url, String[] prefixes, boolean dontAddPrefix) {
        boolean hasPrefix = false;
        for (String prefixe : prefixes) {
            if (url.regionMatches(true, 0, prefixe, 0,
                    prefixe.length())) {
                hasPrefix = true;
                // Fix capitalization if necessary
                if (!url.regionMatches(false, 0, prefixe, 0,
                        prefixe.length())) {
                    url = prefixe + url.substring(prefixe.length());
                }
                break;
            }
        }
        if (!dontAddPrefix && !hasPrefix) {
            url = prefixes[0] + url;
        }
        return url;
    }

//    private static void applyLink(String url, int start, int end, SpannableString spannableString, Activity activity,
//                                  boolean shouldOpenYoutubeLink, boolean calledFromComment, boolean shouldShowImageDownloadIcon, boolean shouldShowImageRotateIcon) {
//        URLSpan span = new URLSpanNoUnderline(url, activity, shouldOpenYoutubeLink, calledFromComment, shouldShowImageDownloadIcon, shouldShowImageRotateIcon);
//        spannableString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//    }


//    public static Spannable getSpannedText(Activity activity, String text, HashMap<String, ImageMeta> imageMap) {
//        Html.ImageGetter imageGetter = source -> {
//
//            Drawable drawable = null;
//            if (imageMap != null) {
//                ImageMeta map = imageMap.get(source);
//                if (map == null) map = new ImageMeta();
//                if (map.getWidth() == 0) map.setWidth(100);
//                if (map.getHeight() == 0) map.setHeight(100);
//
//                float density = activity.getResources().getDisplayMetrics().density * 2;
//                drawable = BitmapDrawable.createFromPath(getAbsoluteLocalPath(activity, source));
//                if (drawable != null) {
//
//                    int width = drawable.getIntrinsicWidth();
//                    if (width == 0) {
//                        width = map.getWidth();
//                    } else {
//                        map.setWidth(width);
//                    }
//
//                    if (drawable != null) {
//                        int height = drawable.getIntrinsicHeight();
//                        if (height == 0) {
//                            height = map.getHeight();
//                        } else {
//                            map.setHeight(height);
//                        }
//
//                        width = (int) (width * density);
//                        height = (int) (height * density);
//
//                        int screenWidth = AppHelper.getDeviceDimensions(activity).x - AppHelper
//                                .pxFromDp(activity, 32);
//
//                        if (width > screenWidth) {
//                            float aspectRatio = (float) map.getWidth() / map.getHeight();
//                            width = screenWidth;
//                            height = (int) (width / aspectRatio);
//                        }
//
//                        drawable.setBounds(0, 0, width, height);
//                    }
//                }
//            }
//            if (drawable == null)
//                drawable = activity.getResources().getDrawable(R.drawable.save_image_icon);
//
//            if (SharedPreferencesHelper.getNightModeStatus())
//                drawable.setColorFilter(new ColorMatrixColorFilter(AppHelper.NEGATIVE));
//            else
//                drawable.setColorFilter(null);
//
//
//            return drawable;
//        };
//
//        return (Spannable) Html.fromHtml(text, imageGetter, null);
//
//    }

    private static String getAbsoluteLocalPath(Activity activity, String source) {
        return activity.getFilesDir().getAbsolutePath() + "/" + getLocalFileName(source);
    }

    static String getLocalFileName(String source) {
        // fromIndex 10 to skip "//" after protocol
        int i = source.indexOf("/", 10);
        return source.substring(i + 1).replaceAll("/", "-");
    }

//    public static HashMap<String, ImageMeta> getImageMetaMap(String text) {
//        HashMap<String, ImageMeta> imageMap = new HashMap<>();
//        if (text == null) return imageMap;
//        Pattern pattern = Pattern.compile("<img([^>]*)src[^'\"]*['\"]([^'\"]*)['\"][^>]*>");
//        Matcher matcher = pattern.matcher(text);
//        int index = 0;
//        while (matcher.find()) {
//            ImageMeta imageMetaTO = new ImageMeta();
//            try {
//                imageMetaTO.setHeight(10);
//                imageMetaTO.setWidth(10);
//            } catch (RuntimeException e) {
//                e.printStackTrace();
//                break;
//            }
//            index++;
//            imageMap.put(matcher.group(2), imageMetaTO);
//        }
//        return imageMap;
//    }

//    static HashMap<String, ImageMeta> getImageMapForQuestions(List<Question> questions) {
//        HashMap<String, ImageMeta> imageMetaMap = new HashMap<>();
//        for (Question q : questions) {
//            imageMetaMap.putAll(getImageMetaMap(q.getQuestionText()));
//            imageMetaMap.putAll(getImageMetaMap(q.getCommonContent()));
//            imageMetaMap.putAll(getImageMetaMap(q.getSolution()));
//            if (q.getOptions() != null && q.getOptions().size() > 0)
//                for (QuestionOption o : q.getOptions()) {
//                    imageMetaMap.putAll(getImageMetaMap(o.getOptionText()));
//                }
//        }
//        return imageMetaMap;
//    }

//    static Map<String, ImageMeta> getImageMapForPYSPQuestions(ArrayList<PYSPQuestion> p) {
//        HashMap<String, ImageMeta> imageMetaMap = new HashMap<>();
//        for (PYSPQuestion q : p) {
//            imageMetaMap.putAll(getImageMetaMap(q.getQuestionText()));
//            imageMetaMap.putAll(getImageMetaMap(q.getCommonText()));
//            imageMetaMap.putAll(getImageMetaMap(q.getSolution()));
//            if (q.getOptions() != null && q.getOptions().size() > 0)
//                for (String o : q.getOptions()) {
//                    imageMetaMap.putAll(getImageMetaMap(o));
//                }
//        }
//        return imageMetaMap;
//    }

//    static Map<String, ImageMeta> getImageMapForLiveQuizEntityQuestions(ArrayList<QuestionUnit> p) {
//        HashMap<String, ImageMeta> imageMetaMap = new HashMap<>();
//        for (QuestionUnit q : p) {
//            imageMetaMap.putAll(getImageMetaMap(q.getContent()));
//            imageMetaMap.putAll(getImageMetaMap(q.getAnswer()));
//            imageMetaMap.putAll(getImageMetaMap(q.getSolution()));
//            if (q.getChoices() != null && q.getChoices().size() > 0)
//                for (String o : q.getChoices()) {
//                    imageMetaMap.putAll(getImageMetaMap(o));
//                }
//        }
//        return imageMetaMap;
//    }


//    /**
//     * To get ImageMeta for MockTest ( Questions and Solutions both)
//     *
//     * @param mockEncryptedDataTo : To download images for questions and options
//     * @param solution            : to download images for solution text
//     * @return Map comprising of all the <img> tag
//     */
//    static Map<String, ImageMeta> getImageMapForMockQuestions(MockEncryptedDataTo mockEncryptedDataTo, ArrayList<String> solution) {
//        HashMap<String, ImageMeta> imageMetaMapForMock = new HashMap<>();
//
//        // To download solution text images
//        if (solution != null && solution.size() > 0) {
//            for (String sol : solution) {
//                imageMetaMapForMock.putAll(getImageMetaMap(sol));
//            }
//        }
//
//        //To download Questions, Common text and Options text images
//        ArrayList<MockQuestionTo> mockQuestionTos = new ArrayList<>();
//
//        for (MockSectionTo mockSectionTo : mockEncryptedDataTo.getData().getMockSections()) {
//            mockQuestionTos.addAll(mockSectionTo.getMockQuestionTos());
//        }
//
//        for (MockQuestionTo q : mockQuestionTos) {
//
//            if (q.getSupportedLanguageMap() != null
//                    && q.getSupportedLanguageMap().size() > 1
//                    && q.getQuestionLanguageDataToHashMap() != null) {
//                for (String key : q.getSupportedLanguageMap().keySet()) {
//
//                    imageMetaMapForMock.putAll(getImageMetaMap(q.getQuestionLanguageDataToHashMap().get(key).getText()));
//                    imageMetaMapForMock.putAll(getImageMetaMap(q.getQuestionLanguageDataToHashMap().get(key).getCommonText()));
//
//                    if (q.getQuestionLanguageDataToHashMap().get(key).getOptions() != null && q.getQuestionLanguageDataToHashMap().get(key).getOptions().size() > 0)
//                        for (String o : q.getQuestionLanguageDataToHashMap().get(key).getOptions()) {
//                            imageMetaMapForMock.putAll(getImageMetaMap(o));
//                        }
//                }
//            } else {
//                imageMetaMapForMock.putAll(getImageMetaMap(q.getQuestionText()));
//                imageMetaMapForMock.putAll(getImageMetaMap(q.getCommonText()));
//                if (q.getMockOptionTos() != null && q.getMockOptionTos().size() > 0)
//                    for (String o : q.getMockOptionTos()) {
//                        imageMetaMapForMock.putAll(getImageMetaMap(o));
//                    }
//
//            }
//
////                if(q.getSupportedLanguageMap().size()>=2) {
////                    imageMetaMapForMock.putAll(getImageMetaMap(q.getQuestionLanguageDataToHashMap().get(q.getSupportedLanguageMap().get(1)).getText()));
////                    imageMetaMapForMock.putAll(getImageMetaMap(q.getQuestionLanguageDataToHashMap().get(q.getSupportedLanguageMap().get(1)).getCommonText()));
////                    if (q.getMockOptionTos() != null && q.getMockOptionTos().size() > 0)
////                        for (String o : q.getQuestionLanguageDataToHashMap().get(q.getSupportedLanguageMap().get(1)).getOptions()) {
////                            imageMetaMapForMock.putAll(getImageMetaMap(o));
////                        }
////                }
//
//        }
//        return imageMetaMapForMock;
//    }

}
