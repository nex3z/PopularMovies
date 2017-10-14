package com.nex3z.popularmovies.presentation;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class UiThread implements PostExecutionThread {

    private UiThread() {}

    private static class Holder {
        private static final UiThread INSTANCE = new UiThread();
    }

    public static UiThread getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

}
