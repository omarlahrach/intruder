package com.ailyan.intrus.data.sources.remote;

import com.ailyan.intrus.utilities.XmlOrJsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class RetrofitInstance {
    private static Retrofit retrofitInstance;
    private static final String BASE_URL = "https://www.ailyan.fr/interface/";

    public static Retrofit getInstance() {
        if(retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new XmlOrJsonConverterFactory())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
