package com.example.safecity.connection.review;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReviewAPI {
    @POST("review/new")
    Call<Void> postNewReview(@Body HashMap<String,String> map);


}
