package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.LevelRepository;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.remote.beans.AuthResponse;
import com.ailyan.intrus.utilities.ConnectionLiveData;
import com.ailyan.intrus.utilities.DataSource;

import java.util.List;

public class LevelViewModel extends AndroidViewModel {
    private static final String TAG = LevelViewModel.class.getSimpleName();
    private final LevelRepository levelRepository;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        levelRepository = new LevelRepository(application);
    }

    public LiveData<List<LevelEntity>> loadAllLocalLevels() {
        return levelRepository.loadAllLevels(DataSource.LOCAL);
    }

    public LiveData<List<LevelEntity>> loadAllRemoteLevels() {
        return levelRepository.loadAllLevels(DataSource.REMOTE);
    }
}
