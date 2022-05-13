package com.ailyan.intrus.data.sources.remote.beans;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("succes")
    public boolean success;
    @SerializedName("message")
    public String message;
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
                "success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", session='" + session + '\'' +
                ", establishment=" + establishment +
                '}';
    }
}