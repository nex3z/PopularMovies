package com.nex3z.popularmovies.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieInfoPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieInfoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieInfoFragment extends Fragment implements MovieInfoView {
    private static final String LOG_TAG = MovieInfoFragment.class.getSimpleName();

    private static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.tv_movie_title)
    TextView mTvMovieTitle;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.iv_poster)
    ImageView mIvPoster;
    @BindView(R.id.tv_overview)
    TextView mOverview;

    private MovieModel mMovie;
    private MovieInfoPresenter mPresenter;

    public MovieInfoFragment() {}

    public static MovieInfoFragment newInstance(Parcelable parcelable) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieInfoFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_MOVIE_INFO)) {
                mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
                Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadMovieInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void renderMovie(MovieModel movie) {
        mTvMovieTitle.setText(movie.getTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mOverview.setText(movie.getOverview());

        Picasso.with(getContext())
                .load(movie.getPosterImageUrl())
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(mIvPoster);
    }

    private void initialize() {
        mPresenter = new MovieInfoPresenter(mMovie);
        mPresenter.setView(this);

    }

    private void loadMovieInfo() {
        if (mPresenter != null) {
            mPresenter.initialize();
        }
    }

}
