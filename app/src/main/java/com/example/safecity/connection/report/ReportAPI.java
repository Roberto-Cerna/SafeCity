package com.example.safecity.connection.report;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReportAPI {

    //https://safecity-api.herokuapp.com/report-incident/duration/ + dias
    //https://safecity-api.herokuapp.com/report-incident/duration/3
    @GET("report-incident/duration/{days}")
    Call<GetRecentReportsResult> getLastNIncidents(@Path("days") String days);
    //eso te da los incidentes de los ultimos x dias
}