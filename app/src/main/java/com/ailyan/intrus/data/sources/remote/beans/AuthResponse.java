package com.ailyan.intrus.data.sources.remote.beans;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("id")
    public long id;
    @SerializedName("login")
    public String username;
    @SerializedName("session")
    public String session;
    @SerializedName("etablissement")
    public int establishment;

    @NonNull
    @Override
    public String toString() {
        return "AuthResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", session='" + session + '\'' +
                ", establishment=" + establishment +
                '}';
    }
}