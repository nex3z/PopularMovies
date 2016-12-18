package com.nex3z.popularmovies.presentation.internal.di.module;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;
import com.nex3z.popularmovies.domain.interactor.UseCase;
import com.nex3z.popularmovies.domain.interactor.movie.CheckFavourite;
import com.nex3z.popularmovies.domain.interactor.movie.DeleteMovie;
import com.nex3z.popularmovies.domain.interactor.movie.GetFavouriteMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.GetMovieList;
import com.nex3z.popularmovies.domain.interactor.movie.SaveMovie;
import com.nex3z.popularmovies.domain.repository.MovieRepository;
import com.nex3z.popularmovies.presentation.internal.di.PerActivity;
import com.nex3z.popularmovies.presentation.mapper.MovieModelDataMapper;
import com.nex3z.popularmovies.presentation.model.MovieModel;
import com.nex3z.popularmovies.presentation.presenter.MovieInfoPresenter;
import com.nex3z.popularmovies.presentation.presenter.MovieListPresenter;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieModule {
    private MovieModel mMovieModel;

    public MovieModule() {}

    public MovieModule(MovieModel movieModel) {
        mMovieModel = movieModel;
    }

    @Provides @PerActivity @Named("getMovieList")
    UseCase provideGetMovieListUseCase(MovieRepository repository, ThreadExecutor threadExecutor,
                                       PostExecutionThread postExecutionThread) {
        return new GetMovieList(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("getFavouriteMovieList")
    UseCase provideGetFavouriteMovieListUseCase(MovieRepository repository,
                                                ThreadExecutor threadExecutor,
                                                PostExecutionThread postExecutionThread) {
        return new GetFavouriteMovieList(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("saveMovie")
    UseCase provideSaveMovieUseCase(MovieRepository repository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        return new SaveMovie(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("deleteMovie")
    UseCase provideDeleteMovieUseCase(MovieRepository repository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        return new DeleteMovie(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("checkFavourite")
    UseCase provideCheckFavouriteUseCase(MovieRepository repository, ThreadExecutor threadExecutor,
                                         PostExecutionThread postExecutionThread) {
        return new CheckFavourite(repository, threadExecutor, postExecutionThread);
    }

    @Provides @PerActivity @Named("discoverMoviePresenter")
    MovieListPresenter provideMovieListPresenter(@Named("getMovieList") UseCase getMovieList,
                                                 @Named("saveMovie") UseCase saveMovie,
                                                 @Named("deleteMovie") UseCase deleteMovie,
                                                 @Named("checkFavourite") UseCase isFavourite,
                                                 MovieModelDataMapper mapper) {
        return new MovieListPresenter(getMovieList, saveMovie, deleteMovie, isFavourite, mapper);
    }

    @Provides @PerActivity @Named("favouriteMoviePresenter")
    MovieListPresenter provideFavouriteMovieListPresenter(
            @Named("getFavouriteMovieList") UseCase getMovieList,
            @Named("saveMovie") UseCase saveMovie,
            @Named("deleteMovie") UseCase deleteMovie,
            @Named("checkFavourite") UseCase isFavourite,
            MovieModelDataMapper mapper) {
        return new MovieListPresenter(getMovieList, saveMovie, deleteMovie, isFavourite, mapper);
    }

    @Provides @PerActivity
    MovieInfoPresenter provideMovieInfoPresenter() {
        return new MovieInfoPresenter(mMovieModel);
    }

}
