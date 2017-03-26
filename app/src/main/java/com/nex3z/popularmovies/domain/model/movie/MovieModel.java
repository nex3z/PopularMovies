package com.nex3z.popularmovies.domain.model.movie;

import java.util.List;

public class MovieModel {

    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private String mReleaseDate;
    private List<Integer> mGenreIds = null;
    private int mId;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private String mBackdropPath;
    private double mPopularity;
    private int mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;

    @Override
    public String toString() {
        return "MovieModel{" +
                "mPosterPath='" + mPosterPath + '\'' +
                ", mAdult=" + mAdult +
                ", mOverview='" + mOverview + '\'' +
                ", mReleaseDate='" + mReleaseDate + '\'' +
                ", mGenreIds=" + mGenreIds +
                ", mId=" + mId +
                ", mOriginalTitle='" + mOriginalTitle + '\'' +
                ", mOriginalLanguage='" + mOriginalLanguage + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mBackdropPath='" + mBackdropPath + '\'' +
                ", mPopularity=" + mPopularity +
                ", mVoteCount=" + mVoteCount +
                ", mVideo=" + mVideo +
                ", mVoteAverage=" + mVoteAverage +
                '}';
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        mAdult = adult;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        mGenreIds = genreIds;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        mPopularity = popularity;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        mVoteCount = voteCount;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        mVideo = video;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        mVoteAverage = voteAverage;
    }
}
