package com.ailyan.intrus.data.sources.remote.services;

import com.ailyan.intrus.data.sources.remote.beans.AnswerResponse;
import com.ailyan.intrus.utilities.annotations.Xml;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AnswerService {
    @FormUrlEncoded
    @POST("reponsesQuestions.php")
    @Xml
    Flowable<AnswerResponse> loadAllAnswers(@Field("login") String username, @Field("session") String session);
}
