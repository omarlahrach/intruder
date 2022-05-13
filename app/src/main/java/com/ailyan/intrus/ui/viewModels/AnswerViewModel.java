package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.AnswerRepository;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.utilities.enums.ConnectionState;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnswerViewModel extends AndroidViewModel {
    private static final String TAG = AnswerViewModel.class.getSimpleName();
    private final MutableLiveData<ConnectionState> connectionState = new MutableLiveData<>();
    private final MutableLiveData<List<AnswerEntity>> localAnswersByQuestion = new MutableLiveData<>();
    private final MutableLiveData<AnswerEntity> answer = new MutableLiveData<>();
    private final AnswerRepository answerRepository;
    private Disposable disposable;

    public AnswerViewModel(@NonNull Application application) {
        super(application);
        this.answerRepository = new AnswerRepository(application);
    }

    public LiveData<ConnectionState> loadRemoteAnswersByQuestionId(int questionId) {
        disposable = answerRepository.getAllRemoteAnswersByQuestionId(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        answers -> {},
                        throwable -> {
                            Log.e(TAG, "Cannot access remote answers!", throwable);
                            connectionState.setValue(ConnectionState.TIMEOUT);
                        }
                );
        return connectionState;
    }

    public LiveData<List<AnswerEntity>> loadLocalAnswersByQuestionId(int questionId) {
        disposable = answerRepository.loadLocalAnswersByQuestionId(questionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this.localAnswersByQuestion::setValue,
                        throwable -> Log.e(TAG, "Cannot load local answers by question!", throwable)
                );
        return localAnswersByQuestion;
    }

    public MutableLiveData<AnswerEntity> selectedAnswer() {
        return answer;
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
