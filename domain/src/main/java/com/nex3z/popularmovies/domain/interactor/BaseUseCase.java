package com.nex3z.popularmovies.domain.interactor;

import com.nex3z.popularmovies.domain.Context;

public abstract class BaseUseCase<T, Params> extends UseCase<T, Params> {

    protected final Context mContext;

    public BaseUseCase(Context context) {
        super(context.getThreadExecutor(), context.getPostExecutionThread());
        mContext = context;
    }

}
