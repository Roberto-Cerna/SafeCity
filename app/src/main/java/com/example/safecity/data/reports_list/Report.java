package com.example.safecity.data.reports_list;

public class Report {
    public String name = "";
    public String type = "";
    public String details = "";
    public boolean isSeen = false;
    public String time = "6h";

    public Report(String name, String type, String details, boolean isSeen, String time) {
        this.name = name;
        this.type = type;
        this.details = details;
        this.isSeen = isSeen;
        this.time = time;
    }
}
