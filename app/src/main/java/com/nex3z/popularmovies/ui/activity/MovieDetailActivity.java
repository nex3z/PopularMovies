package com.nex3z.popularmovies.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.model.Video;
import com.nex3z.popularmovies.data.rest.model.VideoResponse;
import com.nex3z.popularmovies.data.rest.service.VideoService;
import com.nex3z.popularmovies.ui.fragment.MovieDetailFragment;
import com.nex3z.popularmovies.util.ImageUtility;
import com.nex3z.popularmovies.util.VideoUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieDetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    public static final String MOVIE_INFO = "MOVIE_INFO";

    private Movie mMovie;
    private List<Video> mVideos = new ArrayList<Video>();

    @Bind(R.id.detail_backdrop_image) ImageView mBackdropImage;
    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout mAppBarLayout;
    @Bind(R.id.detail_toolbar) Toolbar mToolbar;
    @Bind(R.id.detail_play_btn) ImageButton mPlayBtn;
    @Bind(R.id.detail_coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            mMovie = getIntent().getParcelableExtra(MOVIE_INFO);
            Log.v(LOG_TAG, "onCreate(): savedInstanceState == null, movie = " + mMovie);

            MovieDetailFragment fragment = MovieDetailFragment.newInstance(mMovie);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
            updateMovieInfo(mMovie);
        }

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Video video = mVideos.get(0);
                if (video.getKey() != null) {
                    VideoUtility.playVideo(MovieDetailActivity.this,
                            video.getSite(), video.getKey());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieGridActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public class SectionPagerAdapter extends FragmentPagerAdapter {
//
//        final int PAGE_COUNT = 3;
//        private String tabTitles[] = new String[]{
//                getString(R.string.tab_movie_detail),
//                getString(R.string.tab_movie_video),
//                getString(R.string.tab_movie_review)};
//
//        public SectionPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position) {
//                case 0: {
//                    Bundle arguments = new Bundle();
//                    arguments.putParcelable(MovieDetailFragment.ARG_MOVIE_INFO,
//                            getIntent().getParcelableExtra(MOVIE_INFO));
//
//                    MovieDetailFragment fragment = new MovieDetailFragment();
//                    fragment.setArguments(arguments);
//
//                    return fragment;
//                }
//                case 1: {
//                    return VideoFragment.newInstance(mMovie.getId());
//                }
//                case 2: {
//                    return ReviewFragment.newInstance(mMovie.getId());
//                }
//                default:
//                    return new MovieDetailFragment();
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return PAGE_COUNT;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return tabTitles[position];
//        }
//    }

    private void updateMovieInfo(Movie movie) {
        mAppBarLayout.setTitle(mMovie.getTitle());

        String url = ImageUtility.getImageUrl(movie.getBackdropPath());
        Log.v(LOG_TAG, "updateBackdropImage(): backdrop url = " + url
                + ", mBackdropImage = " + mBackdropImage);
        Picasso.with(this).load(url).into(mBackdropImage);
        fetchVideos(movie.getId());
    }

    public void fetchVideos(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        VideoService service = App.getRestClient().getVideoService();
        service.getVideos(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processVideoResponse(response),
                        throwable -> Snackbar.make(
                                mCoordinatorLayout,
                                throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG
                        ).show()
                );
    }

    private void processVideoResponse(VideoResponse response) {
        List<Video> videos = response.getVideos();
        if (videos.size() != 0) {
            Log.v(LOG_TAG, "processVideoResponse(): videos size = " + videos.size());
            mVideos.addAll(videos);
            Log.v(LOG_TAG, "processVideoResponse(): mVideos size = " + mVideos.size());
            for(Video video : mVideos) {
                Log.v(LOG_TAG, "processVideoResponse(): video key = " + video.getKey()
                        + ", name = " + video.getName());
            }
            Log.v(LOG_TAG, "processVideoResponse(): size = " + response.getVideos().size());
            mPlayBtn.setVisibility(View.VISIBLE);
        }
    }
}
