package com.ailyan.intrus.ui.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.SharedData;

public class ConnectionFragment extends Fragment {
    private static final String TAG = ConnectionFragment.class.getSimpleName();
    private ConnectionLiveData connectionLiveData;
    private AuthResponse authResponse;
    private View root;
    private TextView textView_connectionState;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        authResponse = (AuthResponse) SharedData.get(requireActivity().getApplication(), AuthResponse.class, "auth");
        connectionLiveData = new ConnectionLiveData(getContext());
        connectionLiveData.observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected && authResponse == null)
                loginViewModel.login("testAdmin1", "123").observe(getViewLifecycleOwner(), authResponse ->
                        SharedData.add(requireActivity().getApplication(), authResponse, "auth"));
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView_connectionState = root.findViewById(R.id.textView_connectionState);
        connectionLiveData.observe(getViewLifecycleOwner(), isConnected -> {
            if (isConnected)
                textView_connectionState.setText(R.string.online_message);
            else
                textView_connectionState.setText(R.string.offline_message);
        });
    }
}