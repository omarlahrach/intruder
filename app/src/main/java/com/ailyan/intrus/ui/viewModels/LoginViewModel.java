package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.PlayerRepository;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();
    private final PlayerRepository playerRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        playerRepository = new PlayerRepository(application);
    }

    public LiveData<AuthResponse> login(String username, String password) {
        return playerRepository.login(username, password);
    }
}
