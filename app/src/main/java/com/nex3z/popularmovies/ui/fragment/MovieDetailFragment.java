package com.nex3z.popularmovies.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Movie;
import com.nex3z.popularmovies.data.model.Review;
import com.nex3z.popularmovies.data.rest.model.ReviewResponse;
import com.nex3z.popularmovies.data.rest.service.ReviewService;
import com.nex3z.popularmovies.util.GenreUtility;
import com.nex3z.popularmovies.util.ImageUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MovieDetailFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    public static final String ARG_MOVIE_INFO = "MOVIE_INFO";
    public static final int REVIEW_NUM = 3;

    private Movie mMovie;
    private List<Review> mReviews = new ArrayList<Review>();

    @Bind(R.id.detail_title_textview) TextView mTitleView;
    @Bind(R.id.detail_release_date_textview) TextView mReleaseDateView;
    @Bind(R.id.detail_rate_textview) TextView mRateView;
    @Bind(R.id.detail_genre_textview) TextView mGenreView;
    @Bind(R.id.detail_overview_textview) TextView mOverviewView;
    @Bind(R.id.detail_poster_imageview) ImageView mPosterView;
    @Bind(R.id.detail_layout) LinearLayout mDetailLayout;
    @Bind(R.id.detail_review_layout) LinearLayout mReviewLayout;
    @Bind(R.id.empty_textview) TextView mEmptyView;

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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateDetail();
        fetchReviews(mMovie.getId());
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

    public void fetchReviews(long movieId) {
        Log.v(LOG_TAG, "fetchReviews(): movieId = " + movieId);
        ReviewService service = App.getRestClient().getReviewService();
        service.getReviews(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processReviewResponse(response),
                        throwable -> Snackbar.make(
                                mDetailLayout,
                                throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG
                        ).show()
                );
    }

    private void processReviewResponse(ReviewResponse response) {
        List<Review> reviews = response.getReviews();
        Log.v(LOG_TAG, "processReviewResponse(): reviews size = " + reviews.size());
        if (reviews.size() == 0) {
            mReviewLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
            return;
        } else {
            mReviewLayout.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
        mReviews.addAll(reviews);
        Log.v(LOG_TAG, "processReviewResponse(): mVideos size = " + mReviews.size());
        for(Review review : mReviews) {
            Log.v(LOG_TAG, "processReviewResponse(): author = " + review.getAuthor()
                    + ", content = " + review.getContent());
        }
        Log.v(LOG_TAG, "processReviewResponse(): size = " + response.getReviews().size());

        updateReviews(mReviews);
    }

    private void updateReviews(List<Review> reviews) {
        for (int i = 0;i < reviews.size() && i < REVIEW_NUM ; ++i) {
            Review review = reviews.get(i);
            Log.v(LOG_TAG, "updateReviews(): review = " + review);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View detailItem = inflater.inflate(R.layout.review_item, null);

            TextView authorView = (TextView)detailItem.findViewById(R.id.review_author_textview);
            authorView.setText(review.getAuthor());
            TextView contentView = (TextView)detailItem.findViewById(R.id.review_content_textview);
            contentView.setText(review.getContent());

            mReviewLayout.addView(detailItem);
        }
    }
}
