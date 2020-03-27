package com.roundlers.mytemplate.constants;

public class Constants {

    public static final String YOUTUBE_PATTERN = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";
    public static final int HEADERS_START_INDEX = Integer.MIN_VALUE;
    public static final int FOOTERS_START_INDEX = Integer.MIN_VALUE / 2;
    public static final int EMPTY_BINDER = -18233434;
    public static final int RECYCLER_VIEW_SCROLLED_TO_BOTTOM_OFFSET = 3;
    public static final int SERVER_ERROR = 1;
    public static final int NO_INTERNET = 2;
    public static final int NO_DATA = 3;
    public static final int OPERATION_FAILED = 4;



    public static class Direction {
        public static final int UP = 0;
        public static final int DOWN = 1;
        public static final String BEFORE = "before";
        public static final String AFTER = "next";

        public static String getDirectionString(int dir) {
            switch (dir) {
                case 0:
                    return AFTER;
                case 1:
                    return BEFORE;
                default:
                    return BEFORE;
            }
        }
    }

    public interface ModelType {
        int USER = 1;
        int GENERIC_CARD_DIVIDER = 2;
    }
}
