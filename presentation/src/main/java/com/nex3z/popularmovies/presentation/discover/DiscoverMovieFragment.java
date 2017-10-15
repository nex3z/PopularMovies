package com.nex3z.popularmovies.presentation.discover;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;
import com.nex3z.popularmovies.domain.model.movie.MovieModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverMovieFragment extends Fragment {

    @BindView(R.id.swipy_discover_movie_list) SwipyRefreshLayout mSwipyMovieList;
    @BindView(R.id.rv_discover_movie_list) RecyclerView mRvMovieList;

    private OnMovieClickListener mListener;

    public DiscoverMovieFragment() {}

    public static DiscoverMovieFragment newInstance() {
        return new DiscoverMovieFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discover_movie, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieClickListener) {
            mListener = (OnMovieClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnMovieClickListener {
        void onMovieClick(MovieModel movie);
    }
}
