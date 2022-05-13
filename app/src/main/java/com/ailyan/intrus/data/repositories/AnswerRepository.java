package com.ailyan.intrus.data.repositories;

import android.app.Application;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.AnswerDao;
import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;
import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.services.AnswerService;
import com.ailyan.intrus.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class AnswerRepository {
    //private static final String TAG = AnswerRepository.class.getSimpleName();
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final Application application;
    private final AnswerService answerService;
    private final AnswerDao answerDao;

    public AnswerRepository(Application application) {
        this.application = application;
        answerService = RetrofitInstance.getInstance().create(AnswerService.class);
        answerDao = AppDatabase.getInstance(application).answerDao();
    }

    public Observable<List<AnswerEntity>> getAllRemoteAnswersByQuestionId(int questionId) {
        AuthResponse authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        return answerService.loadAllAnswers(authResponse.username, authResponse.session)
                .flatMap(answerResponse -> Observable
                        .fromIterable(answerResponse.data.answers)
                        .filter(answer -> answer.question.id == questionId)
                        .map(answer -> new AnswerEntity(
                                answer.id,
                                answer.imageUrl,
                                answer.isCorrect,
                                answer.question.id)
                        ))
                .toList().toObservable()
                .map(answerEntities -> {
                    executor.execute(() -> answerDao.insertAllAnswers(answerEntities));
                    return answerEntities;
                });
    }

    public Observable<List<AnswerEntity>> loadLocalAnswersByQuestionId(int questionId) {
        return answerDao.loadAnswersByQuestionId(questionId);
    }
}
