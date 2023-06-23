package com.example.aplikasikita.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.aplikasikita.model.ApiResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.1.9/aplikasikita/";

    private static Retrofit retrofit = null;


    public static Retrofit getApiClient(){
        if(retrofit == null){
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

//    private static OkHttpClient client =new OkHttpClient.Builder()
//            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .build();
}