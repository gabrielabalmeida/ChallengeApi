package com.gabrielabalmeida.apigist;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by gabriela on 03/01/18.
 */

public class Api {

    private static final String END_POINT = "https://api.github.com";

    private static ServiceGistApi REST_CLIENT;


    public static ServiceGistApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient(Context context, Gson gson) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setErrorHandler(new ErroApi(context))
                .setClient(new OkClient(new OkHttpClient()));

        if (gson != null)
            builder.setConverter(new GsonConverter(gson));

        RestAdapter restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(ServiceGistApi.class);
    }

    public static void getGist(Context context, int page, CallbackRetrof<List<Usuarios>> callback) {
        Type listType = new TypeToken<ArrayList<Usuarios>>() {
        }.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(listType, new DateTypeAdapter())
                .registerTypeAdapter(ObjetoFile.class, new ObjDeserializerGist())
                .create();

        setupRestClient(context, gson);
        Api.get().getPaginas(page, callback);
    }
}
