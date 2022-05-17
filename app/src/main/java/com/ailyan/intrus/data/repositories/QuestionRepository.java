package com.ailyan.intrus.data.repositories;

import android.app.Application;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.QuestionDao;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.services.QuestionService;
import com.ailyan.intrus.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class QuestionRepository {
    //private static final String TAG = QuestionRepository.class.getSimpleName();
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final Application application;
    private final QuestionService questionService;
    private final QuestionDao questionDao;

    public QuestionRepository(Application application) {
        this.application = application;
        questionService = RetrofitInstance.getInstance().create(QuestionService.class);
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public Observable<List<QuestionEntity>> loadAllRemoteQuestions() {
        AuthResponse authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        return questionService.loadAllQuestions(authResponse.username, authResponse.session)
                .flatMap(questionResponse -> Observable
                        .fromIterable(questionResponse.data.questions)
                        .filter(question -> question.establishment == authResponse.establishment)
                        .map(question -> new QuestionEntity(
                                question.id,
                                question.statement,
                                question.level,
                                question.validation,
                                question.establishmentValidation
                        ))
                        .toList().toObservable())
                .map(questionEntities -> {
                    executor.execute(() -> questionDao.insertAllQuestions(questionEntities));
                    return questionEntities;
                });
    }

    public Observable<List<QuestionEntity>> loadLocalQuestionsByLevel(int level) {
        return questionDao.loadQuestionsByLevel(level);
    }
}
