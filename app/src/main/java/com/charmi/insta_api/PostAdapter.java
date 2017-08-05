package com.charmi.insta_api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ADMIN on 8/5/2017.
 */

class PostAdapter extends ArrayAdapter<Posts>
{
    public PostAdapter(Context context, List<Posts> posts) {
        super(context, 0, posts);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View ListItemView= convertView;
        if(ListItemView==null)
        {
            ListItemView= LayoutInflater.from(getContext()).inflate(R.layout.posts_list_activity, parent,false);
        }

        Posts currentTweet= getItem(position);
        TextView text = (TextView) ListItemView.findViewById(R.id.text);
        text.setText(currentTweet.getMtext());

        TextView time = (TextView) ListItemView.findViewById(R.id.time);
        time.setText(currentTweet.getMtime());


        return ListItemView;
    }
}

