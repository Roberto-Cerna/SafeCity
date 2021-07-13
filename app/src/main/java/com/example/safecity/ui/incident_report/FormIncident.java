package com.example.safecity.ui.incident_report;

import android.graphics.Bitmap;

public class FormIncident {

    private String victim;
    private String incident;
    private String details;
    private String latitude;
    private String longitude;
    private Bitmap imageReport;
    private String id;

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Bitmap getImageReport() {
        return imageReport;
    }

    public void setImageReport(Bitmap imageReport) {
        this.imageReport = imageReport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}