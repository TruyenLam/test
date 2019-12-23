package com.it.quanlyxevai.api;

import android.content.Context;

import com.it.quanlyxevai.model.LoginRequest;
import com.it.quanlyxevai.model.LoginResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AppHandler extends BaseApiHandler {
    public static Observable<LoginResponse> loginSystem(LoginRequest rqt){
        Retrofit retrofit = buildAuthorizationRetrofitLogin();
        AppAPI appAPI = retrofit.create(AppAPI.class);
        return  appAPI.loginSystem(rqt).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
