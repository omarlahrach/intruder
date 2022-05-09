package com.ailyan.intrus.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.LevelDao;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
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

public class LevelRepository {
    private static final String TAG = LevelRepository.class.getSimpleName();
    private final AuthResponse authResponse;
    private final QuestionService questionService;
    private final LevelDao levelDao;
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();

    public LevelRepository(Application application) {
        authResponse = (AuthResponse) SharedData.get(application, AuthResponse.class, "auth");
        questionService = RetrofitInstance.getInstance().create(QuestionService.class);
        levelDao = AppDatabase.getInstance(application).levelDao();
    }

    public LiveData<List<LevelEntity>> loadAllLevels(DataSource dataSource) {
        switch (dataSource) {
            case LOCAL:
                return LiveDataReactiveStreams.fromPublisher(levelDao
                        .loadAllLevels()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            case REMOTE:
                return LiveDataReactiveStreams.fromPublisher(questionService
                        .loadAllQuestions(authResponse.username, authResponse.session)
                        .flatMap(questionResponse -> Flowable
                                .fromIterable(questionResponse.data.questions)
                                .filter(question -> question.establishment == authResponse.establishment)
                                .distinct(question -> question.level)
                                .map(question -> new LevelEntity(question.level))
                                .toList().toFlowable())
                        .map(levelEntities -> {
                            executor.execute(() -> levelDao.insertAllLevels(levelEntities));
                            return levelEntities;
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()));
            default:
                return null;
        }
    }
}





