package com.nex3z.popularmovies.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.ReviewModel;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private static OnItemClickListener mListener;
    private List<ReviewModel> mReviews;

    public interface OnItemClickListener {
        void onItemClick(int position, ReviewAdapter.ViewHolder vh);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.review_item, parent, false);

        return new ViewHolder(contactView);
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setReviewsCollection(Collection<ReviewModel> reviewCollection) {
        validateReviewCollection(reviewCollection);
        mReviews = (List<ReviewModel>) reviewCollection;
        notifyDataSetChanged();
    }

    private void validateReviewCollection(Collection<ReviewModel> reviewCollection) {
        if (reviewCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = ViewHolder.class.getSimpleName();
        @BindView(R.id.tv_review_author) public TextView mTvAuthor;
        @BindView(R.id.tv_review_content) public TextView mTvContent;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(LOG_TAG, "onClick(): mListener = " + mListener);
                    if (mListener != null)
                        mListener.onItemClick(getLayoutPosition(), ViewHolder.this);
                }
            });
        }
    }
}
