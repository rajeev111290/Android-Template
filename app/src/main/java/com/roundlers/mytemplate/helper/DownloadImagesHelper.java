package com.roundlers.mytemplate.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.roundlers.mytemplate.R;
import com.roundlers.mytemplate.models.ImageMeta;
import com.roundlers.mytemplate.models.events.ImageDownloadComplete;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by abhayalekal on 14/01/18.
 */
public class DownloadImagesHelper {

    private final ExecutorService executorService;
    private Context context;
    private HashMap<String, ImageMeta> imageMetaMap;
    private float density;
    private int imagesToBeDownloaded;
    private PublishSubject<Integer> downloadedImagesPublishSubject;
//    private DownloadImagesDialog downloadImagesDialog;
    private List d;
    private int direction;
    private boolean notifyAll;
    private int scrollToIndex;
    private String feedId;
    private int errorCount, currentDownloadNumerator;
    private Map<String, ImageMeta> downloadingImagesFromMap;
    private PublishSubject<Boolean> isImageDownloaded;
    public boolean downloadFinishedCalled;

    public HashMap<String, ImageMeta> getImageMetaMap() {
        return imageMetaMap;
    }

    @Inject
    public DownloadImagesHelper(Activity context) {
        this.context = context;
        imageMetaMap = new HashMap<>();
        downloadedImagesPublishSubject = PublishSubject.create();
//        downloadImagesDialog = new DownloadImagesDialog(context);
        downloadFinishedCalled = false;
        density = context.getResources().getDisplayMetrics().density;
        executorService = Executors.newFixedThreadPool(5);
    }

