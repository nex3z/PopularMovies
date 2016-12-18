package com.nex3z.popularmovies.presentation.internal.di.module;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.review.GetReviewList;
import com.nex3z.popularmovies.domain.repository.ReviewRepository;
import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieReviewPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ReviewModule {
    private MovieModel mMovieModel;

    public ReviewModule(MovieModel movieModel) {
        mMovieModel = movieModel;
    }

    @Provides @PerActivity @Named("getReviewList")
    UseCase provideGetReviewListUseCase(ReviewRepository repository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        return new GetReviewList(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity
    MovieReviewPresenter provideMovieReviewPresenter(@Named("getReviewList") UseCase getReviewList,
                                                     ReviewModelDataMapper mapper) {
        return new MovieReviewPresenter(mMovieModel, getReviewList, mapper);
    }

}
