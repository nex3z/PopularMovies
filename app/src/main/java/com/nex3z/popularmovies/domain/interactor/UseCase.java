package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.executor.PostExecutionThread;
import com.nex3z.popularmovies.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Params> {

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;
    private final CompositeDisposable mDisposables;

    public abstract Observable<T> buildUseCaseObservable(Params params);

    public UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
        mDisposables = new CompositeDisposable();
    }

    public void execute(DisposableObserver<T> observer, Params params) {
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    public void dispose() {
        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }
}
