package com.nex3z.popularmovies.domain.model.movie;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public class MovieModel implements Parcelable {
    private static final String BASE_URL = "http://image.tmdb.org/t/p/";

    public static final String POSTER_SIZE_W92 = "w92";
    public static final String POSTER_SIZE_W154 = "w154";
    public static final String POSTER_SIZE_W185 = "w185";
    public static final String POSTER_SIZE_W342 = "w342";
    public static final String POSTER_SIZE_W500 = "w500";
    public static final String POSTER_SIZE_W780 = "w780";
    public static final String POSTER_SIZE_ORIGINAL = "original";
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({POSTER_SIZE_W92, POSTER_SIZE_W154, POSTER_SIZE_W185, POSTER_SIZE_W342,
            POSTER_SIZE_W500, POSTER_SIZE_W780, POSTER_SIZE_ORIGINAL
    })
    public @interface PosterSize {}

    public static final String BACKDROP_SIZE_W300 = "w300";
    public static final String BACKDROP_SIZE_W780 = "w780";
    public static final String BACKDROP_SIZE_W1280 = "w1280";
    public static final String BACKDROP_SIZE_ORIGINAL = "original";
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({BACKDROP_SIZE_W300, BACKDROP_SIZE_W780, BACKDROP_SIZE_W1280, BACKDROP_SIZE_ORIGINAL
    })
    public @interface BackdropSize {}


    private int voteCount;

    private final long id;

    private boolean video;

    private double voteAverage;

    private String title;

    private double popularity;

    private String posterPath;

    private String originalLanguage;

    private String originalTitle;

    private List<Integer> genreIds;

    private String backdropPath;

    private boolean adult;

    private String overview;

    private String releaseDate;

    public MovieModel(long id) {
        this.id = id;
    }

    public String getPosterUrl(@PosterSize String size) {
        return BASE_URL + size + "/" + posterPath;
    }

    public String getBackdropUrl(@BackdropSize String size) {
        return BASE_URL + size + "/" + backdropPath;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public long getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "MovieModel{" +
                "voteCount=" + voteCount +
                ", id=" + id +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", title='" + title + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", genreIds=" + genreIds +
                ", backdropPath='" + backdropPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.voteCount);
        dest.writeLong(this.id);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.title);
        dest.writeDouble(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeList(this.genreIds);
        dest.writeString(this.backdropPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }

    protected MovieModel(Parcel in) {
        this.voteCount = in.readInt();
        this.id = in.readLong();
        this.video = in.readByte() != 0;
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.backdropPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
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
