package com.info121.mapletree.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.info121.mapletree.App;


import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by KZHTUN on 2/16/2017.
 */

public class RestClient {

    private static String AuthToken = "";
    private static RestClient instance = null;
    private static int callCount = 10;
    private APIService service;

    private RestClient() {

        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.CONST_REST_API_URL)
                .client(new OkHttpClient().newBuilder()
                        .protocols(Util.immutableList(Protocol.HTTP_1_1))
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                HttpUrl originalHttpUrl = original.url();

                                HttpUrl newUrl = originalHttpUrl.newBuilder()
                                        .build();


                                Request newRequest = original.newBuilder()
                                        .header("driver", App.userName)
                                        .header("token", App.authToken)
                                        .url(newUrl)
                                        .method(original.method(), original.body())
                                        .build();

                                return chain.proceed(newRequest);
                            }
                        })

                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build()
                ).build();

        service = retrofit.create(APIService.class);

    }

    public APIService getApiService() {
        return service;
    }

    public static RestClient MAPLE() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public static void Dismiss(){
        instance = null;
    }

}
