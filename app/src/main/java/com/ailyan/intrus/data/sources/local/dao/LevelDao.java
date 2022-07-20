package com.ailyan.intrus.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ailyan.intrus.data.sources.local.entities.LevelEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface LevelDao {
    @Query("SELECT * FROM levelentity")
    Observable<List<LevelEntity>> loadAllLevel();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertLevel(LevelEntity level);

    @Update
    void updateLevel(LevelEntity level);
}
