package com.ailyan.intrus.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.services.PlayerService;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlayerRepository {
    private static final String TAG = PlayerRepository.class.getSimpleName();
    private final PlayerService playerService;

    public PlayerRepository(Application application) {
        playerService = RetrofitInstance.getInstance().create(PlayerService.class);
    }

    public LiveData<AuthResponse> login(String username, String password) {
        return LiveDataReactiveStreams.fromPublisher(playerService
                .login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()));
    }
}
