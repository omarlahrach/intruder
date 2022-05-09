package com.ailyan.intrus.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.QuestionRepository;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.DataSource;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    private static final String TAG = QuestionViewModel.class.getSimpleName();
    private final QuestionRepository questionRepository;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
    }

    public LiveData<List<QuestionEntity>> loadLocalQuestionsByLevel(LevelEntity levelEntity) {
        return questionRepository.loadQuestionsByLevel(levelEntity, DataSource.LOCAL);
    }

    public LiveData<List<QuestionEntity>> loadRemoteQuestionsByLevel(LevelEntity levelEntity) {
        return questionRepository.loadQuestionsByLevel(levelEntity, DataSource.REMOTE);
    }
}
