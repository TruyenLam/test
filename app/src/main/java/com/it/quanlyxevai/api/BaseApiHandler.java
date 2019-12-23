package com.it.quanlyxevai.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.it.quanlyxevai.base.AppPref;
import com.it.quanlyxevai.base.Constant;


import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseApiHandler {

    protected static Retrofit buildAuthorizationRetrofitLogin() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constant.API_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constant.API_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(
                        chain -> {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "application/json")
                                    .build();
                            return chain.proceed(request);
                        }).build();

        String serverUrlRoot = Constant.SERVER_NAME;
        String serverUrl = serverUrlRoot + (serverUrlRoot.substring(serverUrlRoot.length() - 1).equals("/") ? "" : "/");
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(defaultHttpClient)
                .build();
        return builder;
    }

    protected static Retrofit buildAuthorizationRetrofit(Context context) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Constant.API_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constant.API_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(
                        chain -> {
                            Request request = chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "application/json")
                                    .addHeader("Authorization", "Bearer".concat(AppPref.getString(context, Constant.PREF_TOKEN, "")))
                                    .build();
                            return chain.proceed(request);
                        }).build();

        String serverUrlRoot = Constant.SERVER_NAME;
        String serverUrl = serverUrlRoot + (serverUrlRoot.substring(serverUrlRoot.length() - 1).equals("/") ? "" : "/");
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(serverUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(defaultHttpClient)
                .build();
        return builder;
    }

}
