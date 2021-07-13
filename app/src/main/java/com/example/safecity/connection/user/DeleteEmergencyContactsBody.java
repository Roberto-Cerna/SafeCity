package com.example.safecity.connection.user;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DeleteEmergencyContactsBody {
    @SerializedName("contact_phones")
    private ArrayList<String> contact_phones;

    public DeleteEmergencyContactsBody(ArrayList<String> contact_phones) {
        this.contact_phones = contact_phones;
    }
}