    private void downloadImages(Map<String, ImageMeta> map) {

        downloadingImagesFromMap = map;
        currentDownloadNumerator = 0;
        imagesToBeDownloaded = map.size();
        downloadFinishedCalled = false;
        errorCount = 0;
//        downloadImagesDialog.show();

        if (map.size() == 0) {
            downloadFinished();
        } else {
            Completable.create(e -> glideDownload(map))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    public void downloadImagesFromTVH(Map<String, ImageMeta> map, PublishSubject<Boolean> isImageDownloaded) {


        downloadingImagesFromMap = map;
        this.isImageDownloaded = isImageDownloaded;
        currentDownloadNumerator = 0;
        imagesToBeDownloaded = map.size();
        downloadFinishedCalled = false;
        errorCount = 0;
//        downloadImagesDialog.show();

        if (map.size() != 0) {
            Completable.create(e -> glideDownload(map))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        }
    }

    private void downloadFinished() {
        if (downloadFinishedCalled) return;

        downloadFinishedCalled = true;

        if (isImageDownloaded != null) {
            isImageDownloaded.onNext(true);
            isImageDownloaded.onComplete();
        }

        EventbusHelper.post(new ImageDownloadComplete(true, d, direction, notifyAll, scrollToIndex));

//        try {
//            if (!((Activity) context).isFinishing() && downloadImagesDialog.isShowing())
//                downloadImagesDialog.dismiss();
//        } catch (Exception e) {
//            e.printStackTrace();
//            downloadImagesDialog.dismiss();
//        }

        if (errorCount == 0)
            downloadingImagesFromMap = null;
    }

    private void glideDownload(Map<String, ImageMeta> imageMap) {
        int i = 0;

        for (Map.Entry<String, ImageMeta> entry : imageMap.entrySet()) {
            executorService.execute(new DownloadImageRunnable(entry, ++i));
        }
    }

//    public void questionsLoaded(List<Question> d, int direction, boolean notifyAll, int scrollToIndex, String feedId) {
//        this.d = d;
//        this.direction = direction;
//        this.notifyAll = notifyAll;
//        this.scrollToIndex = scrollToIndex;
//        this.feedId = feedId;
//        downloadImages(TextViewHelper.getImageMapForQuestions(d));
//    }


//    public void questionsLoaded(ArrayList<PYSPQuestion> p) {
//        this.d = p;
//        this.direction = Constants.Direction.DOWN;
//        this.notifyAll = true;
//        this.scrollToIndex = -1;
//        downloadImages(TextViewHelper.getImageMapForPYSPQuestions(p));
//    }

//    public void questionLoaded(ArrayList<QuestionUnit> p) {
//        this.d = p;
//        this.direction = Constants.Direction.DOWN;
//        this.notifyAll = true;
//        this.scrollToIndex = -1;
//        downloadImages(TextViewHelper.getImageMapForLiveQuizEntityQuestions(p));
//    }

//    /**
//     * Call this method from DownloadImageHelper instance to download MockTest Images
//     *
//     * @param mockEncryptedDataTo : For mock Question, common text and option text images
//     * @param solution            : For solution text images
//     */
//    public void downloadMockTestImages(MockEncryptedDataTo mockEncryptedDataTo, ArrayList<String> solution) {
//        this.direction = Constants.Direction.DOWN;
//        this.notifyAll = true;
//
//        // This is random number which is checked in MockTestActivity for event posted after completion
//        // ( Distinguishing number to track mock images download completion)
//        this.scrollToIndex = 555;
//        downloadImages(TextViewHelper.getImageMapForMockQuestions(mockEncryptedDataTo, solution));
//    }

//    private class DownloadImagesDialog extends Dialog {
//        private TextView status, percentage;
//        private ProgressBar progressBar;
//
//        DownloadImagesDialog(@NonNull Context context) {
//            super(context, R.style.PopUpBackgroundStyle);
//            this.requestWindowFeature(1);
//            View view = View.inflate(context, R.layout.download_images_dialog_layout, null);
//            setContentView(view);
//            setViews(view);
//        }
//
//        private void setViews(View view) {
//            status = view.findViewById(R.id.status);
//            percentage = view.findViewById(R.id.percentage);
//            progressBar = view.findViewById(R.id.progress);
//            progressBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.gradeup_green), android.graphics.PorterDuff.Mode.SRC_IN);
//            view.findViewById(R.id.image);
//
////            AppHelper.setNightModeTransform(context, view.findViewById(R.id.image), context.getResources().getDrawable(R.drawable.clock_icon_for_quiz_landing));
//
//            downloadedImagesPublishSubject.subscribe(integer ->
//                    ((Activity) context).runOnUiThread(() -> {
//
//                        if (integer < imagesToBeDownloaded && integer > currentDownloadNumerator) {
//
//                            currentDownloadNumerator = integer;
//
//                            int progress = (int) (((float) integer / imagesToBeDownloaded) * 100);
//                            if (progress > progressBar.getProgress())
//                                progressBar.setProgress(progress);
//
//                            percentage.setText(String.format(Locale.ENGLISH, "(%d/%d)",
//                                    integer, imagesToBeDownloaded));
//                        }
//                    }));
//        }
//
//        @Override
//        public void show() {
//            try {
//                HashMap<String, String> map = new HashMap<>();
//                if (((Activity) context) instanceof TestActivity) {
//                    map.put("postId", feedId);
//                    map.put("source", "test");
//                } else {
//                    map.put("source", "practice");
//                }
//                FirebaseAnalyticsHelper.sendEvent(context, "Downloader_Launched", map);
//                progressBar.setProgress(0);
//                if (!(context instanceof BookmarkActivityWithFilters) || !((BookmarkActivityWithFilters) context).isFinishing()) {
//                    super.show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void setOnCancelListener(@Nullable OnCancelListener listener) {
//            super.setOnCancelListener(listener);
////            HashMap<String, String> map = new HashMap<>();
////            if (((Activity) context) instanceof TestActivity) {
////                map.put("postId", feedId);
////                map.put("source", "test");
////            } else {
////                map.put("source", "practice");
////            }
////            FirebaseAnalyticsHelper.sendEvent(context, "Downloader_Interrupted", map);
//        }
//    }

    private class DownloadImageRunnable implements Runnable {
        private Map.Entry<String, ImageMeta> entry;
        private int index;

        DownloadImageRunnable(Map.Entry<String, ImageMeta> imageMetaEntry, int i) {
            entry = imageMetaEntry;
            index = i;
        }

        @Override
        public void run() {
            try {
                String localFileName = TextViewHelper.getLocalFileName(entry.getKey());

                if (!GenericHelper.ifFileExists(context, localFileName)) {
                    /*Bitmap bitmap = Glide.with(context)
                            .load(entry.getKey())
                            .asBitmap()
                            .into((int) (entry.getValue().getWidth() * density), (int) (entry.getValue().getHeight() * density))
                            .get();*/
                    URL url = null;
                    url = new URL(entry.getKey());

                    URLConnection conn = url.openConnection();
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
                    GenericHelper.saveQuestionImages(context, bitmap, localFileName);

                }

                downloadedImagesPublishSubject.onNext(index);
                imageMetaMap.put(entry.getKey(), entry.getValue());

            } catch (Exception ignored) {
                ignored.printStackTrace();
                errorCount++;
            } finally {

                if (index >= imagesToBeDownloaded - 3) {

                    ((Activity) context).runOnUiThread(() -> {
                        if (errorCount > 0)
                            LogHelper.showBottomToast(context, R.string.Failed_to_download_all_images_Make_sure_your_internet_is_working);
                    });

                    if (!downloadFinishedCalled)
                        downloadFinished();
                }
            }
        }
    }

    public void onNetworkChanged() {
        if (downloadingImagesFromMap != null && downloadingImagesFromMap.size() > 0 && errorCount > 0) {
            downloadImages(downloadingImagesFromMap);
        }
    }
}
