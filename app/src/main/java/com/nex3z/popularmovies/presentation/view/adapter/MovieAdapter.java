package com.nex3z.popularmovies.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.util.ImageUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private static OnPosterClickListener mPosterListener;
    private static OnFavouriteClickListener mFavouriteListener;
    private List<MovieModel> mMovies;

    public interface OnPosterClickListener {
        void onClick(int position, MovieAdapter.ViewHolder vh);
    }

    public interface OnFavouriteClickListener {
        void onClick(int position, MovieAdapter.ViewHolder vh);
    }

    public MovieAdapter(List<MovieModel> movies) {
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movieModel = mMovies.get(position);
        holder.mTvTitle.setText(movieModel.getTitle());

        String url = ImageUtility.getPosterImageUrl(movieModel.getPosterPath());

        Picasso.with(holder.itemView.getContext())
                .load(url)
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(holder.mIvPoster);
        updateFavouriteButtonIcon(holder.mIBtnFavourite, movieModel.isFavourite());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setOnPosterClickListener(OnPosterClickListener listener) {
        mPosterListener = listener;
    }

    public void setOnFavouriteClickListener(OnFavouriteClickListener listener) {
        mFavouriteListener = listener;
    }

    public void setMovies(List<MovieModel> movies) {
        validateMovies(movies);
        mMovies = movies;
        notifyDataSetChanged();
    }

    private void validateMovies(List<MovieModel> movies) {
        if (movies == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    private void updateFavouriteButtonIcon(ImageButton favouriteButton, boolean isFavourite) {
        if (isFavourite) {
            favouriteButton.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            favouriteButton.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = ViewHolder.class.getSimpleName();
        @BindView(R.id.iv_poster) public ImageView mIvPoster;
        @BindView(R.id.tv_title) TextView mTvTitle;
        @BindView(R.id.ibtn_favourite) ImageButton mIBtnFavourite;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mIvPoster.setOnClickListener(v -> {
                if (mPosterListener != null)
                    mPosterListener.onClick(getLayoutPosition(), ViewHolder.this);
            });

            mIBtnFavourite.setOnClickListener(v -> {
                if (mFavouriteListener != null)
                    mFavouriteListener.onClick(getLayoutPosition(), ViewHolder.this);
            });
        }
    }

}
