package com.ailyan.intrus.data.repositories;

import android.app.Application;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.LevelDao;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Observable;

public class LevelRepository {
    private final Executor executor = Executors.newSingleThreadScheduledExecutor();
    private final LevelDao levelDao;

    public LevelRepository(Application application) {
        levelDao = AppDatabase.getInstance(application).levelDao();
    }

    public Observable<List<LevelEntity>> loadAllLevels() {
        return levelDao.loadAllLevel();
    }

    public void updateLevel(LevelEntity level) {
        executor.execute(() -> levelDao.updateLevel(level));
    }
}
