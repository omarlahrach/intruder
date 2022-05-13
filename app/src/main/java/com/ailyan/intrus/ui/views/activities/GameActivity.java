package com.ailyan.intrus.ui.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.ui.viewModels.AnswerViewModel;
import com.ailyan.intrus.ui.viewModels.LoginViewModel;
import com.ailyan.intrus.ui.viewModels.QuestionViewModel;
import com.ailyan.intrus.ui.views.fragments.LevelFragment;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.Toast;
import com.ailyan.intrus.utilities.enums.DataSource;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, LevelFragment.class, null)
                    .addToBackStack("levels")
                    .commit();
        }

        ConnectionLiveData connectionLiveData = new ConnectionLiveData(getApplicationContext());
        connectionLiveData.observe(this, connectionState ->
                Toast.showConnectionState(this, connectionState));

        QuestionViewModel questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        AnswerViewModel answerViewModel = new ViewModelProvider(this).get(AnswerViewModel.class);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.selectedDataSource().observe(this, dataSource -> {
            if (dataSource == DataSource.REMOTE) {
                questionViewModel.getAllRemoteQuestions().observe(this, questions -> {
                    for (QuestionEntity question : questions)
                        answerViewModel.loadRemoteAnswersByQuestionId(question.id).observe(this, connectionState ->
                                Toast.showConnectionState(this, connectionState));
                });
            }
        });
    }
}