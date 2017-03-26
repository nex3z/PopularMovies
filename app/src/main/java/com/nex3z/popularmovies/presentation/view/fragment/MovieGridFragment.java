package com.nex3z.popularmovies.presentation.view.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nex3z.popularmovies.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieGridFragment extends Fragment {

    public MovieGridFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
