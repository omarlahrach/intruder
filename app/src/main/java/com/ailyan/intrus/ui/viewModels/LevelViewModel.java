package com.ailyan.intrus.ui.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.LevelRepository;

import java.util.List;

public class LevelViewModel extends AndroidViewModel {
    //private static final String TAG = LevelViewModel.class.getSimpleName();
    private final MutableLiveData<Integer> level = new MutableLiveData<>();
    private final LevelRepository levelRepository;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        this.levelRepository = new LevelRepository();
    }

    public List<Integer> loadAllLevels() {
        return levelRepository.loadAllLevels();
    }

    public MutableLiveData<Integer> selectedLevel() {
        return level;
    }
}
