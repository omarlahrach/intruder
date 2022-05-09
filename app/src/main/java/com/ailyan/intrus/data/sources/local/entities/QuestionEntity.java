package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionEntity {
    @PrimaryKey
    public long id;
    public String statement;
    public int levelId;
    public int validation;
    public int establishmentValidation;

    public QuestionEntity(long id, String statement, int levelId, int validation, int establishmentValidation) {
        this.id = id;
        this.statement = statement;
        this.levelId = levelId;
        this.validation = validation;
        this.establishmentValidation = establishmentValidation;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                ", levelId=" + levelId +
                ", validation=" + validation +
                ", establishmentValidation=" + establishmentValidation +
                '}';
    }
}
