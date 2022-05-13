package com.ailyan.intrus.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProgressViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> points = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionIndex = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionsCount = new MutableLiveData<>();
    
    public ProgressViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getPoints() {
        return points;
    }

    public MutableLiveData<Integer> getQuestionIndex() {
        return questionIndex;
    }

    public MutableLiveData<Integer> getQuestionsCount() {
        return questionsCount;
    }
}
