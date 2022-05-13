package com.ailyan.intrus.data.sources.remote.beans;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Reponse", strict = false)
public class CheckSessionResponse {
    @Element(name = "Succes", required = false)
    public boolean succes;
    @Element(name = "Message", required = false)
    public String message;
    @Element(name = "Objet", required = false)
    public Data data;

    @Root(name = "Objet", strict = false)
    public static class Data {
        @Element(name = "Id", required = false)
        public long id;
        @Element(name = "Login", required = false)
        public String username;

        @NonNull
        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "CheckSessionResponse{" +
                "succes=" + succes +
                ", message='" + message + '\'' +
                '}';
    }
}
