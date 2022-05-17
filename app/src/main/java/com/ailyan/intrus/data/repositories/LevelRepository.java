package com.ailyan.intrus.data.repositories;

import android.app.Application;

import com.ailyan.intrus.data.sources.local.AppDatabase;
import com.ailyan.intrus.data.sources.local.dao.QuestionDao;
import com.ailyan.intrus.data.sources.remote.RetrofitInstance;
import com.ailyan.intrus.data.sources.remote.services.QuestionService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class LevelRepository {
    private final QuestionDao questionDao;

    public LevelRepository(Application application) {
        questionDao = AppDatabase.getInstance(application).questionDao();
    }

    public Observable<List<Integer>> loadAllLevels() {
        return questionDao.loadAllLevels();
    }
}
