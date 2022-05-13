package com.ailyan.intrus.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ailyan.intrus.data.sources.local.entities.QuestionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface QuestionDao {
    @Query("SELECT * FROM questionentity WHERE level = :level")
    Observable<List<QuestionEntity>> loadQuestionsByLevel(int level);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllQuestions(List<QuestionEntity> questionEntities);
}
