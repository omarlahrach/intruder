package com.ailyan.intrus.data.sources.local.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionEntity implements Parcelable {
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

    protected QuestionEntity(Parcel in) {
        id = in.readInt();
        statement = in.readString();
        level = in.readInt();
        validation = in.readInt();
        establishmentValidation = in.readInt();
    }

    public static final Creator<QuestionEntity> CREATOR = new Creator<QuestionEntity>() {
        @Override
        public QuestionEntity createFromParcel(Parcel in) {
            return new QuestionEntity(in);
        }

        @Override
        public QuestionEntity[] newArray(int size) {
            return new QuestionEntity[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(statement);
        parcel.writeInt(level);
        parcel.writeInt(validation);
        parcel.writeInt(establishmentValidation);
    }
}
