package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.LevelRepository;
import com.ailyan.intrus.data.sources.local.entities.LevelEntity;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LevelViewModel extends AndroidViewModel {
    private static final String TAG = LevelViewModel.class.getSimpleName();
    private final MutableLiveData<List<LevelEntity>> levels = new MutableLiveData<>();
    private final LevelRepository levelRepository;
    private Disposable disposable;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        this.levelRepository = new LevelRepository(application);
    }

    public LiveData<List<LevelEntity>> loadAllLevels() {
        disposable = levelRepository.loadAllLevels()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        levels::postValue,
                        throwable -> Log.e(TAG, "Cannot load levels!", throwable)
                );
        return levels;
    }

    public void updateLevel(LevelEntity level) {
        levelRepository.updateLevel(level);
    }

    @Override
    protected void onCleared() {
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();
        super.onCleared();
    }
}
