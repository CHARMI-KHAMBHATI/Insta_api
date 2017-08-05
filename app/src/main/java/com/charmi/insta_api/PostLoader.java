package com.charmi.insta_api;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by ADMIN on 8/5/2017.
 */

class PostLoader extends AsyncTaskLoader<List<Posts>> {

    private  String mUrl;



    public PostLoader(Context context, String url)
    {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Posts> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Posts> tweets = Utils.fetchTweetData(mUrl);
        return tweets;
    }
}
