package com.ailyan.intrus.ui.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.SharedData;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        connectionLiveData.observe(this, isConnected -> {
            if (isConnected) {
                loginViewModel.login("testAdmin1", "123").observe(this, authResponse -> {
                    boolean authShared = SharedData.add(getApplication(), authResponse, "auth");
                    if (authShared) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}