package com.nex3z.popularmovies.presentation.discover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieClickListener mListener;
    private int mItemHeight;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = mMovies.get(position);
        holder.mTvTitle.setText(movie.getTitle());
        holder.mSdvPoster.setImageURI(movie.getPosterUrl(MovieModel.POSTER_SIZE_W342));
    }

    @Override
    public int getItemCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    public void setMovies(List<MovieModel> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_movie_poster) SimpleDraweeView mSdvPoster;
        @BindView(R.id.tv_item_movie_title) TextView mTvTitle;
        @BindView(R.id.cb_item_movie_favourite) CheckBox mCbFavourite;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.sdv_item_movie_poster)
        void onPosterClick() {
            if (mListener != null) {
                mListener.onMovieClick(getAdapterPosition());
            }
        }

        @OnClick(R.id.cb_item_movie_favourite)
        void onFavouriteClick() {
            if (mListener != null) {
                mListener.onFavouriteClick(getAdapterPosition());
            }
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(int position);
        void onFavouriteClick(int position);
    }
}
