package com.ailyan.intrus.data.repositories;

import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.beans.CheckSessionResponse;
import com.ailyan.intrus.data.sources.remote.services.PlayerService;

import io.reactivex.rxjava3.core.Single;

public class PlayerRepository {
    private final PlayerService playerService;

    public PlayerRepository() {
        playerService = RetrofitInstance.getInstance().create(PlayerService.class);
    }

    public Single<AuthResponse> login(String username, String password) {
        return playerService.login(username, password);
    }

    public Single<CheckSessionResponse> checkSession(String username, String session) {
        return playerService.checkSession(username, session);
    }
}
