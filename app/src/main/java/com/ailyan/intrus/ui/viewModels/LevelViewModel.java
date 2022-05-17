package com.ailyan.intrus.ui.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ailyan.intrus.data.repositories.LevelRepository;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LevelViewModel extends AndroidViewModel {
    private static final String TAG = LevelViewModel.class.getSimpleName();
    private final MutableLiveData<List<Integer>> levels = new MutableLiveData<>();
    private final LevelRepository levelRepository;
    private Disposable disposable;

    public LevelViewModel(@NonNull Application application) {
        super(application);
        this.levelRepository = new LevelRepository(application);
    }

    public LiveData<List<Integer>> loadAllLevels() {
        disposable = levelRepository.loadAllLevels()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        levels::postValue,
                        throwable -> Log.d(TAG, "Cannot load levels!")
                );
        return levels;
    }
}
