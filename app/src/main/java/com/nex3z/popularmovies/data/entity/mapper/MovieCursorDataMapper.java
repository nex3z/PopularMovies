package com.nex3z.popularmovies.data.entity.mapper;

import android.database.Cursor;

import com.nex3z.popularmovies.data.entity.MovieEntity;
import com.nex3z.popularmovies.data.provider.MovieContract;
import com.nex3z.popularmovies.domain.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieCursorDataMapper {

    public static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_POPULARITY,
            MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUMN_VOTE_COUNT,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.COLUMN_ADULT,
            MovieContract.MovieEntry.COLUMN_VIDEO,
            MovieContract.MovieEntry.COLUMN_GENRE_IDS,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID
    };

    public static final int COL_MOVIE_ID = 0;
    public static final int COL_MOVIE_TITLE = 1;
    public static final int COL_MOVIE_POSTER_PATH = 2;
    public static final int COL_MOVIE_RELEASE_DATE = 3;
    public static final int COL_MOVIE_OVERVIEW = 4;
    public static final int COL_MOVIE_BACKDROP_PATH = 5;
    public static final int COL_MOVIE_POPULARITY = 6;
    public static final int COL_MOVIE_COLUMN_VOTE_AVERAGE = 7;
    public static final int COL_MOVIE_VOTE_COUNT = 8;
    public static final int COL_MOVIE_ORIGINAL_TITLE = 9;
    public static final int COL_MOVIE_ORIGINAL_LANGUAGE = 10;
    public static final int COL_MOVIE_ADULT = 11;
    public static final int COL_MOVIE_VIDEO = 12;
    public static final int COL_MOVIE_GENRE_IDS = 13;
    public static final int COL_MOVIE_MOVIE_ID = 14;

    public MovieEntity transform(Cursor movieCursor) {
        MovieEntity movie = null;
        if (movieCursor != null) {
            movie = new MovieEntity();
            movie.setAdult(movieCursor.getInt(COL_MOVIE_ADULT) == 1);
            movie.setBackdropPath(movieCursor.getString(COL_MOVIE_BACKDROP_PATH));

            String genre = movieCursor.getString(COL_MOVIE_GENRE_IDS);
            movie.setGenreIds(convertStringToIntList(genre));
            movie.setId(movieCursor.getLong(COL_MOVIE_MOVIE_ID));
            movie.setOriginalLanguage(movieCursor.getString(COL_MOVIE_ORIGINAL_LANGUAGE));
            movie.setOriginalTitle(movieCursor.getString(COL_MOVIE_ORIGINAL_TITLE));
            movie.setOverview(movieCursor.getString(COL_MOVIE_OVERVIEW));
            movie.setPopularity(movieCursor.getDouble(COL_MOVIE_POPULARITY));
            movie.setPosterPath(movieCursor.getString(COL_MOVIE_POSTER_PATH));
            movie.setReleaseDate(movieCursor.getString(COL_MOVIE_RELEASE_DATE));
            movie.setTitle(movieCursor.getString(COL_MOVIE_TITLE));
            movie.setVideo(movieCursor.getInt(COL_MOVIE_VIDEO) == 1);
            movie.setVoteAverage(movieCursor.getDouble(COL_MOVIE_COLUMN_VOTE_AVERAGE));
            movie.setVoteCount(movieCursor.getLong(COL_MOVIE_VOTE_COUNT));
        }

        return movie;
    }

    public List<MovieEntity> transformList(Cursor movieCursor) {
        List<MovieEntity> movieList = new ArrayList<>();

        MovieEntity movie = null;
        while(movieCursor.moveToNext()) {
            movie = new MovieEntity();
            movie.setAdult(movieCursor.getInt(COL_MOVIE_ADULT) == 1);
            movie.setBackdropPath(movieCursor.getString(COL_MOVIE_BACKDROP_PATH));

            String genre = movieCursor.getString(COL_MOVIE_GENRE_IDS);
            movie.setGenreIds(convertStringToIntList(genre));
            movie.setId(movieCursor.getLong(COL_MOVIE_MOVIE_ID));
            movie.setOriginalLanguage(movieCursor.getString(COL_MOVIE_ORIGINAL_LANGUAGE));
            movie.setOriginalTitle(movieCursor.getString(COL_MOVIE_ORIGINAL_TITLE));
            movie.setOverview(movieCursor.getString(COL_MOVIE_OVERVIEW));
            movie.setPopularity(movieCursor.getDouble(COL_MOVIE_POPULARITY));
            movie.setPosterPath(movieCursor.getString(COL_MOVIE_POSTER_PATH));
            movie.setReleaseDate(movieCursor.getString(COL_MOVIE_RELEASE_DATE));
            movie.setTitle(movieCursor.getString(COL_MOVIE_TITLE));
            movie.setVideo(movieCursor.getInt(COL_MOVIE_VIDEO) == 1);
            movie.setVoteAverage(movieCursor.getDouble(COL_MOVIE_COLUMN_VOTE_AVERAGE));
            movie.setVoteCount(movieCursor.getLong(COL_MOVIE_VOTE_COUNT));

            movieList.add(movie);
        }

        return movieList;
    }

    private List<Integer> convertStringToIntList(String str) {
        List<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(str);
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }
        return list;
    }
}
