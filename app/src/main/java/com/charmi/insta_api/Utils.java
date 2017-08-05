package com.charmi.insta_api;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by ADMIN on 8/5/2017.
 */

public final class Utils {

    /** Tag for the log messages */
    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static ArrayList<Posts> fetchTweetData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Earthquake} object
        ArrayList<Posts> posts = extractFeatureFromJson(jsonResponse);

        // Return the {@link Earthquake}
        return posts;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the posts JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }


        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static ArrayList<Posts>  extractFeatureFromJson(String postJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(postJSON)) {

            //Toast.makeText(this , "null json string", Toast.LENGTH_SHORT).show();
            return null;
        }
        ArrayList<Posts> posts = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(postJSON);
            JSONArray feed = baseJsonResponse.getJSONArray("posts");

            JSONObject c = feed.getJSONObject(0);
            JSONObject insta = c.getJSONObject("instagram");
            JSONArray profile = insta.getJSONArray("profile_posts");

            JSONObject f = profile.getJSONObject(0);

            JSONObject entry =f.getJSONObject("entry_data");
            JSONArray ProfilePage = entry.getJSONArray("ProfilePage");
            JSONObject d= ProfilePage.getJSONObject(0);
            JSONObject user = d.getJSONObject("user");

            JSONObject media = user.getJSONObject("media");
            JSONArray nodes = media.getJSONArray("nodes");

            for(int i=0;i<nodes.length();i++)
            {
                JSONObject ca = nodes.getJSONObject(i);


                    String text= ca.getString("caption");
                    String time = ca.getString("date");
                    posts.add(new Posts(time, text));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the posts JSON results", e);
        }
        return posts;
    }


}