package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionEntity {
    @PrimaryKey
    public int id;
    public String statement;
    public int level;
    public int validation;
    public int establishmentValidation;

    public QuestionEntity(int id, String statement, int level, int validation, int establishmentValidation) {
        this.id = id;
        this.statement = statement;
        this.level = level;
        this.validation = validation;
        this.establishmentValidation = establishmentValidation;
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", statement='" + statement + '\'' +
                ", level=" + level +
                ", validation=" + validation +
                ", establishmentValidation=" + establishmentValidation +
                '}';
    }
}
