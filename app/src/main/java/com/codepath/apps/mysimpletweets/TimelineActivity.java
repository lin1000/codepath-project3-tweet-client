package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter tweetsAdapter;
    ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.tweetListView);

        tweets = new ArrayList<>();
        tweetsAdapter = new TweetsArrayAdapter(this,tweets);
        lvTweets.setAdapter(tweetsAdapter);

        client = TwitterApplication.getRestClient(); //singleton client

        populateTimeline();
    }

    //send api quest to get tweets
    //populate listview by creating tweets object from json
    private void populateTimeline(){
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            //success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                super.onSuccess(statusCode, headers, json);
                Log.d("DEBUG",json.toString());
                //deserialize
                //create model
                //load into view
                tweetsAdapter.addAll(Tweet.fromJSONArray(json));
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG",errorResponse.toString());
            }
        });
    }
}
