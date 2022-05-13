package com.ailyan.intrus.data.sources.remote;

import com.ailyan.intrus.utilities.XmlOrJsonConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class RetrofitInstance {
    private static Retrofit retrofitInstance;
    private static final String BASE_URL = "https://www.ailyan.fr/interface/";

    public static Retrofit getInstance() {
        if(retrofitInstance == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(4, TimeUnit.MINUTES)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(new XmlOrJsonConverterFactory())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }
}
