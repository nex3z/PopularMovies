package com.nex3z.popularmovies.domain.interactor.movie;


public class GetMovieListArg {
    public static final int INVALID_PAGE = -1;

    public static final String SORT_BY_POPULARITY_DESC = "popularity.desc";
    public static final String SORT_BY_VOTE_AVERAGE_DESC = "vote_average.desc";
    public static final String SORT_BY_VOTE_COUNT_DESC = "vote_count.desc";
    public static final String SORT_BY_RELEASE_DATE_DESC = "release_date.desc";

    private final String mSortBy;
    private final int mPage;

    public GetMovieListArg(String sortBy, int page) {
        mSortBy = sortBy;
        mPage = page;
    }

    public GetMovieListArg(String sortBy) {
        this(sortBy, INVALID_PAGE);
    }

    public GetMovieListArg() {
        this(SORT_BY_POPULARITY_DESC, INVALID_PAGE);
    }

    public String getSortBy() {
        return mSortBy;
    }

    public int getPage() {
        return mPage;
    }
}
