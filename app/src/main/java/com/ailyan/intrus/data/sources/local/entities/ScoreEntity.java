package com.ailyan.intrus.data.sources.local.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ScoreEntity implements Serializable {
    public int maximumResult;
    public int resultObtained;
    private short rate;

    public short getRate() {
        rate = (short) ((float) resultObtained/maximumResult * 100);
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public short getStarsNumber() {
        short starsNumber;
        if (rate >= 0 && rate < 35)
            starsNumber = 0;
        else if (rate >= 35 && rate < 70)
            starsNumber = 1;
        else if (rate >= 70 && rate < 100)
            starsNumber = 2;
        else
            starsNumber = 3;
        return starsNumber;
    }

    @NonNull
    @Override
    public String toString() {
        return "ScoreEntity{" +
                "maximumResult=" + maximumResult +
                ", resultObtained=" + resultObtained +
                ", rate=" + rate +
                '}';
    }
}
