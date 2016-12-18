package com.nex3z.popularmovies.presentation.internal.di.module;

import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.mapper.ReviewModelDataMapper;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.IntegratedMovieInfoPresenter;
import com.nex3z.popularmovies.presentation.presenter.MovieDetailPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieDetailModule {
    private MovieModel mMovieModel;

    public MovieDetailModule(MovieModel movieModel) {
        mMovieModel = movieModel;
    }

    @Provides @PerActivity
    MovieDetailPresenter provideMovieDetailPresenter(@Named("getVideoList") UseCase getVideoList,
                                                     VideoModelDataMapper mapper) {
        return new MovieDetailPresenter(mMovieModel, getVideoList, mapper);
    }

    @Provides @PerActivity
    IntegratedMovieInfoPresenter provideIntegratedMovieInfoPresenter(
            @Named("getVideoList") UseCase getVideoList,
            VideoModelDataMapper videoModelDataMapper,
            @Named("getReviewList") UseCase getReviewList,
            ReviewModelDataMapper reviewModelDataMapper) {
        return new IntegratedMovieInfoPresenter(
                mMovieModel,
                getVideoList, videoModelDataMapper,
                getReviewList, reviewModelDataMapper);
    }
}
