package com.ailyan.intrus.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ailyan.intrus.data.sources.local.entities.LevelEntity;
import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface LevelDao {
    @Query("SELECT * FROM levelentity")
    Flowable<List<LevelEntity>> loadAllLevels();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllLevels(List<LevelEntity> levelEntities);
}
