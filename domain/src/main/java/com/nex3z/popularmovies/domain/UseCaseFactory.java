package com.nex3z.popularmovies.domain;

import com.nex3z.popularmovies.domain.interactor.BaseUseCase;

public interface UseCaseFactory {

    <T extends BaseUseCase> T create(Class<T> clazz);

}
