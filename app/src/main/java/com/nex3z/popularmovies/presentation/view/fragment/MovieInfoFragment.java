package com.nex3z.popularmovies.presentation.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieInfoPresenter;
import com.nex3z.popularmovies.presentation.util.GenreUtility;
import com.nex3z.popularmovies.presentation.util.ImageUtility;
import com.nex3z.popularmovies.presentation.view.MovieInfoView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieInfoFragment extends BaseFragment implements MovieInfoView {
    private static final String LOG_TAG = MovieInfoFragment.class.getSimpleName();
    private static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.tv_movie_title)TextView mTvMovieTitle;
    @BindView(R.id.tv_release_date) TextView mTvReleaseDate;
    @BindView(R.id.iv_poster) ImageView mIvPoster;
    @BindView(R.id.tv_genre) TextView mTbGenre;
    @BindView(R.id.tv_overview) TextView mTvOverview;

    private Unbinder mUnbinder;
    private MovieInfoPresenter mPresenter;
    private MovieModel mMovie;

    public MovieInfoFragment() {}

    public static MovieInfoFragment newInstance(Parcelable movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_MOVIE_INFO, movie);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_MOVIE_INFO)) {
                mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
            }
        }
        Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
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
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
    }

    @Override
    public void renderMovie(MovieModel movie) {
        Log.v(LOG_TAG, "renderMovie(): movie = " + movie);
        mTvMovieTitle.setText(movie.getTitle());
        mTvReleaseDate.setText(movie.getReleaseDate());
        mTbGenre.setText(GenreUtility.getGenre(getContext(), movie.getGenreIds()));
        mTvOverview.setText(movie.getOverview());

        Picasso.with(getContext())
                .load(ImageUtility.getPosterImageUrl(movie.getPosterPath()))
                .error(R.drawable.placeholder_poster_white)
                .placeholder(R.drawable.placeholder_poster_white)
                .into(mIvPoster, new com.squareup.picasso.Callback() {
                    @Override
                    public void onError() {
                        getActivity().supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onSuccess() {
                        getActivity().supportStartPostponedEnterTransition();
                    }
                });
    }

    private void initPresenter() {
        mPresenter = new MovieInfoPresenter(mMovie);
        mPresenter.setView(this);
        mPresenter.init();
    }
}
