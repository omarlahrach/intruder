package com.ailyan.intrus.data.sources.remote.beans;

import androidx.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Reponse", strict = false)
public class QuestionResponse {
    @Element(name = "Succes", required = false)
    public boolean succes;
    @Element(name = "Message", required = false)
    public String message;
    @Element(name = "Objet", required = false)
    public Data data;

    @Root(name = "Objet", strict = false)
    public static class Data {
        @ElementList(entry = "Question", inline = true, required = false)
        public List<Question> questions;

        @Root(name = "Question", strict = false)
        public static class Question {
            @Element(name = "Id", required = false)
            public int id;
            @Element(name = "Enonce", required = false)
            public String statement;
            @Element(name = "Niveau", required = false)
            public int level;
            @Element(name = "Etablissement", required = false)
            public int establishment;
            @Element(name = "Validation", required = false)
            public int validation;
            @Element(name = "ValidationEtablissement", required = false)
            public int establishmentValidation;
            @Element(name = "CategorieQuestion", required = false)
            public Category category;

            @Root(name = "CategoryQuestion", strict = false)
            public static class Category {
                @Element(name = "Id", required = false)
                public int id;
                @Element(name = "Nom", required = false)
                public String name;
                @Element(name = "Validation", required = false)
                public int validation;
                @Element(name = "ValidationEtablissement", required = false)
                public int establishmentValidation;

                @NonNull
                @Override
                public String toString() {
                    return "Category{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            ", validation=" + validation +
                            ", establishmentValidation=" + establishmentValidation +
                            '}';
                }
            }

            @NonNull
            @Override
            public String toString() {
                return "Question{" +
                        "id=" + id +
                        ", statement='" + statement + '\'' +
                        ", level=" + level +
                        ", establishment=" + establishment +
                        ", validation=" + validation +
                        ", establishmentValidation=" + establishmentValidation +
                        ", category=" + category +
                        '}';
            }
        }

        @NonNull
        @Override
        public String toString() {
            return "Data{" +
                    "questions=" + questions +
                    '}';
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionResponse{" +
                "succes=" + succes +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}