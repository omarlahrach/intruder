package com.ailyan.intrus.data.sources.local.entities;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class LevelEntity implements Serializable {
    @PrimaryKey
    public int id;
    public String title;
    @Embedded
    public ScoreEntity score;
}
