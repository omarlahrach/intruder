package com.ailyan.intrus.ui.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.dialogs.QuitDialogFragment;
import com.ailyan.intrus.ui.views.fragments.LevelFragment;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.Toast;
import com.ailyan.intrus.utilities.enums.DataSource;

public class GameActivity extends AppCompatActivity {
    //private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, LevelFragment.class, null, "Level")
                    .addToBackStack(null)
                    .commit();
        }

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, connectionState ->
                Toast.showConnectionState(this, connectionState));

        QuestionViewModel questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.selectedDataSource().observe(this, dataSource -> {
            if (dataSource == DataSource.REMOTE) {
                questionViewModel.loadAllRemoteQuestions(loginViewModel);
            }
        });
    }

    @Override
    public void onBackPressed() {
        LevelFragment levelFragment = (LevelFragment) getSupportFragmentManager().findFragmentByTag("Level");
        if (levelFragment != null && levelFragment.isVisible())
            new QuitDialogFragment().show(getSupportFragmentManager(), "Quit game");
        else
            new QuitDialogFragment().show(getSupportFragmentManager(), "Quit level");
    }
}