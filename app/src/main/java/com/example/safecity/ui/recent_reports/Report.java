package com.example.safecity.ui.recent_reports;

public class Report {
    public String name;
    public String type;
    public boolean isSeen;
    public String time;

    public Report(String name, String type, boolean isSeen, String time) {
        this.name = name;
        this.type = type;
        this.isSeen = isSeen;
        this.time = time;
    }
}
