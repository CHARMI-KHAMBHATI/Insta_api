package com.charmi.insta_api;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Posts>> {

    private static final int LOADER_ID = 1;
    private static String REQUEST_URL ="http://api.loklak.org/api/instagramprofilescraper.json?query=";
    private EditText user_id;

    private ListView postListView;
    private PostAdapter mAdapter;

    private ProgressBar progress;
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_id = (EditText) findViewById(R.id.user_id);
        postListView = (ListView) findViewById(R.id.list);

        empty= (TextView) findViewById(R.id.empty);
        progress= (ProgressBar) findViewById(R.id.loading_spinner);


    }

    public void submit(View v){


        REQUEST_URL = REQUEST_URL + user_id.getText().toString();
        //Toast.makeText(this, "clicked - "+ REQUEST_URL, Toast.LENGTH_SHORT).show();


        mAdapter = new PostAdapter(this, new ArrayList<Posts>());

        postListView.setEmptyView(findViewById(R.id.empty));

        postListView.setAdapter(mAdapter);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);


        } else {

            progress.setVisibility(View.INVISIBLE);
            empty.setText("No Internet Connection");
        }
    }

    @Override
    public Loader<List<Posts>> onCreateLoader(int id, Bundle bundle) {
        return new PostLoader(this, REQUEST_URL);
    }



    @Override
    public void onLoadFinished(Loader<List<Posts>> loader, final List<Posts> posts) {


        progress.setVisibility(View.INVISIBLE);
        mAdapter.clear();
        empty.setText("No posts found");

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (posts != null && !posts.isEmpty()) {

            mAdapter.addAll(posts);

        }



    }



    @Override
    public void onLoaderReset(Loader<List<Posts>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }
}