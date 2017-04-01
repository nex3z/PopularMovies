package com.nex3z.popularmovies.presentation.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.nex3z.popularmovies.presentation.view.BaseView;

public abstract class BaseFragment extends Fragment implements BaseView {

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int msgResId) {
        Toast.makeText(getContext(), msgResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(int msgResId) {
        Toast.makeText(getContext(), msgResId, Toast.LENGTH_LONG).show();
    }
}