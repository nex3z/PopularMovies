package com.nex3z.popularmovies.presentation.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieModel implements Parcelable {

    private boolean mAdult;

    private String mBackdropPath;

    private List<Integer> mGenreIds = new ArrayList<>();

    private long mId;

    private String mOriginalLanguage;

    private String mOriginalTitle;

    private String mOverview;

    private String mReleaseDate;

    private String mPosterPath;

    private double mPopularity;

    private String mTitle;

    private boolean mVideo;

    private double mVoteAverage;

    private long mVoteCount;

    private String mBackdropImageUrl;

    private String mPosterImageUrl;

    public MovieModel() {}

    @Override
    public String toString() {
        return "MovieModel: mId = " + mId + ", mTitle = " + mTitle;
    }

    public boolean isAdult() {
        return mAdult;
    }

    public void setAdult(boolean adult) {
        this.mAdult = adult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.mGenreIds = genreIds;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public void setPopularity(double popularity) {
        this.mPopularity = popularity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        this.mVideo = video;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public long getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(long voteCount) {
        this.mVoteCount = voteCount;
    }

    public String getPosterImageUrl() {
        return mPosterImageUrl;
    }

    public void setPosterImageUrl(String mPosterImageUrl) {
        this.mPosterImageUrl = mPosterImageUrl;
    }

    public String getBackdropImageUrl() {
        return mBackdropImageUrl;
    }

    public void setBackdropImageUrl(String mBackdropImageUrl) {
        this.mBackdropImageUrl = mBackdropImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mAdult ? 1 : 0));
        dest.writeString(mBackdropPath);
        dest.writeList(mGenreIds);
        dest.writeLong(mId);
        dest.writeString(mOriginalLanguage);
        dest.writeString(mOriginalTitle);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeString(mPosterPath);
        dest.writeDouble(mPopularity);
        dest.writeString(mTitle);
        dest.writeByte((byte) (mVideo ? 1 : 0));
        dest.writeDouble(mVoteAverage);
        dest.writeLong(mVoteCount);
        dest.writeString(mBackdropImageUrl);
        dest.writeString(mPosterImageUrl);
    }

    protected MovieModel(android.os.Parcel in) {
        mAdult = in.readByte() != 0;
        mBackdropPath = in.readString();
        mGenreIds = new ArrayList<Integer>();
        in.readList(mGenreIds, List.class.getClassLoader());
        mId = in.readLong();
        mOriginalLanguage = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
        mPopularity = in.readDouble();
        mTitle = in.readString();
        mVideo = in.readByte() != 0;
        mVoteAverage = in.readDouble();
        mVoteCount = in.readLong();
        mBackdropImageUrl = in.readString();
        mPosterImageUrl = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        public MovieModel createFromParcel(android.os.Parcel source) {
            return new MovieModel(source);
        }

        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
