package com.example.safecity.connection.report;

import com.example.safecity.data.reports_list.Report;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetRecentReportsResult {
    @SerializedName("recentReports")
    @Expose
    private ArrayList<Report> recentReports;

    public ArrayList<Report> getRecentReports() {
        return recentReports;
    }

    public void setRecentReports(ArrayList<Report> recentReports) {
        this.recentReports = recentReports;
    }
}
