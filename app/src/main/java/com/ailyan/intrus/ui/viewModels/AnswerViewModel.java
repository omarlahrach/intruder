package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.AnswerRepository;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.utilities.enums.DataSource;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnswerViewModel extends AndroidViewModel {
    private static final String TAG = AnswerViewModel.class.getSimpleName();
    private final MutableLiveData<List<AnswerEntity>> answers = new MutableLiveData<>();
    private final MutableLiveData<AnswerEntity> answer = new MutableLiveData<>();
    private final AnswerRepository answerRepository;
    private Disposable disposable;

    public AnswerViewModel(@NonNull Application application) {
        super(application);
        this.answerRepository = new AnswerRepository(application);
    }

    public LiveData<List<AnswerEntity>> loadAnswersByQuestionId(LoginViewModel loginViewModel, DataSource dataSource, int questionId) {
        switch (dataSource) {
            case REMOTE:
                disposable = answerRepository.loadRemoteAnswersByQuestionId(questionId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                answers::postValue,
                                throwable -> {
                                    Log.e(TAG, "Cannot load remote answers by question!", throwable);
                                    loginViewModel.checkSession(null, null);
                                }
                        );
                break;
            case LOCAL:
                disposable = answerRepository.loadLocalAnswersByQuestionId(questionId)
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                answers::postValue,
                                throwable -> Log.e(TAG, "Cannot load local answers by question!", throwable)
                        );
                break;
        }
        return answers;
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
