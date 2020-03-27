package com.roundlers.mytemplate.helper;

import org.greenrobot.eventbus.EventBus;

public class EventbusHelper {

    /**
     * Register subscriber to receive events
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * Unregister subscriber to receive events
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * Post event
     *
     * @param event
     */
    public static void post(Object event) {
        if (event == null) {
            return;
        }
        EventBus.getDefault().post(event);
    }

    /**
     * Post sticky
     *
     * @param event
     */
    public static void postSticky(Object event) {

        if (event == null) {
            return;
        }

        EventBus.getDefault().postSticky(event);
    }

    /**
     * Remove sticky event
     *
     * @param event
     */
    public static void removeSticky(Object event) {
        if (event == null) {
            return;
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    public static <T> T getStickyEvent(Class<T> type) {
        return EventBus.getDefault().getStickyEvent(type);
    }
}
