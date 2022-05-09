package com.ailyan.intrus.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.AnswerDao;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.services.AnswerService;
import com.ailyan.intrus.utilities.DataSource;
import com.ailyan.intrus.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AnswerRepository {
    private static final String TAG = AnswerRepository.class.getSimpleName();
    private final AuthResponse authResponse;
    private final AnswerService answerService;
    private final AnswerDao answerDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public AnswerRepository(Application application) {
        authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        answerService = RetrofitInstance.getInstance().create(AnswerService.class);
        answerDao = AppDatabase.getInstance(application).answerDao();
    }

    public LiveData<List<AnswerEntity>> loadAnswersByQuestion(QuestionEntity questionEntity, DataSource dataSource) {
        switch (dataSource) {
            case LOCAL:
                return LiveDataReactiveStreams.fromPublisher(answerDao
                        .loadAnswersByQuestionId(questionEntity.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            case REMOTE:
                return LiveDataReactiveStreams.fromPublisher(answerService
                        .loadAllAnswers(authResponse.username, authResponse.session)
                        .flatMap(answerResponse -> Flowable
                                .fromIterable(answerResponse.data.answers)
                                .filter(answer -> answer.question.id == questionEntity.id)
                                .map(answer -> new AnswerEntity(
                                                answer.id,
                                                answer.imageUri,
                                                answer.isCorrect,
                                                answer.question.id
                                        ))
                                .toList().toFlowable())
                        .map(answerEntities -> {
                            executor.execute(() -> answerDao.insertAllAnswers(answerEntities));
                            return answerEntities;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            default:
                return null;
        }
    }
}
