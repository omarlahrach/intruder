package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerEntity {
    @PrimaryKey
    public long id;
    public String imageUrl;
    public boolean isCorrect;
    public long questionId;

    public AnswerEntity(long id, String imageUrl, boolean isCorrect, long questionId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnswerEntity{" +
                "id=" + id +
                ", imageUrl=" + imageUrl +
                ", isCorrect=" + isCorrect +
                ", questionId=" + questionId +
                '}';
    }
}
