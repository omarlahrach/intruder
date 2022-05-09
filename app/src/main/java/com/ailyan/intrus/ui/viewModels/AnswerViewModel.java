package com.ailyan.intrus.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.AnswerRepository;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.DataSource;

import java.util.List;

public class AnswerViewModel extends AndroidViewModel {
    private static final String TAG = AnswerViewModel.class.getSimpleName();
    private final AnswerRepository answerRepository;

    public AnswerViewModel(@NonNull Application application) {
        super(application);
        answerRepository = new AnswerRepository(application);
    }

    public LiveData<List<AnswerEntity>> loadLocalAnswersByQuestion(QuestionEntity questionEntity) {
        return answerRepository.loadAnswersByQuestion(questionEntity, DataSource.LOCAL);
    }

    public LiveData<List<AnswerEntity>> loadRemoteAnswersByQuestion(QuestionEntity questionEntity) {
        return answerRepository.loadAnswersByQuestion(questionEntity, DataSource.REMOTE);
    }
}
