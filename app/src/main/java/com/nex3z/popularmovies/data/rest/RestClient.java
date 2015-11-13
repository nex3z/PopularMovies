package com.nex3z.popularmovies.data.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nex3z.popularmovies.BuildConfig;
import com.nex3z.popularmovies.data.rest.service.VideoService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class RestClient {

    private static final String BASE_URL = "http://api.themoviedb.org";

    private VideoService videoService;

    public RestClient() {
        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        request.addQueryParam("api_key", BuildConfig.API_KEY);
                    }
                })
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .build();

        videoService = restAdapter.create(VideoService.class);
    }

    public VideoService getVideoService() {
        return videoService;
    }

}
