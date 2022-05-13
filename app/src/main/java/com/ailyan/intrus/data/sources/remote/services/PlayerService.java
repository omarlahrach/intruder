package com.ailyan.intrus.data.sources.remote.services;

import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.beans.CheckSessionResponse;
import com.ailyan.intrus.utilities.annotations.Json;
import com.ailyan.intrus.utilities.annotations.Xml;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PlayerService {
    @FormUrlEncoded
    @POST("connexionVA.php")
    @Json
    Single<AuthResponse> login(@Field("login") String username, @Field("pass") String password);

    @FormUrlEncoded
    @POST("check_session.php")
    @Xml
    Single<CheckSessionResponse> checkSession(@Field("login") String username, @Field("session") String session);
}