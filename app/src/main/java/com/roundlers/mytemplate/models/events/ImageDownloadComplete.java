package com.roundlers.mytemplate.models.events;

import java.util.List;

/**
 * Created by abhayalekal on 15/01/18.
 */

public class ImageDownloadComplete {
    private final List d;
    private final int direction;
    private final boolean notifyAll;
    private boolean successful;
    private int scrollToIndex;

    public ImageDownloadComplete(boolean c, List d, int direction, boolean notifyAll, int scrollToIndex) {
        successful = c;
        this.d = d;
        this.direction = direction;
        this.notifyAll = notifyAll;
        this.scrollToIndex = scrollToIndex;
    }

    public List getD() {
        return d;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isNotifyAll() {
        return notifyAll;
    }

    public int getScrollToIndex() {
        return scrollToIndex;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
