package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.QuestionRepository;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuestionViewModel extends AndroidViewModel {
    private static final String TAG = QuestionViewModel.class.getSimpleName();
    private final MutableLiveData<List<QuestionEntity>> remoteQuestions = new MutableLiveData<>();
    private final MutableLiveData<List<QuestionEntity>> localQuestionsByLevel = new MutableLiveData<>();
    private final MutableLiveData<QuestionEntity> question = new MutableLiveData<>();
    private final QuestionRepository questionRepository;
    private Disposable disposable;

    public QuestionViewModel(@NonNull Application application) {
        super(application);
        this.questionRepository = new QuestionRepository(application);
    }

    public LiveData<List<QuestionEntity>> getAllRemoteQuestions() {
        disposable = questionRepository.getAllRemoteQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this.remoteQuestions::setValue,
                        throwable -> Log.e(TAG, "Cannot access remote questions!", throwable)
                );
        return remoteQuestions;
    }

    public LiveData<List<QuestionEntity>> loadLocalQuestionsByLevel(int level) {
        disposable = questionRepository.loadLocalQuestionsByLevel(level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this.localQuestionsByLevel::setValue,
                        throwable -> Log.e(TAG, "Cannot load local questions by level!", throwable)
                );
        return localQuestionsByLevel;
    }

    public MutableLiveData<QuestionEntity> selectedQuestion() {
        return question;
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
