package com.nex3z.popularmovies.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.presentation.internal.di.component.MovieDetailComponent;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieInfoPresenter;
import com.nex3z.popularmovies.presentation.ui.MovieInfoView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieInfoFragment extends BaseFragment implements MovieInfoView {
    private static final String LOG_TAG = MovieInfoFragment.class.getSimpleName();

    private static final String ARG_MOVIE_INFO = "arg_movie_info";

    @BindView(R.id.tv_movie_title) TextView mTvMovieTitle;
    @BindView(R.id.tv_release_date) TextView mReleaseDate;
    @BindView(R.id.iv_poster) ImageView mIvPoster;
    @BindView(R.id.tv_overview) TextView mOverview;

    @Inject MovieInfoPresenter mPresenter;

    private Unbinder mUnbinder;

    public MovieInfoFragment() {}

    public static MovieInfoFragment newInstance(Parcelable parcelable) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieInfoFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected boolean onInjectView() throws IllegalStateException {
        getComponent(MovieDetailComponent.class).inject(this);
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_info, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void onViewInjected(Bundle savedInstanceState) {
        super.onViewInjected(savedInstanceState);
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
        mTvMovieTitle.setText(movie.getTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mOverview.setText(movie.getOverview());

        Picasso.with(getContext())
                .load(movie.getPosterImageUrl())
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

    private void initialize() {
        mPresenter.setView(this);

    }

    private void loadMovieInfo() {
        if (mPresenter != null) {
            mPresenter.initialize();
        }
    }

}
