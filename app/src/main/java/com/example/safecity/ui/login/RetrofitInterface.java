package com.example.safecity.ui.login;

import com.example.safecity.ui.incident_report.FormIncident;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @POST("user/login")
    Call<LoginResult> executeLogin(@Body HashMap<String,String> map);

    @POST("user/register")
    Call<Void> executeSignup(@Body HashMap<String,String> map);

    @POST("user/request_new_password")
    Call<LoginResult> executeRequest(@Body HashMap<String,String> map);

    @POST("user/new_password")
    Call<Void> executeNewPassword(@Body HashMap<String,String> map);

    @Multipart
    @POST("report-incident/form")
    Call<Void> executeSendIncident(@Part("victim") RequestBody victim,
                                           @Part("incident") RequestBody incident,
                                           @Part("details") RequestBody details,
                                           @Part("longitude") RequestBody longitude,
                                           @Part("latitude") RequestBody latitude,
                                           @Part("id") RequestBody id,
                                           @Part MultipartBody.Part imageReport);
}
