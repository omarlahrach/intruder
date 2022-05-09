package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;

public class GameViewModel extends AndroidViewModel {
    private static final String TAG = GameViewModel.class.getSimpleName();
    private final MutableLiveData<LevelEntity> selectedLevel = new MutableLiveData<>();
    private final MutableLiveData<QuestionEntity> currentQuestion = new MutableLiveData<>();

    public GameViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<LevelEntity> selectedLevel() {
        return selectedLevel;
    }

    public MutableLiveData<QuestionEntity> currentQuestion() {
        return currentQuestion;
    }
}
