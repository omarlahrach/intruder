package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.PlayerRepository;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.utilities.enums.DataSource;
import com.ailyan.intrus.utilities.enums.SessionState;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();
    private final MutableLiveData<AuthResponse> authResponse = new MutableLiveData<>();
    private final MutableLiveData<SessionState> sessionState = new MutableLiveData<>();
    private final MutableLiveData<DataSource> dataSource = new MutableLiveData<>();
    private final PlayerRepository playerRepository;
    private Disposable disposable;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        playerRepository = new PlayerRepository();
    }

    public LiveData<AuthResponse> login(String username, String password) {
        disposable = playerRepository.login(username, password)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this.authResponse::setValue,
                        throwable -> Log.e(TAG, "Login failed!", throwable)
                );
        return authResponse;
    }

    public LiveData<SessionState> checkSession(String username, String session) {
        disposable = playerRepository.checkSession(username, session)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        checkSessionResponse -> {
                            if (checkSessionResponse.succes)
                                this.sessionState.setValue(SessionState.OPENED);
                            else {
                                this.sessionState.setValue(SessionState.CLOSED);
                                Log.e(TAG, checkSessionResponse.message);
                            }
                        },
                        throwable -> Log.e(TAG, "Checking session failed!", throwable)
                );
        return sessionState;
    }

    public MutableLiveData<DataSource> selectedDataSource() {
        return dataSource;
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
