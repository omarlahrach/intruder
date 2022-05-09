package com.ailyan.intrus.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.QuestionDao;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.data.sources.remote.services.QuestionService;
import com.ailyan.intrus.utilities.DataSource;
import com.ailyan.intrus.utilities.SharedData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuestionRepository {
    private static final String TAG = QuestionRepository.class.getSimpleName();
    private final AuthResponse authResponse;
    private final QuestionService questionService;
    private final QuestionDao questionDao;
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();

    public QuestionRepository(Application application) {
        authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        questionService = RetrofitInstance.getInstance().create(QuestionService.class);
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public LiveData<List<QuestionEntity>> loadQuestionsByLevel(LevelEntity levelEntity, DataSource dataSource) {
        switch (dataSource) {
            case LOCAL:
                return LiveDataReactiveStreams.fromPublisher(questionDao
                        .loadQuestionsByLevel(levelEntity.id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            case REMOTE:
                return LiveDataReactiveStreams.fromPublisher(questionService
                        .loadAllQuestions(authResponse.username, authResponse.session)
                        .flatMap(questionResponse -> Flowable
                                .fromIterable(questionResponse.data.questions)
                                .filter(question -> question.establishment == authResponse.establishment)
                                .map(question -> new QuestionEntity(
                                        question.id,
                                        question.statement,
                                        question.level,
                                        question.validation,
                                        question.establishmentValidation
                                ))
                                .filter(questionEntity -> questionEntity.levelId == levelEntity.id)
                                .toList().toFlowable())
                        .map(questionEntities -> {
                            executor.execute(() -> questionDao.insertAllQuestions(questionEntities));
                            return questionEntities;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            default:
                return null;
        }
    }
}
