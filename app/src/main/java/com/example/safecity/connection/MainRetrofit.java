package com.example.safecity.connection;

import com.example.safecity.connection.report.ReportAPI;
import com.example.safecity.connection.user.UserAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRetrofit {
    private static final String BASE_URL = "https://safecity-api.herokuapp.com/";
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static UserAPI userAPI = retrofit.create(UserAPI.class);
    public static ReportAPI reportAPI = retrofit.create(ReportAPI.class);
}
