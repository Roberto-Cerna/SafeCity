package com.example.safecity.connection.report;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReportAPI {

    // Post
    // // Parameters: -
    // // Req: User id, contact_name, contact_phone
    // // Returns: 202 exito
    @POST("report-incident/message/{id}")
    Call<Void> postSendMessage(@Path ("id") String id, @Body SendMessage SendMessage);

    //https://safecity-api.herokuapp.com/report-incident/duration/ + dias
    //https://safecity-api.herokuapp.com/report-incident/duration/3
    @GET("report-incident/duration/{days}")
    Call<GetRecentReportsResult> getLastNIncidents(@Path("days") String days);
    //eso te da los incidentes de los ultimos x dias
}