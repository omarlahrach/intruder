package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.ui.views.activities.GameActivity;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.SharedData;
import com.ailyan.intrus.utilities.Toast;
import com.ailyan.intrus.utilities.enums.ConnectionState;
import com.ailyan.intrus.utilities.enums.DataSource;

import com.ailyan.intrus.R;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginViewModel loginViewModel;
    private ConnectionState connectionState;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("connectionState", connectionState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            this.connectionState = (ConnectionState) savedInstanceState.getSerializable("connectionState");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getContext());
        connectionLiveData.observe(getViewLifecycleOwner(), connectionState -> {
            if (this.connectionState != connectionState) {
                Toast.showConnectionState((GameActivity) requireActivity(), connectionState);
                this.connectionState = connectionState;
                switch (connectionState) {
                    case ONLINE:
                        AuthResponse sharedAuth = (AuthResponse) SharedData.get(requireActivity().getApplication(), AuthResponse.class, "auth");
                        if (sharedAuth == null)
                            login();
                        else
                            checkSession(sharedAuth);
                        break;
                    case OFFLINE:
                        loginViewModel.selectedDataSource().postValue(DataSource.LOCAL);
                        break;
                }
            }
        });
    }

    private void checkSession(AuthResponse sharedAuth) {
        String username = sharedAuth.username;
        String session = sharedAuth.session;
        loginViewModel.checkSession(username, session).observe(getViewLifecycleOwner(), sessionState -> {
            switch (sessionState) {
                case OPENED:
                    Log.d(TAG, "Session opened");
                    loginViewModel.selectedDataSource().postValue(DataSource.REMOTE);
                    break;
                case CLOSED:
                    Log.d(TAG, "Session closed!");
                    login();
                    break;
            }
        });
    }

    private void login() {
        String username = (String) SharedData.get(requireActivity().getApplication(), String.class, "username");
        String password = (String) SharedData.get(requireActivity().getApplication(), String.class, "password");
        loginViewModel.login(username, password).observe(getViewLifecycleOwner(), authResponse -> {
            if (authResponse.message == null) {
                Log.d(TAG, "Login success");
                SharedData.add(requireActivity().getApplication(), authResponse, "auth");
                loginViewModel.selectedDataSource().postValue(DataSource.REMOTE);
            } else {
                Log.e(TAG, authResponse.message);
                loginViewModel.selectedDataSource().postValue(DataSource.LOCAL);
            }
        });
    }
}