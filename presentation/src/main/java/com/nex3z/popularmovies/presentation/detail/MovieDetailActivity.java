package com.nex3z.popularmovies.presentation.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.nex3z.popularmovies.presentation.detail.info.MovieInfoFragment;
import com.nex3z.popularmovies.presentation.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE = "arg_movie";

    @BindView(R.id.ctl_movie_detail) CollapsingToolbarLayout mCtlToolBarContainer;
    @BindView(R.id.tb_movie_detail) Toolbar mToolBar;
    @BindView(R.id.sdv_movie_detail_backdrop) SimpleDraweeView mSdvBackdrop;
    @BindView(R.id.btn_movie_detail_play) ImageButton mBtnPlay;

    private MovieModel mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(ARG_MOVIE);
            showMovieInfo();
        } else {
            mMovie = savedInstanceState.getParcelable(ARG_MOVIE);
        }

        renderMovie();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ARG_MOVIE, mMovie);
        super.onSaveInstanceState(outState);
    }

    private void renderMovie() {
        mCtlToolBarContainer.setTitle(mMovie.getTitle());
        ViewUtil.loadProgressiveImage(mSdvBackdrop, 
                mMovie.getBackdropUrl(MovieModel.BACKDROP_SIZE_W780));
    }

    private void showMovieInfo() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ndv_movie_detail_container, MovieInfoFragment.newInstance(mMovie));
        transaction.commit();
    }

}
