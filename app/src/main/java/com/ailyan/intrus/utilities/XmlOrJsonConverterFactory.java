package com.ailyan.intrus.utilities;

import androidx.annotation.NonNull;

import com.ailyan.intrus.utilities.annotations.Json;
import com.ailyan.intrus.utilities.annotations.Xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class XmlOrJsonConverterFactory extends Converter.Factory {
    @SuppressWarnings("deprecation")
    final Converter.Factory xml = SimpleXmlConverterFactory.create();
    final Converter.Factory json = GsonConverterFactory.create();

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Xml.class)
                return xml.responseBodyConverter(type, annotations, retrofit);
            if (annotation.annotationType() == Json.class)
                return json.responseBodyConverter(type, annotations, retrofit);
        }
        return null;
    }
}
