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

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.SharedData;
import com.ailyan.intrus.utilities.enums.DataSource;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private LoginViewModel loginViewModel;

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
            switch (connectionState) {
                case ONLINE:
                    AuthResponse sharedAuth = (AuthResponse) SharedData.get(requireActivity().getApplication(), AuthResponse.class, "auth");
                    if (sharedAuth == null)
                        login();
                    else
                        checkSession(sharedAuth);
                    break;
                case OFFLINE:
                    loginViewModel.selectedDataSource().setValue(DataSource.LOCAL);
                    break;
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
                    loginViewModel.selectedDataSource().setValue(DataSource.REMOTE);
                    break;
                case CLOSED:
                    Log.d(TAG, "Session closed!");
                    login();
                    break;
            }
        });
    }

    private void login() {
        loginViewModel.login("testAdmin1", "123").observe(getViewLifecycleOwner(), authResponse -> {
            if (authResponse.message == null) {
                Log.d(TAG, "Login success");
                SharedData.add(requireActivity().getApplication(), authResponse, "auth");
                loginViewModel.selectedDataSource().setValue(DataSource.REMOTE);
            } else {
                Log.e(TAG, authResponse.message);
                loginViewModel.selectedDataSource().setValue(DataSource.LOCAL);
            }
        });
    }
}