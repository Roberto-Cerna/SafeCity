package com.example.safecity.connection.user;

import com.google.gson.annotations.SerializedName;

public class PostEmergencyContactBody {
    @SerializedName("id")
    private String id;
    @SerializedName("contact_name")
    private String contact_name;
    @SerializedName("contact_phone")
    private String contact_phone;

    public PostEmergencyContactBody(String id, String contact_name, String contact_phone) {
        this.id = id;
        this.contact_name = contact_name;
        this.contact_phone = contact_phone;
    }
}
