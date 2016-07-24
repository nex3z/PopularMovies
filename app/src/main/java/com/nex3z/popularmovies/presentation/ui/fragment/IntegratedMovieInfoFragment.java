package com.nex3z.popularmovies.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.data.entity.mapper.ReviewEntityDataMapper;
import com.nex3z.popularmovies.data.entity.mapper.VideoEntityDataMapper;
import com.nex3z.popularmovies.data.executor.JobExecutor;
import com.nex3z.popularmovies.data.repository.ReviewDataRepository;
import com.nex3z.popularmovies.data.repository.VideoDataRepository;
import com.nex3z.popularmovies.data.repository.datasource.review.ReviewDataStoreFactory;
import com.nex3z.popularmovies.data.repository.datasource.video.VideoDataStoreFactory;
import com.nex3z.popularmovies.domain.Review;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewList;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoList;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;
import com.nex3z.popularmovies.domain.repository.VideoRepository;
import com.nex3z.popularmovies.presentation.UIThread;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.model.ReviewModel;
import com.nex3z.popularmovies.presentation.model.VideoModel;
import com.nex3z.popularmovies.presentation.presenter.IntegratedMovieInfoPresenter;
import com.nex3z.popularmovies.presentation.presenter.MovieReviewPresenter;
import com.nex3z.popularmovies.presentation.ui.IntegratedMovieInfoView;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegratedMovieInfoFragment extends Fragment implements IntegratedMovieInfoView {
    private static final String LOG_TAG = IntegratedMovieInfoFragment.class.getSimpleName();

    private static final String ARG_MOVIE_INFO = "arg_movie_info";
    private static final int DISPLAY_VIDEO_ITEM_NUM = 3;
    private static final int DISPLAY_REVIEW_ITEM_NUM = 3;

    @BindView(R.id.tv_movie_title) TextView mTvMovieTitle;
    @BindView(R.id.tv_release_date) TextView mReleaseDate;
    @BindView(R.id.iv_poster) ImageView mIvPoster;
    @BindView(R.id.tv_overview) TextView mOverview;
    @BindView(R.id.video_list_container) LinearLayout mVideoList;
    @BindView(R.id.review_list_container) LinearLayout mReviewList;

    private MovieModel mMovie;
    private IntegratedMovieInfoPresenter mPresenter;

    public IntegratedMovieInfoFragment() {}

    public static IntegratedMovieInfoFragment newInstance(Parcelable parcelable) {
        IntegratedMovieInfoFragment fragment = new IntegratedMovieInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(IntegratedMovieInfoFragment.ARG_MOVIE_INFO, parcelable);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        View rootView = inflater.inflate(R.layout.fragment_integrated_movie_info, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadData();
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

    @Override
    public void renderVideos(Collection<VideoModel> videos) {
        List<VideoModel> videoList = (List<VideoModel>)videos;
        int displayCount = videoList.size() > DISPLAY_VIDEO_ITEM_NUM ?
                DISPLAY_VIDEO_ITEM_NUM : videos.size();
        for (int i = 0; i < displayCount; i++) {
            addVideoItem(videoList.get(i));
        }
    }

    @Override
    public void renderReviews(Collection<ReviewModel> reviews) {
        List<ReviewModel> reviewList = (List<ReviewModel>)reviews;
        int displayCount = reviewList.size() > DISPLAY_REVIEW_ITEM_NUM ?
                DISPLAY_REVIEW_ITEM_NUM : reviewList.size();
        for (int i = 0; i < displayCount; i++) {
            addReviewItem(reviewList.get(i));
        }
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showRetry() {}

    @Override
    public void hideRetry() {}

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initialize() {
        VideoRepository videoRepo = new VideoDataRepository(
                new VideoDataStoreFactory(), new VideoEntityDataMapper());
        UseCase getVideoList = new GetVideoList(videoRepo, new JobExecutor(), new UIThread());
        ReviewRepository reviewRepo = new ReviewDataRepository(
                new ReviewDataStoreFactory(), new ReviewEntityDataMapper());
        UseCase getReviewList = new GetReviewList(reviewRepo, new JobExecutor(), new UIThread());

        mPresenter = new IntegratedMovieInfoPresenter(mMovie,
                getVideoList, new VideoModelDataMapper(),
                getReviewList, new ReviewModelDataMapper());
        mPresenter.setView(this);
    }

    private void loadData() {
        mPresenter.initialize();
    }

    private void addVideoItem(VideoModel videoModel) {
        LayoutInflater inflater =
                (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View videoItem = inflater.inflate(R.layout.video_item, null);
        TextView title = (TextView) videoItem.findViewById(R.id.tv_video_title);
        title.setText(videoModel.getName());
        videoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.playVideo(videoModel);
            }
        });
        mVideoList.addView(videoItem);
    }

    private void addReviewItem(ReviewModel reviewModel) {
        LayoutInflater inflater =
                (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View reviewItem = inflater.inflate(R.layout.review_item, null);

        TextView author = (TextView) reviewItem.findViewById(R.id.tv_review_author);
        author.setText(reviewModel.getAuthor());

        TextView review = (TextView) reviewItem.findViewById(R.id.tv_review_content);
        review.setText(reviewModel.getContent());

        mReviewList.addView(reviewItem);
    }

}
