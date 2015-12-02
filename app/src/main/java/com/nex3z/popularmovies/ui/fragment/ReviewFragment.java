package com.nex3z.popularmovies.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.app.App;
import com.nex3z.popularmovies.data.model.Review;
import com.nex3z.popularmovies.data.rest.model.ReviewResponse;
import com.nex3z.popularmovies.data.rest.service.ReviewService;
import com.nex3z.popularmovies.ui.adapter.ReviewAdapter;
import com.nex3z.popularmovies.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ReviewFragment extends Fragment {

    private static final String LOG_TAG = ReviewFragment.class.getSimpleName();

    @Bind(R.id.review_list) RecyclerView mReviewList;
    @Bind(R.id.empty_view) TextView mEmptyView;
    @Bind(R.id.empty_view_container) NestedScrollView mEmptyViewContainer;

    public static final String ARG_MOVIE_ID = "MOVIE_ID";
    private long mMovieId = -1;
    private ReviewAdapter mReviewAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Review> mReviews = new ArrayList<Review>();

    public ReviewFragment() { }

    public static ReviewFragment newInstance(long movieId) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(ReviewFragment.ARG_MOVIE_ID, movieId);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_MOVIE_ID)) {
            mMovieId = getArguments().getLong(ARG_MOVIE_ID);
            Log.v(LOG_TAG, "onCreate(): mMovie = " + mMovieId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        ButterKnife.bind(this, rootView);

        setupRecyclerView(mReviewList);
        mReviewAdapter = new ReviewAdapter(mReviews);

        mReviewList.setAdapter(mReviewAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fetchReviews(mMovieId);
    }

    public void fetchReviews(long movieId) {
        Log.v(LOG_TAG, "fetchVideos(): movieId = " + movieId);
        ReviewService service = App.getRestClient().getReviewService();
        service.getReviews(movieId)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> processReviewResponse(response),
                        throwable -> Snackbar.make(
                                mReviewList,
                                throwable.getLocalizedMessage(), Snackbar.LENGTH_LONG
                        ).show()
                );
    }

    private void processReviewResponse(ReviewResponse response) {
        List<Review> reviews = response.getReviews();
        Log.v(LOG_TAG, "processReviewResponse(): reviews size = " + reviews.size());
        if (reviews.size() == 0) {
            mReviewList.setVisibility(View.GONE);
            mEmptyViewContainer.setVisibility(View.VISIBLE);
            //mEmptyView.setVisibility(View.VISIBLE);
            return;
        } else {
            mReviewList.setVisibility(View.VISIBLE);
            mEmptyViewContainer.setVisibility(View.GONE);
            //mEmptyView.setVisibility(View.GONE);
        }
        mReviews.addAll(reviews);
        Log.v(LOG_TAG, "processReviewResponse(): mVideos size = " + mReviews.size());
        for(Review review : mReviews) {
            Log.v(LOG_TAG, "processReviewResponse(): author = " + review.getAuthor()
                    + ", content = " + review.getContent());
        }
        mReviewAdapter.notifyDataSetChanged();
        Log.v(LOG_TAG, "processReviewResponse(): size = " + response.getReviews().size());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);
    }

}
