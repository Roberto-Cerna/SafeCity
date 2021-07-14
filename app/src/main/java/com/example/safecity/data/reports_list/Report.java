package com.example.safecity.data.reports_list;


public class Report {
    public String id;
    public String victimId;
    public String victim;
    public String incident;
    public String details;
    public String locationLatitude;
    public String locationLongitude;
    public String fileURL;
    public Double daysAgo;
    //public Boolean isSeen;

    public Report(String id, String victimId, String victim, String incident, String details, String locationLatitude, String locationLongitude, String fileURL, Double daysAgo) {
        this.id = id;
        this.victimId = victimId;
        this.victim = victim;
        this.incident = incident;
        this.details = details;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.fileURL = fileURL;
        this.daysAgo = daysAgo;
    }

    /*
    public Report(String id, String victimId, String victim, String incident, String details, String location, String fileURL, Double daysAgo, Boolean isSeen) {
        this.id = id;
        this.victimId = victimId;
        this.victim = victim;
        this.incident = incident;
        this.details = details;
        this.location = location;
        this.fileURL = fileURL;
        this.daysAgo = daysAgo;
        this.isSeen = isSeen;
    }*/
}
