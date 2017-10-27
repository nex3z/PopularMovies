package com.nex3z.popularmovies.presentation.detail.info;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.util.GenreUtil;
import com.nex3z.popularmovies.presentation.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieInfoFragment extends Fragment {
    private static final String LOG_TAG = MovieInfoFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.sdv_movie_info_poster) SimpleDraweeView mSdvPoster;
    @BindView(R.id.tv_item_movie_vote_average) TextView mTvVoteAverage;
    @BindView(R.id.tv_item_movie_release_date) TextView mTvReleaseDate;
    @BindView(R.id.tv_item_movie_genre) TextView mTvGenre;
    @BindView(R.id.tv_movie_info_overview) TextView mTvOverview;

    private MovieModel mMovie;
    private Unbinder mUnbinder;

    public MovieInfoFragment() {}

    public static MovieInfoFragment newInstance(MovieModel movie) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
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
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void init() {
        renderMovie(mMovie);
    }

    private void renderMovie(MovieModel movie) {
        mTvVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        mTvReleaseDate.setText(movie.getReleaseDate());
        mTvGenre.setText(GenreUtil.getGenre(getContext(), movie.getGenreIds()));
        mTvOverview.setText(movie.getOverview());
        ViewUtil.loadProgressiveImage(mSdvPoster, movie.getPosterUrl(MovieModel.POSTER_SIZE_W342));
    }
}
