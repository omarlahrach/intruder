package com.ailyan.intrus.data.sources.remote.services;

import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.utilities.annotations.Json;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlayerService {
    @FormUrlEncoded
    @POST("connexionVA.php")
    @Json
    Flowable<AuthResponse> login(@Field("login") String username, @Field("pass") String password);
}