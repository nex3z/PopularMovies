package com.nex3z.popularmovies.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.Collection;
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

        View contactView = inflater.inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movieModel = mMovies.get(position);
        holder.mTvTitle.setText(movieModel.getTitle());

        String url = movieModel.getPosterImageUrl();

        Picasso.with(holder.itemView.getContext())
                .load(url)
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(holder.mIvPoster);
        updateFavouriteButtonIcon(holder.mIBtnFavourite, holder.mIsFavourite);;
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

    public void setMovieCollection(Collection<MovieModel> movieCollection) {
        validateMovieCollection(movieCollection);
        mMovies = (List<MovieModel>) movieCollection;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final String LOG_TAG = ViewHolder.class.getSimpleName();
        @BindView(R.id.iv_poster) public ImageView mIvPoster;
        @BindView(R.id.tv_title) public TextView mTvTitle;
        @BindView(R.id.ibtn_favourite) public ImageButton mIBtnFavourite;
        boolean mIsFavourite = false;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mIvPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(LOG_TAG, "onClick(): mPosterListener = " + mPosterListener);
                    if (mPosterListener != null)
                        mPosterListener.onClick(getLayoutPosition(), ViewHolder.this);
                }
            });

            mIBtnFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsFavourite = !mIsFavourite;
                    Log.v(LOG_TAG, "onClick(): mIsFavourite = " + mIsFavourite);
                    updateFavouriteButtonIcon(mIBtnFavourite, mIsFavourite);
                    if (mFavouriteListener != null)
                        mFavouriteListener.onClick(getLayoutPosition(), ViewHolder.this);
                }
            });
        }

        public boolean isFavourite() {
            return mIsFavourite;
        }
    }

    private void updateFavouriteButtonIcon(ImageButton favouriteButton, boolean isFavourite) {
        if (isFavourite) {
            favouriteButton.setImageResource(R.drawable.ic_favorite_24dp);
        } else {
            favouriteButton.setImageResource(R.drawable.ic_favorite_border_24dp);
        }
    }

    private void validateMovieCollection(Collection<MovieModel> movieCollection) {
        if (movieCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

}
