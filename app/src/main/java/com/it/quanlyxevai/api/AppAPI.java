package com.it.quanlyxevai.api;


import com.it.quanlyxevai.model.LoginRequest;
import com.it.quanlyxevai.model.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppAPI {
    @POST("thtapigate/api/login/login")
    Observable<LoginResponse> loginSystem(@Body LoginRequest rqt);


}
