package com.nex3z.popularmovies.presentation.base;

import android.support.v4.app.Fragment;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment implements BaseView {

    @Override
    public void onResume() {
        super.onResume();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this instanceof HasPresenter && ((HasPresenter) this).getPresenter() != null) {
            ((HasPresenter) this).getPresenter().destroy();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}
