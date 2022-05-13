package com.ailyan.intrus.utilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class SharedData {
    private static final String TAG = SharedData.class.getSimpleName();

    public static void add(Application application, Object object, String tag) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String data = gson.toJson(object);
        prefEditor.putString(tag, data);
        prefEditor.apply();
    }

    public static Object get(Application application, Class<?> target, String tag) {
        SharedPreferences sharedPreferences = application.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(tag, "");
        return gson.fromJson(data, target);
    }
}
