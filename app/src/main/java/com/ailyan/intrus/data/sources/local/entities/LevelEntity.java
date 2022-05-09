package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LevelEntity implements Serializable {
    @PrimaryKey
    public int id;

    public LevelEntity(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "LevelEntity{" +
                "id=" + id +
                '}';
    }
}
