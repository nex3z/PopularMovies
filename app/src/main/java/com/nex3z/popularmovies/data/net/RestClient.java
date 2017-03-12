package com.nex3z.popularmovies.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nex3z.popularmovies.BuildConfig;
import com.nex3z.popularmovies.data.net.service.MovieService;
import com.nex3z.popularmovies.data.net.service.ReviewService;
import com.nex3z.popularmovies.data.net.service.VideoService;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class RestClient {
    private static final String BASE_URL = "http://api.themoviedb.org";

    private VideoService mVideoService;
    private MovieService mMovieService;
    private ReviewService mReviewService;

    @Inject
    public RestClient() {
        Gson gson = new GsonBuilder().create();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl.Builder builder = originalHttpUrl.newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY);

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(builder.build())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mVideoService = retrofit.create(VideoService.class);
        mMovieService = retrofit.create(MovieService.class);
        mReviewService = retrofit.create(ReviewService.class);
    }

    public VideoService getVideoService() { return mVideoService; }

    public MovieService getMovieService() { return mMovieService; }

    public ReviewService getReviewService() { return mReviewService; }
}
