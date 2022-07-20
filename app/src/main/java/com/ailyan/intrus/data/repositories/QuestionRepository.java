package com.ailyan.intrus.data.repositories;

import android.app.Application;

import com.ailyan.intrus.R;
import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.LevelDao;
import com.ailyan.intrus.data.sources.local.dao.QuestionDao;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;
import com.ailyan.intrus.data.sources.local.entities.ScoreEntity;
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
    private final LevelDao levelDao;
    private final QuestionDao questionDao;

    public QuestionRepository(Application application) {
        this.application = application;
        questionService = RetrofitInstance.getInstance().create(QuestionService.class);
        levelDao = AppDatabase.getInstance(application).levelDao();
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public Observable<List<QuestionEntity>> loadAllRemoteQuestions() {
        AuthResponse authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        return questionService.loadAllQuestions(authResponse.username, authResponse.session)
                .flatMap(questionResponse -> Observable
                        .fromIterable(questionResponse.data.questions)
                        .filter(question -> question.establishment == authResponse.establishment)
                        .map(question -> {
                            LevelEntity level = new LevelEntity();
                            level.id = question.level;
                            switch (level.id) {
                                case 0:
                                    level.title = application.getResources().getString(R.string.level1_title);
                                    break;
                                case 1:
                                    level.title = application.getResources().getString(R.string.level2_title);
                                    break;
                                case 2:
                                    level.title = application.getResources().getString(R.string.level3_title);
                                    break;
                                case 3:
                                    level.title = application.getResources().getString(R.string.level4_title);
                                    break;
                            }
                            level.score = new ScoreEntity();
                            executor.execute(() -> levelDao.insertLevel(level));
                            return new QuestionEntity(
                                    question.id,
                                    question.statement,
                                    question.level,
                                    question.validation,
                                    question.establishmentValidation
                            );
                        })
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
