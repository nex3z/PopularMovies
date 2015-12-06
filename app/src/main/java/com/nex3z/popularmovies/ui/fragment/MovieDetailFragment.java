package com.nex3z.popularmovies.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.util.GenreUtility;
import com.nex3z.popularmovies.util.ImageUtility;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "MOVIE_INFO";

    private Movie mMovie;

    //ImageView mBackdropImage;
    //CollapsingToolbarLayout mAppBarLayout;
    @Bind(R.id.detail_title_textview) TextView mTitleView;
    @Bind(R.id.detail_release_date_textview) TextView mReleaseDateView;
    @Bind(R.id.detail_rate_textview) TextView mRateView;
    @Bind(R.id.detail_genre_textview) TextView mGenreView;
    @Bind(R.id.detail_overview_textview) TextView mOverviewView;
    @Bind(R.id.detail_poster_imageview) ImageView mPosterView;
    @Bind(R.id.detail_layout) LinearLayout mDetailLayout;

    public MovieDetailFragment() {
    }

    public static MovieDetailFragment newInstance(Parcelable parcelable) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(MovieDetailFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE_INFO)) {
            mMovie = getArguments().getParcelable(ARG_MOVIE_INFO);
            Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovie);

//            Activity activity = this.getActivity();
//            mAppBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            mBackdropImage = (ImageView) activity.findViewById(R.id.detail_backdrop_image);
//            if (mAppBarLayout != null) {
//                mAppBarLayout.setTitle(mMovie.getTitle());
//            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        updateDetail();
        return rootView;
    }

    private void updateDetail() {
        mTitleView.setText(mMovie.getTitle());
        mReleaseDateView.setText(mMovie.getReleaseDate());
        mRateView.setText(mMovie.getVoteAverage() + "/10");
        mOverviewView.setText(mMovie.getOverview());

        StringBuilder builder = new StringBuilder();
        for (Integer id : mMovie.getGenreIds()) {
            Log.v(LOG_TAG, "updateDetail(): genre id = " + id);
            String genre = GenreUtility.getGenreName(id);
            if (genre != "") {
                if (builder.length() != 0) {
                    builder.append(", ");
                }
                builder.append(genre);
            }
        }
        mGenreView.setText(builder);

        String url = ImageUtility.getImageUrl(mMovie.getPosterPath());
        Picasso.with(getActivity())
                .load(url)
                .into(mPosterView);
    }
}
