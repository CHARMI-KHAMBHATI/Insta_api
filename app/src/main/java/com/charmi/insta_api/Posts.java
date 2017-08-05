package com.charmi.insta_api;

/**
 * Created by ADMIN on 8/5/2017.
 */

class Posts {
    String mtime, mtext;

    public Posts(String time, String text) {
        mtime = time;
        mtext = text;
    }

    public String getMtime() {
        return mtime;
    }

    public String getMtext() {
        return mtext;
    }
}
