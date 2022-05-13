package com.ailyan.intrus.data.sources.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ailyan.intrus.data.sources.local.entities.AnswerEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface AnswerDao {
    @Query("SELECT * FROM answerentity WHERE questionId = :questionId")
    Observable<List<AnswerEntity>> loadAnswersByQuestionId(long questionId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAllAnswers(List<AnswerEntity> answerEntities);
}
