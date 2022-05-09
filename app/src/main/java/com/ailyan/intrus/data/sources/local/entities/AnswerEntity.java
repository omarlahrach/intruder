package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerEntity {
    @PrimaryKey
    public long id;
    public String imageUri;
    public boolean isCorrect;
    public long questionId;

    public AnswerEntity(long id, String imageUri, boolean isCorrect, long questionId) {
        this.id = id;
        this.imageUri = imageUri;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    @NonNull
    @Override
    public String toString() {
        return "AnswerEntity{" +
                "id=" + id +
                ", imageUri='" + imageUri + '\'' +
                ", isCorrect=" + isCorrect +
                ", questionId=" + questionId +
                '}';
    }
}
