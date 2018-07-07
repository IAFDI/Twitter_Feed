package com.codepath.apps.restclienttemplate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Transformer;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    Context context;
    ProgressBar pb;

    //pass in the Tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    // for each row, inflate the layout and cache references into ViewHolder class
    @NonNull
    @Override
    public TweetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    //bind values based on the position of the element
    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get data according to position
        Tweet tweet = mTweets.get(position);

        //populate the views
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvTimeAgo.setText(tweet.timeAgo);

        int radius = 50; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transform( new RoundedCornersTransformation(radius, margin)))
                .into(holder.tvProfileImage);

        if (tweet.imageURL.equals("N/A")){
//            holder.tvBodyImage.setVisibility(View.INVISIBLE);
//            holder.tvBodyImage.setMaxHeight(1);
        }else{
//            holder.tvBodyImage.setMaxHeight(94);
//            holder.tvBodyImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                .load(tweet.imageURL)
                .apply(new RequestOptions().transform( new RoundedCornersTransformation(30, 10)))
                .into(holder.tvBodyImage);
        }

    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView tvProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvTimeAgo;
        public ImageView tvBodyImage;

        public ViewHolder(View itemView) {
            super(itemView);

            //perform view by id lookups
            tvProfileImage = (ImageView) itemView.findViewById(R.id.tvProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvTimeAgo = (TextView) itemView.findViewById(R.id.tvTimeAgo);
            tvBodyImage = (ImageView) itemView.findViewById(R.id.bodyImage);

        }
    }

}

