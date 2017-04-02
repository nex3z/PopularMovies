package com.nex3z.popularmovies.domain.model.movie;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MovieModel implements Parcelable {

    private String mPosterPath;
    private boolean mAdult;
    private String mOverview;
    private String mReleaseDate;
    private List<Integer> mGenreIds = null;
    private long mId;
    private String mOriginalTitle;
    private String mOriginalLanguage;
    private String mTitle;
    private String mBackdropPath;
    private double mPopularity;
    private int mVoteCount;
    private boolean mVideo;
    private double mVoteAverage;
    private boolean mIsFavourite;

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

    public long getId() {
        return mId;
    }

    public void setId(long id) {
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

    public boolean isFavourite() {
        return mIsFavourite;
    }

    public void setFavourite(boolean favourite) {
        mIsFavourite = favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mPosterPath);
        dest.writeByte(this.mAdult ? (byte) 1 : (byte) 0);
        dest.writeString(this.mOverview);
        dest.writeString(this.mReleaseDate);
        dest.writeList(this.mGenreIds);
        dest.writeLong(this.mId);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mOriginalLanguage);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBackdropPath);
        dest.writeDouble(this.mPopularity);
        dest.writeInt(this.mVoteCount);
        dest.writeByte(this.mVideo ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.mVoteAverage);
        dest.writeByte(this.mIsFavourite ? (byte) 1 : (byte) 0);
    }

    public MovieModel() {
    }

    protected MovieModel(Parcel in) {
        this.mPosterPath = in.readString();
        this.mAdult = in.readByte() != 0;
        this.mOverview = in.readString();
        this.mReleaseDate = in.readString();
        this.mGenreIds = new ArrayList<Integer>();
        in.readList(this.mGenreIds, Integer.class.getClassLoader());
        this.mId = in.readLong();
        this.mOriginalTitle = in.readString();
        this.mOriginalLanguage = in.readString();
        this.mTitle = in.readString();
        this.mBackdropPath = in.readString();
        this.mPopularity = in.readDouble();
        this.mVoteCount = in.readInt();
        this.mVideo = in.readByte() != 0;
        this.mVoteAverage = in.readDouble();
        this.mIsFavourite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MovieModel> CREATOR = new Parcelable.Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
