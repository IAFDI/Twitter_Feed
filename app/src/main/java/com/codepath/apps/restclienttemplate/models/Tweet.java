package com.codepath.apps.restclienttemplate.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import com.bumptech.glide.annotation.GlideModule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@org.parceler.Parcel
public class Tweet {

    //list out attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public String timeAgo;
    public String imageURL;
    public boolean hasMedia;

    //deserialize the JSON\
    public Tweet(){

    }

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        //extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        String tempTime = getRelativeTimeAgo(jsonObject.getString("created_at"));
        int index = tempTime.indexOf(" ");
        if (tempTime.contains("minutes") || tempTime.contains("minute")){
            tweet.timeAgo = tempTime.substring(0,index) + "m";
        }else if (tempTime.contains("hours") || tempTime.contains("hour")){
            tweet.timeAgo = tempTime.substring(0,index) + "h";
        }else{
            tweet.timeAgo = tempTime.substring(0,index) + "s";
        }

        JSONArray media = null;


        JSONObject entities = jsonObject.getJSONObject("entities");
        if (entities.has("media")) {
            media = entities.getJSONArray("media");
            tweet.hasMedia = true;
        }
        if(tweet.hasMedia){
            //iterate over the media to find an image;
            for (int i = 0; i < media.length(); i++){
                String type = media.getJSONObject(i).getString("type");
                if(type.equals("photo")){
                    tweet.imageURL = media.getJSONObject(i).getString("media_url");
                }
            }
        }else{
            tweet.imageURL = "N/A";
        }
        return tweet;

//        try{
//            JSONObject whole = jsonObject.getJSONObject("extended_entities");
//            JSONArray media = whole.getJSONArray("media");
//            JSONObject image = media.getJSONObject(0);
//            String x = image.getJSONObject("media_url").toString();
//            tweet.imageURL = image.getJSONObject("media_url").toString();
//        }catch(Exception e){
//            tweet.imageURL = "N/A";
//        }
//
//        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
