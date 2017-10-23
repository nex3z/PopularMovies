package com.nex3z.popularmovies.presentation.detail.review;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.review.ReviewModel;
import com.nex3z.popularmovies.domain.model.video.VideoModel;
import com.nex3z.popularmovies.presentation.detail.video.VideoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewModel> mReviews;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_review, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewModel review = mReviews.get(position);
        holder.mTvAuthor.setText(review.getAuthor());
        holder.mTvContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviews == null ? 0 : mReviews.size();
    }

    public void setReviews(List<ReviewModel> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_item_review_author) TextView mTvAuthor;
        @BindView(R.id.tv_item_review_content) TextView mTvContent;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
