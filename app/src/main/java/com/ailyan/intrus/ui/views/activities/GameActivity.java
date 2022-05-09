package com.ailyan.intrus.ui.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.ui.viewModels.GameViewModel;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameViewModel gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        Intent intent = getIntent();
        LevelEntity selectedLevel = (LevelEntity) intent.getSerializableExtra("selectedLevel");
        gameViewModel.selectedLevel().setValue(selectedLevel);
    }
}