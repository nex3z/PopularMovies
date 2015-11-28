package com.nex3z.popularmovies.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Review;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private List<Review> mReviews;

    public ReviewAdapter(List<Review> reviews) {
        Log.v(LOG_TAG, "ReviewAdapter(): reviews size = " + reviews.size());
        mReviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        Log.v(LOG_TAG, "onCreateViewHolder(): viewHolder = " + viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.v(LOG_TAG, "onBindViewHolder(): position = " + position);
        Review review = mReviews.get(position);

        holder.authorView.setText(review.getAuthor());
        holder.contentView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.review_author_textview)
        TextView authorView;
        @Bind(R.id.review_content_textview)
        TextView contentView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.v(LOG_TAG, "ViewHolder(): Bind complete.");
        }
    }
}
