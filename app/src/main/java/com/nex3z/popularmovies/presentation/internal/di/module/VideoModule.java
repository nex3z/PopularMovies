package com.nex3z.popularmovies.presentation.internal.di.module;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.video.GetVideoList;
import com.nex3z.popularmovies.domain.repository.VideoRepository;
import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.mapper.VideoModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieVideoPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class VideoModule {

    private MovieModel mMovieModel;

    public VideoModule(MovieModel movieModel) {
        mMovieModel = movieModel;
    }

    @Provides @PerActivity @Named("getVideoList")
    UseCase provideGetVideoListUseCase(VideoRepository repository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new GetVideoList(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity
    MovieVideoPresenter provideMovieVideoPresenter(@Named("getVideoList") UseCase getVideoList,
                                                   VideoModelDataMapper mapper) {
        return new MovieVideoPresenter(mMovieModel, getVideoList, mapper);
    }

}
